package com.example.smart_aqua;

public class data {
    private int Hinh,ASang,Nhiet;
    private String TGian;

    public data(int hinh, int ASang, int nhiet, String TGian) {
        Hinh = hinh;
        this.ASang = ASang;
        Nhiet = nhiet;
        this.TGian = TGian;
    }

    public int getHinh() {
        return Hinh;
    }

    public void setHinh(int hinh) {
        Hinh = hinh;
    }

    public int getASang() {
        return ASang;
    }

    public void setASang(int ASang) {
        this.ASang = ASang;
    }

    public int getNhiet() {
        return Nhiet;
    }

    public void setNhiet(int nhiet) {
        Nhiet = nhiet;
    }

    public String getTGian() {
        return TGian;
    }

    public void setTGian(String TGian) {
        this.TGian = TGian;
    }
}
