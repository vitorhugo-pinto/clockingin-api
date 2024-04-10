package com.vhp.clockinginapi.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vhp.clockinginapi.dtos.UserDTO;
import com.vhp.clockinginapi.mappers.UserMapper;
import com.vhp.clockinginapi.models.UserEntity;
import com.vhp.clockinginapi.repositories.UserRepository;
import com.vhp.clockinginapi.utils.exceptions.LoginAlreadyExists;

@Service
public class UserService {

  private UserRepository userRepository;
  private UserMapper userMapper;
  private PasswordEncoder passwordEncoder;

  UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
  }

  public UserDTO create(UserDTO dto){
    this.userRepository.findByLogin(dto.getLogin()).ifPresent((user) -> {throw new LoginAlreadyExists("Login is already in use");});
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    
    UserEntity user = this.userMapper.toEntity(dto);

    UserEntity response = this.userRepository.save(user);

    return this.userMapper.toDto(response);
  }
}
