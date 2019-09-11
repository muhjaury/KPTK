package com.example.insankaryawankptk.Models;

/**
 * Created by muhjaury on 7/16/2019.
 * Email : muhjaury@gmail.com
 */
public class DiklatModel {

    private String diklatTitle;
    private String diklatTempat;
    private String diklatDesc;
    private String diklatStart;
    private String diklatEnd;
    private String diklatOrganizer;

    public DiklatModel(String diklatTitle, String diklatTempat, String diklatDesc, String diklatStart, String diklatEnd, String diklatOrganizer) {
        this.diklatTitle = diklatTitle;
        this.diklatTempat = diklatTempat;
        this.diklatDesc = diklatDesc;
        this.diklatStart = diklatStart;
        this.diklatEnd = diklatEnd;
        this.diklatOrganizer = diklatOrganizer;
    }

    public String getDiklatTitle() {
        return diklatTitle;
    }

    public void setDiklatTitle(String diklatTitle) {
        this.diklatTitle = diklatTitle;
    }

    public String getDiklatTempat() {
        return diklatTempat;
    }

    public void setDiklatTempat(String diklatTempat) {
        this.diklatTempat = diklatTempat;
    }

    public String getDiklatDesc() {
        return diklatDesc;
    }

    public void setDiklatDesc(String diklatDesc) {
        this.diklatDesc = diklatDesc;
    }

    public String getDiklatStart() {
        return diklatStart;
    }

    public void setDiklatStart(String diklatStart) {
        this.diklatStart = diklatStart;
    }

    public String getDiklatEnd() {
        return diklatEnd;
    }

    public void setDiklatEnd(String diklatEnd) {
        this.diklatEnd = diklatEnd;
    }

    public String getDiklatOrganizer() {
        return diklatOrganizer;
    }

    public void setDiklatOrganizer(String diklatOrganizer) {
        this.diklatOrganizer = diklatOrganizer;
    }
}
