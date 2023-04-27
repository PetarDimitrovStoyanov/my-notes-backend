package com.mynotesapp.backend.domain.service.user;

import com.mynotesapp.backend.domain.entity.UserEntity;
import com.mynotesapp.backend.domain.repository.UserRepository;
import com.mynotesapp.backend.dto.user.LoginDto;
import com.mynotesapp.backend.dto.user.RegisterUserDto;
import com.mynotesapp.backend.dto.user.UserDto;
import com.mynotesapp.backend.exception.EntityNotFoundException;
import com.mynotesapp.backend.exception.UserNotFoundException;
import com.mynotesapp.backend.exception.UserRegisteredException;
import com.mynotesapp.backend.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    @Override
    @Transactional
    public UserDto register(RegisterUserDto registerDto) {
        UserEntity registeredUser = userRepository.findByEmail(registerDto.getEmail()).orElse(null);

        if (!Objects.isNull(registeredUser)) {
            throw new UserRegisteredException(registeredUser.getEmail());
        }

        UserEntity userEntity = mapper.toEntity(registerDto);

        return mapper.toDto(userRepository.save(userEntity));
    }

    @Override
    public UserDto login(LoginDto loginDto) {
        UserEntity user = userRepository
                .findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(loginDto.getEmail()));

        return mapper.toDto(user);
    }

    @Override
    public UserEntity findById(String id) {

        return userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException(id, UserEntity.class.getSimpleName()));
    }
}
