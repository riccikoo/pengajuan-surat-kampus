package com.example.suratkampus.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.suratkampus.model.Mahasiswa;
import com.example.suratkampus.model.PengajuanSuratKeterangan;
import com.example.suratkampus.model.StatusPengajuan;
import com.example.suratkampus.model.User;
import com.example.suratkampus.security.CustomUserDetails;
import com.example.suratkampus.repository.PengajuanSuratRepository;

@Controller
public class MahasiswaController {

    private final PengajuanSuratRepository pengajuanSuratRepository;

    MahasiswaController(PengajuanSuratRepository pengajuanSuratRepository) {
        this.pengajuanSuratRepository = pengajuanSuratRepository;
    }

    @GetMapping("/mahasiswa/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        model.addAttribute("user", userDetails.getUser());
        return "mahasiswa/dashboard";
    }

    @GetMapping("/mahasiswa/pengajuan/form")
    public String formPengajuan(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        model.addAttribute("user", userDetails.getUser());
        return "mahasiswa/pengajuan_form";
    }

    @PostMapping("/mahasiswa/pengajuan/save")
    public String savePengajuan(@ModelAttribute PengajuanSuratKeterangan pengajuan,
                                Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (user == null || !"MAHASISWA".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        pengajuan.setMahasiswa((Mahasiswa) user);
        pengajuan.setStatus(StatusPengajuan.PENDING);
        pengajuan.setTanggalPengajuan(new java.util.Date());

        pengajuanSuratRepository.save(pengajuan);
        return "redirect:/mahasiswa/pengajuan/list"; // perbaikan: prefix sesuai endpoint
    }

}
