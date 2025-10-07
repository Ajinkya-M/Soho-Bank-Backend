package com.example.soho_bank.user.dto;


import com.example.soho_bank.user.model.User;

public class UserResponseDtoMapper {
    public static UserResponseDto userToUserResponseDto(User user) {
        return UserResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .id(user.getId())
                .build();
    }
}
