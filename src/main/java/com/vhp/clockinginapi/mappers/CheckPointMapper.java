package com.vhp.clockinginapi.mappers;

import org.springframework.stereotype.Component;

import com.vhp.clockinginapi.dtos.CheckPointDTO;
import com.vhp.clockinginapi.models.CheckPointEntity;

@Component
public class CheckPointMapper {
  public CheckPointDTO toDto(CheckPointEntity entity) {
    return new CheckPointDTO(
            entity.getId(),
            entity.getUser(),
            entity.getUserId(),
            entity.getTimeStamp(),
            entity.getLunchBreak());
}

public CheckPointEntity toEntity(CheckPointDTO checkPointDTO) {
    return CheckPointEntity.builder()
            .id(checkPointDTO.id())
            .user(checkPointDTO.user())
            .userId(checkPointDTO.userId())
            .timeStamp(checkPointDTO.timeStamp())
            .lunchBreak(checkPointDTO.lunchBreak())
            .build();
}
}
