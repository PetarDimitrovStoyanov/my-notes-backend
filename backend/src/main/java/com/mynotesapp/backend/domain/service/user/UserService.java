package com.mynotesapp.backend.domain.service.user;

import com.mynotesapp.backend.domain.entity.UserEntity;
import com.mynotesapp.backend.dto.user.LoginDto;
import com.mynotesapp.backend.dto.user.RegisterUserDto;
import com.mynotesapp.backend.dto.user.UserDto;

public interface UserService {
    UserDto register(RegisterUserDto registerDto);

    UserDto login(LoginDto loginDto);

    UserEntity findById(String id);
}
