package com.vhp.clockinginapi.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.vhp.clockinginapi.dtos.CheckPointDTO;
import com.vhp.clockinginapi.dtos.CheckPointResponseDTO;
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

  public CheckPointResponseDTO toResponseDto(CheckPointEntity entity) {
    return new CheckPointResponseDTO(
            entity.getUserId(),
            entity.getTimeStamp(),
            entity.getLunchBreak());
  }

  public List<CheckPointResponseDTO> toDto (List<CheckPointEntity> list) {
    return list.stream().map(this::toResponseDto).toList();
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
