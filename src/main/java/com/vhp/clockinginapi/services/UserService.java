package com.vhp.clockinginapi.services;

import org.springframework.stereotype.Service;

import com.vhp.clockinginapi.dtos.UserDTO;
import com.vhp.clockinginapi.mappers.UserMapper;
import com.vhp.clockinginapi.models.UserEntity;
import com.vhp.clockinginapi.repositories.UserRepository;

@Service
public class UserService {

  private UserRepository userRepository;
  private UserMapper userMapper;

  UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  public UserDTO create(UserDTO dto){
    UserEntity user = this.userMapper.toEntity(dto);

    UserEntity response = this.userRepository.save(user);

    return this.userMapper.toDto(response);
  }
}
