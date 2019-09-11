package com.example.insankaryawankptk.Models;

/**
 * Created by muhjaury on 7/19/2019.
 * Email : muhjaury@gmail.com
 */
public class AbsentModel {
    private String absentArrive;
    private String absentReturn;

    public AbsentModel(String absentArrive, String absentReturn) {
        this.absentArrive = absentArrive;
        this.absentReturn = absentReturn;
    }

    public String getAbsentArrive() {
        return absentArrive;
    }

    public void setAbsentArrive(String absentArrive) {
        this.absentArrive = absentArrive;
    }

    public String getAbsentReturn() {
        return absentReturn;
    }

    public void setAbsentReturn(String absentReturn) {
        this.absentReturn = absentReturn;
    }
}
