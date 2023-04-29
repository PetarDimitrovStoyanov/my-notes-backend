package com.mynotesapp.backend.domain.service.user;

import com.mynotesapp.backend.domain.entity.RoleEntity;
import com.mynotesapp.backend.domain.entity.UserEntity;
import com.mynotesapp.backend.domain.repository.RoleRepository;
import com.mynotesapp.backend.domain.repository.UserRepository;
import com.mynotesapp.backend.dto.user.LoginDto;
import com.mynotesapp.backend.dto.user.RegisterUserDto;
import com.mynotesapp.backend.dto.user.UserDto;
import com.mynotesapp.backend.exception.EntityByNameNotFoundException;
import com.mynotesapp.backend.exception.UserNotFoundException;
import com.mynotesapp.backend.exception.UserRegisteredException;
import com.mynotesapp.backend.mapper.UserMapper;
import com.mynotesapp.backend.util.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper mapper;

    @Override
    @Transactional
    public UserDto register(RegisterUserDto registerDto) {
        UserEntity registeredUser = userRepository.findByEmail(registerDto.getEmail()).orElse(null);

        if (!Objects.isNull(registeredUser)) {
            throw new UserRegisteredException(registeredUser.getEmail());
        }

        UserEntity userEntity = mapper.toEntity(registerDto);
        setUserRoles(userEntity);
        userEntity.setPassword(encryptPassword(registerDto.getPassword()));

        return mapper.toDto(userRepository.save(userEntity));
    }

    private String encryptPassword(String password) {

        return passwordEncoder.encode(password);
    }


    private void setUserRoles(UserEntity userEntity) {
        RoleEntity role = roleRepository
                .findByName(RoleEnum.USER.getName())
                .orElseThrow(() -> new EntityByNameNotFoundException(RoleEntity.class.getSimpleName(), RoleEnum.USER.name()));

        userEntity.setRoles(List.of(role));
    }

    @Override
    @Transactional
    public UserDto login(LoginDto loginDto) {
        UserEntity user = getUser(loginDto.getEmail());

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new UserNotFoundException(loginDto.getEmail());
        }

        setAuthenticationContext(user);
        return mapper.toDto(user);
    }

    @Override
    public void logout() {

    }

    private void setAuthenticationContext(UserEntity user) {
        UserDetails userDetails = loadUserByUsername(user.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private UserEntity getUser(String email) {

        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = getUser(username);

        List<GrantedAuthority> grantedAuthorities = userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                true,
                true,
                true,
                true,
                grantedAuthorities);
    }
}
