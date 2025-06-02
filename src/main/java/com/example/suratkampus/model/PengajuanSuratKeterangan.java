package com.example.suratkampus.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("KETERANGAN")
public class PengajuanSuratKeterangan extends PengajuanSurat {

    private String keperluan;

    // Getter dan Setter untuk keperluan
    public String getKeperluan() {
        return keperluan;
    }

    public void setKeperluan(String keperluan) {
        this.keperluan = keperluan;
    }
}
