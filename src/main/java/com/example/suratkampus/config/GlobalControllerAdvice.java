package com.example.suratkampus.config;

import com.example.suratkampus.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute("user")
    public User user(HttpSession session) {
        return (User) session.getAttribute("user");
    }
}

