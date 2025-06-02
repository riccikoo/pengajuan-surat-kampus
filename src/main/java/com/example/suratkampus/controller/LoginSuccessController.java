package com.example.suratkampus.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginSuccessController {

    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ROLE_ADMIN".equals(auth.getAuthority())) {
                return "redirect:/admin/dashboard";
            } else if ("ROLE_MAHASISWA".equals(auth.getAuthority())) {
                return "redirect:/mahasiswa/dashboard";
            }
        }
        return "redirect:/login?error";
    }
}
