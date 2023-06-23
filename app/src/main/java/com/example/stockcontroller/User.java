package com.example.stockcontroller;

public class User {
    private String nama, email, phone;

    public User(String nama, String email, String phone) {
        this.nama = nama;
        this.email = email;
        this.phone = phone;
    }

    public User() {

    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
