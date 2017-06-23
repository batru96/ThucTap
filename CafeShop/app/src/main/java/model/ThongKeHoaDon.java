package model;

import java.io.Serializable;

public class ThongKeHoaDon implements Serializable{
    private int maHoaDon;
    private int maBan;
    private String thoiGian;
    private long tongTien;

    public ThongKeHoaDon() {

    }

    public ThongKeHoaDon(int maHoaDon, int maBan, String thoiGian, long tongTien) {
        this.maHoaDon = maHoaDon;
        this.maBan = maBan;
        this.thoiGian = thoiGian;
        this.tongTien = tongTien;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }
}
