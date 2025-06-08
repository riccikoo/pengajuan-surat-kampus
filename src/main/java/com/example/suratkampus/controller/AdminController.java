package com.example.suratkampus.controller;

import com.example.suratkampus.model.PengajuanSurat;
import com.example.suratkampus.model.StatusPengajuan;
import com.example.suratkampus.model.User;
import com.example.suratkampus.repository.PengajuanSuratRepository;
import com.example.suratkampus.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.nio.file.Files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PengajuanSuratRepository pengajuanSuratRepository;

    // Dashboard admin
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        long total = pengajuanSuratRepository.count();
        long pending = pengajuanSuratRepository.countByStatus(StatusPengajuan.PENDING);
        long approved = pengajuanSuratRepository.countByStatus(StatusPengajuan.APPROVED);
        long rejected = pengajuanSuratRepository.countByStatus(StatusPengajuan.REJECTED);

        model.addAttribute("user", user);
        model.addAttribute("total", total);
        model.addAttribute("pending", pending);
        model.addAttribute("approved", approved);
        model.addAttribute("rejected", rejected);

        return "admin/dashboard";
    }

    // List semua pengajuan
    @GetMapping("/pengajuan/list")
    public String listPengajuan(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        List<PengajuanSurat> list = pengajuanSuratRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("pengajuanList", list);

        return "admin/pengajuan_list";
    }

    //Detail Pengajuan
    @GetMapping("/pengajuan/detail/{id}")
    public String detailPengajuan(@PathVariable Long id, Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        PengajuanSurat pengajuan = pengajuanSuratRepository.findById(id).orElse(null);
        if (pengajuan == null) {
            return "redirect:/admin/pengajuan/list";
        }

        model.addAttribute("user", user);
        model.addAttribute("pengajuan", pengajuan);
        return "admin/pengajuan_detail";
    }

    //Accept Surat
    @PostMapping("/pengajuan/upload/{id}")
    public String uploadSurat(@PathVariable Long id,
                            @RequestParam("file") MultipartFile file,
                            Authentication authentication) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        PengajuanSurat pengajuan = pengajuanSuratRepository.findById(id).orElse(null);
        if (pengajuan != null && pengajuan.getStatus() == StatusPengajuan.PENDING) {
            if (!file.getContentType().equals("application/pdf")) {
                return "redirect:/admin/pengajuan/detail/" + id + "?error=FileHarusPDF";
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String tanggal = sdf.format(pengajuan.getTanggalPengajuan());
            String nim = pengajuan.getMahasiswa().getNim();
            String fileName = nim + "-" + tanggal + "-" + pengajuan.getId() + ".pdf";

            Path uploadDir = Paths.get("uploads");
            Files.createDirectories(uploadDir);
            Path filePath = uploadDir.resolve(fileName);
            Files.write(filePath, file.getBytes());

            pengajuan.setFileSurat(fileName);
            pengajuan.setStatus(StatusPengajuan.APPROVED);
            pengajuanSuratRepository.save(pengajuan);
        }

        return "redirect:/admin/pengajuan/detail/" + id;
    }

    @PostMapping("/pengajuan/reject/{id}")
    public String rejectPengajuan(@PathVariable Long id,
                                @RequestParam String alasanReject,
                                Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        PengajuanSurat pengajuan = pengajuanSuratRepository.findById(id).orElse(null);
        if (pengajuan != null && pengajuan.getStatus() == StatusPengajuan.PENDING) {
            pengajuan.setStatus(StatusPengajuan.REJECTED);
            pengajuan.setAlasanReject(alasanReject);
            pengajuanSuratRepository.save(pengajuan);
        }

        return "redirect:/admin/pengajuan/detail/" + id;
    }
}
