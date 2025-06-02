package com.example.suratkampus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin extends User {

    public Admin() {
        this.setRole("ADMIN");
    }

    // Jika ingin menambah field khusus admin, bisa ditambahkan di sini
}
