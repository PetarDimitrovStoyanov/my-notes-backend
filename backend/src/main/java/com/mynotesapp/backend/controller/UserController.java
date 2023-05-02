package com.mynotesapp.backend.controller;

import com.mynotesapp.backend.domain.service.user.UserService;
import com.mynotesapp.backend.dto.user.LoginDto;
import com.mynotesapp.backend.dto.user.RegisterUserDto;
import com.mynotesapp.backend.dto.user.UserDto;
import com.mynotesapp.backend.jwt.JwtToken;
import com.mynotesapp.backend.util.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(Constants.BACKEND + Constants.USERS)
public class UserController {

    private final UserService userService;

    private final JwtToken jwtToken;

    @PostMapping(Constants.REGISTER)
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterUserDto registerDto) {
        UserDto user = userService.register(registerDto);

        return ResponseEntity.ok()
                .headers(jwtToken.addTokenHeader(user.getEmail()))
                .body(user);
    }

    @PostMapping(Constants.LOGIN)
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginDto loginDto) {
        UserDto user = userService.login(loginDto);

        return ResponseEntity.ok()
                .headers(jwtToken.addTokenHeader(user.getEmail()))
                .body(user);
    }

    @GetMapping(Constants.LOGOUT)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
