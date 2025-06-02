package com.example.suratkampus.service;

import com.example.suratkampus.model.PengajuanSurat;
import com.example.suratkampus.repository.PengajuanSuratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PengajuanService {

    @Autowired
    private PengajuanSuratRepository pengajuanRepository;

    public List<PengajuanSurat> getAllPengajuan() {
        return pengajuanRepository.findAll();
    }

    public List<PengajuanSurat> getPengajuanByMahasiswaId(Long mahasiswaId) {
        return pengajuanRepository.findByMahasiswaId(mahasiswaId);
    }

    public PengajuanSurat save(PengajuanSurat pengajuan) {
        return pengajuanRepository.save(pengajuan);
    }

    public PengajuanSurat findById(Long id) {
        return pengajuanRepository.findById(id).orElse(null);
    }
}
