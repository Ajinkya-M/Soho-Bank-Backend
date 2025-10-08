package com.example.soho_bank.auth.common;

import com.example.soho_bank.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class AuthHelpers {
    public static Long getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Principal : " + principal);
        if (principal instanceof User) {
            return ((User) principal).getId();
        }
        throw new RuntimeException("User not authenticated");
    }

    public static boolean isRoleAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        log.info("isAdmin : " + isAdmin);
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
