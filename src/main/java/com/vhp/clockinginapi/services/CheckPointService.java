package com.vhp.clockinginapi.services;

import org.springframework.stereotype.Service;

import com.vhp.clockinginapi.dtos.CheckPointDTO;
import com.vhp.clockinginapi.mappers.CheckPointMapper;
import com.vhp.clockinginapi.models.CheckPointEntity;
import com.vhp.clockinginapi.repositories.CheckPointRepository;

@Service
public class CheckPointService {

  private CheckPointRepository checkPointRepository;
  private CheckPointMapper checkPointMapper;

  CheckPointService(CheckPointRepository checkPointRepository, CheckPointMapper checkPointMapper) {
    this.checkPointRepository = checkPointRepository;
    this.checkPointMapper = checkPointMapper;
  }

  public CheckPointDTO create(CheckPointDTO dto){
    CheckPointEntity checkPoint = this.checkPointMapper.toEntity(dto);
    
    CheckPointEntity response = this.checkPointRepository.save(checkPoint);

    return this.checkPointMapper.toDto(response);
  }
  
}
