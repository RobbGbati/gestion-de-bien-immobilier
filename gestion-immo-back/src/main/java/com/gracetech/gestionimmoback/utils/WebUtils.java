package com.gracetech.gestionimmoback.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import com.gracetech.gestionimmoback.security.UserPrincipal;

public class WebUtils {

    private WebUtils() {}

    public static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal item1) {
            return item1.getUsername();
        }
        return String.valueOf(principal);
    }
}
