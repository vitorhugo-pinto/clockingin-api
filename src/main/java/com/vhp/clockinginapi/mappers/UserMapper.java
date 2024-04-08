package com.vhp.clockinginapi.mappers;

import org.springframework.stereotype.Component;

import com.vhp.clockinginapi.dtos.UserDTO;
import com.vhp.clockinginapi.models.UserEntity;

@Component
public class UserMapper {

    public UserDTO toDto(UserEntity entity) {
        return new UserDTO(
                entity.getId(),
                entity.getLogin(),
                entity.getPassword(),
                entity.getRole(),
                entity.getJobType(),
                entity.getCreatedAt());
    }

    public UserEntity toEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .id(userDTO.id())
                .login(userDTO.login())
                .password(userDTO.password())
                .role(userDTO.role())
                .jobType(userDTO.jobType())
                .createdAt(userDTO.createdAt())
                .build();
    }
}

