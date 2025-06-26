package com.plazoleta.msrestaurant.infrastructure.security.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityDetails {
    public static CustomUserDetails getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails;
        }
        return null;
    }
}

