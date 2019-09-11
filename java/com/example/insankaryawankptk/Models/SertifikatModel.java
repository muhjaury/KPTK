package com.example.insankaryawankptk.Models;

/**
 * Created by muhjaury on 7/17/2019.
 * Email : muhjaury@gmail.com
 */
public class SertifikatModel {

    private String sertifikatTitle;
    private String sertifikatURL;

    public SertifikatModel(String sertifikatTitle, String sertifikatURL) {
        this.sertifikatTitle = sertifikatTitle;
        this.sertifikatURL = sertifikatURL;
    }

    public String getSertifikatTitle() {
        return sertifikatTitle;
    }

    public void setSertifikatTitle(String sertifikatTitle) {
        this.sertifikatTitle = sertifikatTitle;
    }

    public String getSertifikatURL() {
        return sertifikatURL;
    }

    public void setSertifikatURL(String sertifikatURL) {
        this.sertifikatURL = sertifikatURL;
    }

}
