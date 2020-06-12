package com.example.smart_aqua;

public class Entrie {
    public String date;
    public int tempTB;

    public Entrie(String date, int tempTB) {
        this.date = date;
        this.tempTB = tempTB;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTempTB() {
        return tempTB;
    }

    public void setTempTB(int tempTB) {
        this.tempTB = tempTB;
    }
}

