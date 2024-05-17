package com.example.lastprojectandroid;

public class Doctor {
    private int id;
    private int doctorId;
    private String username;
    private String email;
    private String password;
    private String photo;
    private String medicalMajor;

    // Constructor
    public Doctor(int id, int doctorId, String username, String email, String password, String photo, String medicalMajor) {
        this.id = id;
        this.doctorId = doctorId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.medicalMajor = medicalMajor;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMedicalMajor() {
        return medicalMajor;
    }

    public void setMedicalMajor(String medicalMajor) {
        this.medicalMajor = medicalMajor;
    }
}
