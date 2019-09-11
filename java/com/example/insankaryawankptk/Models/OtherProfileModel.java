package com.example.insankaryawankptk.Models;

/**
 * Created by muhjaury on 8/7/2019.
 * Email : muhjaury@gmail.com
 */
public class OtherProfileModel {

    private String nama;
    private String id;

    public OtherProfileModel(String nama, String id) {
        this.nama = nama;
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
