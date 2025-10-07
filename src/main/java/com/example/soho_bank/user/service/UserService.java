package com.example.soho_bank.user.service;

import com.example.soho_bank.common.UserCredentialsMissmatchException;
import com.example.soho_bank.common.UserNotFoundException;
import com.example.soho_bank.user.dto.UserLoginDto;
import com.example.soho_bank.user.dto.UserResponseDto;
import com.example.soho_bank.user.dto.UserResponseDtoMapper;
import com.example.soho_bank.user.model.User;
import com.example.soho_bank.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long addUser(User user) {
        // Bcrypt the user password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // save user to DB
        this.userRepository.save(user);
        var userId = this.userRepository.findByEmailIgnoreCase(user.getEmail()).orElseThrow().getId();
        log.info("User created with user id : {}", userId);
        return userId;
    }

    public List<UserResponseDto> getAllUsers() {
        return this.userRepository.findAll().stream()
                .map(UserResponseDtoMapper::userToUserResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long userId) {
        var user =  this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id : " + userId + " not found"));
        return UserResponseDtoMapper.userToUserResponseDto(user);

    }

    public UserResponseDto getLoginStatus(UserLoginDto userLoginDto) {
        var user = this.userRepository.findByEmailIgnoreCase(userLoginDto.getEmail()).orElseThrow(() -> new UserNotFoundException("No user found"));
        if (passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            return UserResponseDtoMapper.userToUserResponseDto(user);
        }
        throw new UserCredentialsMissmatchException("User password does not match");

    }
}
