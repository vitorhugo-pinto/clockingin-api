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
import com.vhp.clockinginapi.utils.exceptions.AlreadyInLunchBreakException;
import com.vhp.clockinginapi.utils.exceptions.DatePreviousThanLastRegisteredException;
import com.vhp.clockinginapi.utils.exceptions.LunchTimeBreakException;
import com.vhp.clockinginapi.utils.exceptions.UserNoLunchBreakException;

@Service
public class CheckPointService {

  private CheckPointRepository checkPointRepository;
  private CheckPointMapper checkPointMapper;

  CheckPointService(CheckPointRepository checkPointRepository, CheckPointMapper checkPointMapper) {
    this.checkPointRepository = checkPointRepository;
    this.checkPointMapper = checkPointMapper;
  }

  public CheckPointDTO create(CheckPointRequestDTO dto, UUID userId, String jobType){

    // Checks if the JobType is PARTTIME, this one cannot have lunch break
    if(jobType.replaceAll("\"", "").equals(JobType.PARTTIME.name()) && dto.lunchBreak()){
      throw new UserNoLunchBreakException("User can not go on lunch break");
    }

    LocalDateTime START_OF_DAY = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    LocalDateTime END_OF_DAY = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    Long LUNCH_BREAK_DURATION_MINUTES = 60L;

    List<CheckPointEntity> userCheckPoints = this.checkPointRepository.findAllByUserIdAndTimeStampBetweenOrderByTimeStampAsc(userId, START_OF_DAY, END_OF_DAY);

    // Checks if the date inserted is previous to the on last registered
    if(!userCheckPoints.isEmpty()){
      var lastCheckPointRegistered = userCheckPoints.get(userCheckPoints.size() - 1);
      if(ChronoUnit.MINUTES.between(lastCheckPointRegistered.getTimeStamp(), dto.timeStamp()) < 0){
        throw new DatePreviousThanLastRegisteredException("The date cannot be previous to the one last registered");
      }
    }

    // Checks lunch time conditions
    if(!userCheckPoints.isEmpty()){
      var lastCheckPointRegistered = userCheckPoints.get(userCheckPoints.size() - 1);

      Long diffInMinutes = ChronoUnit.MINUTES.between(lastCheckPointRegistered.getTimeStamp(), dto.timeStamp());

      if(lastCheckPointRegistered.getLunchBreak() && dto.lunchBreak()) {
        throw new AlreadyInLunchBreakException("Already in lunch break");
      }

      if(lastCheckPointRegistered.getLunchBreak() && diffInMinutes < LUNCH_BREAK_DURATION_MINUTES) {
        throw new LunchTimeBreakException("Still in lunch time");
      }
    }

    CheckPointEntity checkPoint = CheckPointEntity.builder().timeStamp(dto.timeStamp()).lunchBreak(dto.lunchBreak()).build();

    checkPoint.setUserId(userId);
    
    CheckPointEntity response = this.checkPointRepository.save(checkPoint);

    return this.checkPointMapper.toDto(response);
  }

  public SummaryDTO getSummary(UUID userId, String jobType){
    long FULLTIME_WORKING_HOURS_IN_MINUTES = 60 * 8;
    long PARTTIME_WORKING_HOURS_IN_MINUTES = 60 * 6;
    jobType = jobType.replaceAll("\"", "");

    LocalDateTime START_OF_DAY = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    LocalDateTime END_OF_DAY = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    
    List<CheckPointEntity> userCheckPoints = this.checkPointRepository.findAllByUserIdAndTimeStampBetweenOrderByTimeStampAsc(userId, START_OF_DAY, END_OF_DAY);

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
