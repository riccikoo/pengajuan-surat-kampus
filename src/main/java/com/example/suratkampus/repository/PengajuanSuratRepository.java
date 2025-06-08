package com.example.suratkampus.repository;

import com.example.suratkampus.model.PengajuanSurat;
import com.example.suratkampus.model.StatusPengajuan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PengajuanSuratRepository extends JpaRepository<PengajuanSurat, Long> {

    List<PengajuanSurat> findByMahasiswaId(Long mahasiswaId);

    // Tambahan untuk hitung berdasarkan status
    long countByStatus(StatusPengajuan status);
}
