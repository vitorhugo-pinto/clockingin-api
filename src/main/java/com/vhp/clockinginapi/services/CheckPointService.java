package com.vhp.clockinginapi.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.vhp.clockinginapi.dtos.CheckPointDTO;
import com.vhp.clockinginapi.dtos.CheckPointRequestDTO;
import com.vhp.clockinginapi.dtos.CheckPointResponseDTO;
import com.vhp.clockinginapi.dtos.SummaryDTO;
import com.vhp.clockinginapi.mappers.CheckPointMapper;
import com.vhp.clockinginapi.models.CheckPointEntity;
import com.vhp.clockinginapi.models.enums.JobType;
import com.vhp.clockinginapi.repositories.CheckPointRepository;

@Service
public class CheckPointService {

  private CheckPointRepository checkPointRepository;
  private CheckPointMapper checkPointMapper;

  CheckPointService(CheckPointRepository checkPointRepository, CheckPointMapper checkPointMapper) {
    this.checkPointRepository = checkPointRepository;
    this.checkPointMapper = checkPointMapper;
  }

  public CheckPointDTO create(CheckPointRequestDTO dto, UUID userId){
    CheckPointEntity checkPoint = CheckPointEntity.builder().timeStamp(dto.timeStamp()).lunchBreak(dto.lunchBreak()).build();
    
    checkPoint.setUserId(userId);
    
    CheckPointEntity response = this.checkPointRepository.save(checkPoint);

    return this.checkPointMapper.toDto(response);
  }

  public SummaryDTO getSummary(UUID userId, String jobType){
    long FULLTIME_WORKING_HOURS_IN_MINUTES = 60 * 8;
    long PARTTIME_WORKING_HOURS_IN_MINUTES = 60 * 6;
    jobType = jobType.replaceAll("\"", "");

    LocalDateTime startOfTheDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    
    LocalDateTime endOfTheDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    
    List<CheckPointEntity> userCheckPoints = this.checkPointRepository.findAllByUserIdAndTimeStampBetween(userId, startOfTheDay, endOfTheDay);

    List<CheckPointResponseDTO> checkPointResponse = this.checkPointMapper.toDto(userCheckPoints);

    long sum = 0;
    for (int i = 0; i < userCheckPoints.size() - 1; i+=2){
      if (i>userCheckPoints.size()) break;
      long diff = ChronoUnit.MINUTES.between(userCheckPoints.get(i).getTimeStamp(), userCheckPoints.get(i+1).getTimeStamp());
      sum += diff;
    }

    long workBalance = jobType.equals(JobType.FULLTIME.name()) ? sum - FULLTIME_WORKING_HOURS_IN_MINUTES : 
      sum - PARTTIME_WORKING_HOURS_IN_MINUTES;

    boolean finishedShift = false;
    if(jobType.equals(JobType.FULLTIME.name())) {
      finishedShift = sum >= FULLTIME_WORKING_HOURS_IN_MINUTES;
    }
    if(jobType.equals(JobType.PARTTIME.name())) {
      finishedShift = sum >= PARTTIME_WORKING_HOURS_IN_MINUTES;
    }

    SummaryDTO summary = new SummaryDTO(checkPointResponse, workBalance, finishedShift);

    return summary;
  }
  
}
