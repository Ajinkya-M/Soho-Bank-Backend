package com.example.soho_bank.auth.controller;

import com.example.soho_bank.user.dto.LoginResponse;
import com.example.soho_bank.user.dto.UserLoginDto;
import com.example.soho_bank.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserService userService;

    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody UserLoginDto userLoginDto) {
        var user = this.userService.getLoginStatus(userLoginDto);
        var token = jwtService.generateToken(user.getId());
        var loginResponse = LoginResponse.builder()
                .email(user.getEmail())
                .userId(user.getId())
                .token(token)
                .build();
        return ResponseEntity.ok(loginResponse);
    }
}
