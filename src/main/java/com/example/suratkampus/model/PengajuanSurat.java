package com.example.suratkampus.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pengajuan_surat")
public class PengajuanSurat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Mahasiswa mahasiswa;

    @Temporal(TemporalType.DATE)
    private Date tanggalPengajuan;

    @Enumerated(EnumType.STRING)
    private StatusPengajuan status;

    @Enumerated(EnumType.STRING)
    private JenisSurat jenisSurat;

    @Column(length = 500)
    private String alasanReject;

    @Column(length = 255)
    private String keperluan;

    // Getter Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    public Date getTanggalPengajuan() {
        return tanggalPengajuan;
    }

    public void setTanggalPengajuan(Date tanggalPengajuan) {
        this.tanggalPengajuan = tanggalPengajuan;
    }

    public StatusPengajuan getStatus() {
        return status;
    }

    public void setStatus(StatusPengajuan status) {
        this.status = status;
    }

    public JenisSurat getJenisSurat() {
        return jenisSurat;
    }

    public void setJenisSurat(JenisSurat jenisSurat) {
        this.jenisSurat = jenisSurat;
    }

    public String getAlasanReject() {
        return alasanReject;
    }

    public void setAlasanReject(String alasanReject) {
        this.alasanReject = alasanReject;
    }

    public String getKeperluan() {
        return keperluan;
    }

    public void setKeperluan(String keperluan) {
        this.keperluan = keperluan;
    }
} 
