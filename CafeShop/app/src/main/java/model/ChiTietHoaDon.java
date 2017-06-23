package model;

import java.io.Serializable;

/**
 * Created by Admin on 6/22/2017.
 */

public class ChiTietHoaDon implements Serializable {
    private String ten;
    private int soluong;
    private long donGia;

    public ChiTietHoaDon() {

    }

    public ChiTietHoaDon(String ten, int soluong, long donGia) {
        this.ten = ten;
        this.soluong = soluong;
        this.donGia = donGia;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public long getDonGia() {
        return donGia;
    }

    public void setDonGia(long donGia) {
        this.donGia = donGia;
    }
}
