package com.example.lastprojectandroid;

public class Student {
    private int id;
    private int bzu_id;
    private String username;
    private String email;
    private String password;

    public Student(int id, int bzu_id, String username, String email, String password, String photo) {
        this.id = id;
        this.bzu_id = bzu_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBzu_id() {
        return bzu_id;
    }

    public void setBzu_id(int bzu_id) {
        this.bzu_id = bzu_id;
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

    private String photo;
}
