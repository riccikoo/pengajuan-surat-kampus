package com.example.suratkampus.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pengajuan_surat")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "jenis_surat")
public abstract class PengajuanSurat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Mahasiswa mahasiswa;

    @Temporal(TemporalType.DATE)
    private Date tanggalPengajuan;

    @Enumerated(EnumType.STRING)
    private StatusPengajuan status;

    @Column(length = 500)
    private String alasanReject;

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

    public String getAlasanReject() {
        return alasanReject;
    }

    public void setAlasanReject(String alasanReject) {
        this.alasanReject = alasanReject;
    }

    public void setFilePath(String filePath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFilePath'");
    }
}
