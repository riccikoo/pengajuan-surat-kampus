package com.example.suratkampus.controller;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.suratkampus.model.*;
import com.example.suratkampus.security.CustomUserDetails;
import com.example.suratkampus.service.PengajuanService;
import com.example.suratkampus.repository.PengajuanSuratRepository;

@Controller
public class MahasiswaController {

    private final PengajuanSuratRepository pengajuanSuratRepository;

    @Autowired
    private PengajuanService pengajuanService;

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
    public String savePengajuan(@RequestParam("jenisSurat") JenisSurat jenisSurat,
                                @RequestParam("keperluan") String keperluan,
                                Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Mahasiswa mahasiswa = (Mahasiswa) userDetails.getUser();

        PengajuanSurat pengajuan = new PengajuanSurat();
        pengajuan.setJenisSurat(jenisSurat);
        pengajuan.setKeperluan(keperluan);
        pengajuan.setMahasiswa(mahasiswa);
        pengajuan.setStatus(StatusPengajuan.PENDING);
        pengajuan.setTanggalPengajuan(new Date());

        pengajuanSuratRepository.save(pengajuan);

        return "redirect:/mahasiswa/pengajuan/list";
    }

    @GetMapping("/mahasiswa/pengajuan/list")
    public String listPengajuan(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Mahasiswa mahasiswa = (Mahasiswa) userDetails.getUser();

        List<PengajuanSurat> daftarPengajuan = pengajuanService.getPengajuanByMahasiswaId(mahasiswa.getId());

        model.addAttribute("user", mahasiswa);
        model.addAttribute("daftarPengajuan", daftarPengajuan);

        return "mahasiswa/pengajuan_list";
    }

    @GetMapping("/mahasiswa/pengajuan/edit/{id}")
    public String formEditPengajuan(@PathVariable Long id, Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Mahasiswa mahasiswa = (Mahasiswa) userDetails.getUser();

        PengajuanSurat pengajuan = pengajuanService.findById(id);

        if (pengajuan == null || !pengajuan.getMahasiswa().getId().equals(mahasiswa.getId())
                || pengajuan.getStatus() != StatusPengajuan.PENDING) {
            return "redirect:/mahasiswa/pengajuan/list";
        }

        model.addAttribute("pengajuan", pengajuan);
        model.addAttribute("user", mahasiswa);
        return "mahasiswa/pengajuan_edit";
    }

    @PostMapping("/mahasiswa/pengajuan/edit")
    public String updatePengajuan(@ModelAttribute PengajuanSurat pengajuan, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Mahasiswa mahasiswa = (Mahasiswa) userDetails.getUser();

        PengajuanSurat existing = pengajuanService.findById(pengajuan.getId());

        if (existing == null || !existing.getMahasiswa().getId().equals(mahasiswa.getId())
                || existing.getStatus() != StatusPengajuan.PENDING) {
            return "redirect:/mahasiswa/pengajuan/list";
        }

        existing.setKeperluan(pengajuan.getKeperluan());
        pengajuanService.save(existing);
        return "redirect:/mahasiswa/pengajuan/list";
    }

    @GetMapping("/mahasiswa/pengajuan/delete/{id}")
    public String deletePengajuan(@PathVariable Long id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Mahasiswa mahasiswa = (Mahasiswa) userDetails.getUser();

        PengajuanSurat pengajuan = pengajuanService.findById(id);

        if (pengajuan != null && pengajuan.getMahasiswa().getId().equals(mahasiswa.getId())
                && pengajuan.getStatus() == StatusPengajuan.PENDING) {
            pengajuanService.deleteById(id);
        }

        return "redirect:/mahasiswa/pengajuan/list";
    }
}
