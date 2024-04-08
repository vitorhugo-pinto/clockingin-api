package com.vhp.clockinginapi.mappers;

import org.springframework.stereotype.Component;

import com.vhp.clockinginapi.dtos.UserDTO;
import com.vhp.clockinginapi.models.UserEntity;

@Component
public class UserMapper {

    public UserDTO toDto(UserEntity entity) {
        return new UserDTO(
                entity.getId(),
                entity.getName(),
                entity.getLogin(),
                entity.getPassword(),
                entity.getRole(),
                entity.getJobType(),
                entity.getCreatedAt());
    }

    public UserEntity toEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .login(userDTO.getLogin())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .jobType(userDTO.getJobType())
                .createdAt(userDTO.getCreatedAt())
                .build();
    }
}

