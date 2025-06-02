package com.example.suratkampus.repository;

import com.example.suratkampus.model.Mahasiswa;
import com.example.suratkampus.model.PengajuanSurat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PengajuanSuratRepository extends JpaRepository<PengajuanSurat, Long> {
    List<PengajuanSurat> findByMahasiswaId(Long mahasiswaId);
}
