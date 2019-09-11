package com.example.insankaryawankptk.Models;

/**
 * Created by muhjaury on 6/26/2019
 * Email : muhjaury@gmail.com
 **/
public class RSSModel {

    private String txtTitle;
    private String txtDesc;
    private String txtUrl;
    private String imageView;

    public RSSModel(String txtTitle, String txtDesc, String txtUrl, String imageView) {
        this.txtTitle = txtTitle;
        this.txtDesc = txtDesc;
        this.txtUrl = txtUrl;
        this.imageView = imageView;
    }

    public String getTxtTitle() {
        return txtTitle;
    }

    public void setTxtTitle(String txtTitle) {
        this.txtTitle = txtTitle;
    }

    public String getTxtDesc() {
        return txtDesc;
    }

    public void setTxtDesc(String txtDesc) {
        this.txtDesc = txtDesc;
    }

    public String getTxtUrl() {
        return txtUrl;
    }

    public void setTxtUrl(String txtUrl) {
        this.txtUrl = txtUrl;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }
}
