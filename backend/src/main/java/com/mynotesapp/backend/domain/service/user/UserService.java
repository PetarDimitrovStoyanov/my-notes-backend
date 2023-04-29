package com.mynotesapp.backend.domain.service.user;

import com.mynotesapp.backend.dto.user.LoginDto;
import com.mynotesapp.backend.dto.user.RegisterUserDto;
import com.mynotesapp.backend.dto.user.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto register(RegisterUserDto registerDto);

    UserDto login(LoginDto loginDto);

    void logout();
}
