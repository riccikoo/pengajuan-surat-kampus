package com.example.suratkampus.controller;

import com.example.suratkampus.model.Mahasiswa;
import com.example.suratkampus.model.PengajuanSurat;
import com.example.suratkampus.model.StatusPengajuan;
import com.example.suratkampus.model.User;
import com.example.suratkampus.repository.PengajuanSuratRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pengajuan")
public class PengajuanController {

    @Autowired
    private PengajuanSuratRepository pengajuanRepo;

    // Admin melihat semua pengajuan
    @GetMapping("/admin/list")
    public String listPengajuanAdmin(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        List<PengajuanSurat> list = pengajuanRepo.findAll();
        model.addAttribute("pengajuanList", list);
        return "pengajuan_list_admin"; // file pengajuan_list_admin.html
    }

    // Admin approve pengajuan
    @PostMapping("/admin/{id}/approve")
    public String approvePengajuan(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        PengajuanSurat p = pengajuanRepo.findById(id).orElse(null);
        if (p != null) {
            p.setStatus(com.example.suratkampus.model.StatusPengajuan.APPROVED);
            pengajuanRepo.save(p);
        }
        return "redirect:/pengajuan/admin/list";
    }

    // Admin reject pengajuan dengan alasan
    @PostMapping("/admin/{id}/reject")
    public String rejectPengajuan(@PathVariable Long id,
                                  @RequestParam String alasanReject,
                                  HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        PengajuanSurat p = pengajuanRepo.findById(id).orElse(null);
        if (p != null) {
            p.setStatus(com.example.suratkampus.model.StatusPengajuan.REJECTED);
            p.setAlasanReject(alasanReject);
            pengajuanRepo.save(p);
        }
        return "redirect:/pengajuan/admin/list";
    }

}
