package com.example.soho_bank.user.controller;

import com.example.soho_bank.user.dto.UserLoginDto;
import com.example.soho_bank.user.dto.UserResponseDto;
import com.example.soho_bank.user.model.User;
import com.example.soho_bank.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public Long addUser(@Validated @RequestBody User user) {
        return this.userService.addUser(user);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers(){
        if (!isAdmin()) throw new AccessDeniedException("Access denied for non admins");
        return this.userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserId(@PathVariable Long userId) {
        var currentUser = this.getCurrentUserId();
        if (!currentUser.equals(userId) && !isAdmin()) {
            throw new RuntimeException("User access forbidden");
        }
        var user =  this.userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    private Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Principal : " + principal);
        if (principal instanceof User) {
            return ((User) principal).getId();
        }
        throw new RuntimeException("User not authenticated");
    }

    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        log.info("isAdmin : " + isAdmin);
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

}
