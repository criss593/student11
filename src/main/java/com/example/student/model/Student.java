package com.example.student.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    private String npm;

    private String name;
    private Double gpa;

    // WAJIB: constructor kosong (JPA)
    public Student() {
    }

    // constructor biasa
    public Student(String npm, String name, Double gpa) {
        this.npm = npm;
        this.name = name;
        this.gpa = gpa;
    }

    // ===== GETTER =====
    public String getNpm() {
        return npm;
    }

    public String getName() {
        return name;
    }

    public Double getGpa() {
        return gpa;
    }

    // ===== SETTER (INI YANG KURANG) =====
    public void setNpm(String npm) {
        this.npm = npm;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
}
