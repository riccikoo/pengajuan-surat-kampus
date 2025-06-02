package com.example.suratkampus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "mahasiswa")
public class Mahasiswa extends User {

    @Column(nullable = false, unique = true)
    private String nim;

    @Column(nullable = true)
    private String jurusan;

    public Mahasiswa() {
        this.setRole("MAHASISWA"); // Set role otomatis
    }

    // Getter and Setter
    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }
}
