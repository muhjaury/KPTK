package com.example.insankaryawankptk.Models;

/**
 * Created by muhjaury on 8/7/2019.
 * Email : muhjaury@gmail.com
 */
public class MyProfileModel {

    private String nama;
    private String jenis_user;

    public MyProfileModel(String nama, String jenis_user) {
        this.nama = nama;
        this.jenis_user = jenis_user;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis_user() {
        return jenis_user;
    }

    public void setJenis_user(String jenis_user) {
        this.jenis_user = jenis_user;
    }
}
