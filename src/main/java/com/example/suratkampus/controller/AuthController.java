package com.example.suratkampus.controller;

import com.example.suratkampus.model.Mahasiswa;
import com.example.suratkampus.model.User;
import com.example.suratkampus.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    // untuk enkripsi password
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/login")
    public String loginForm(HttpSession session) {
        if (session.getAttribute("user") != null) {
            // jika sudah login, redirect sesuai role
            User user = (User) session.getAttribute("user");
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/mahasiswa/dashboard";
            }
        }
        return "login";  // file login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            System.out.println("Login gagal: username tidak ditemukan -> " + username);
            model.addAttribute("error", "Username tidak ditemukan");
            return "login";
        }

        System.out.println("Login coba: username = " + username);
        System.out.println("Password DB (hash) = " + user.getPassword());
        System.out.println("Password input = " + password);

        boolean matches = passwordEncoder.matches(password, user.getPassword());
        System.out.println("Password match? " + matches);

        if (matches) {
            session.setAttribute("user", user);
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                return "redirect:/admin/dashboard";
            } else if ("MAHASISWA".equalsIgnoreCase(user.getRole())) {
                return "redirect:/mahasiswa/dashboard";
            }
        } else {
            model.addAttribute("error", "Password salah");
        }

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerForm(HttpSession session) {
        if (session.getAttribute("user") != null) {
            // jika sudah login, redirect sesuai role
            User user = (User) session.getAttribute("user");
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/mahasiswa/dashboard";
            }
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Mahasiswa mahasiswa, Model model) {
        if (userRepo.findByUsername(mahasiswa.getUsername()) != null) {
            model.addAttribute("error", "Username sudah digunakan!");
            return "register";
        }
        if (mahasiswa.getPassword() == null || mahasiswa.getPassword().length() < 6) {
            model.addAttribute("error", "Password minimal 6 karakter");
            return "register";
        }

        mahasiswa.setPassword(passwordEncoder.encode(mahasiswa.getPassword()));
        userRepo.save(mahasiswa);

        return "redirect:/login";
    }

}
