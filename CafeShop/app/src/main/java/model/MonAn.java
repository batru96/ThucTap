package model;

import java.io.Serializable;

/**
 * Created by hoangkhoa on 5/24/17.
 */

public class MonAn implements Serializable {
    private int id;
    private String ten;
    private long gia;
    private String donVi;
    private String hinhAnh;
    private boolean isConHang;

    public MonAn(int id, String ten, long gia, String donVi, String hinhAnh, boolean isConHang) {
        this.ten = ten;
        this.gia = gia;
        this.donVi = donVi;
        this.hinhAnh = hinhAnh;
        this.id = id;
        this.isConHang = isConHang;
    }

    public MonAn(String ten, long gia, String donVi, boolean isConHang) {
        this.ten = ten;
        this.gia = gia;
        this.donVi = donVi;
        this.isConHang = isConHang;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public boolean isConHang() {
        return isConHang;
    }

    public void setConHang(boolean conHang) {
        isConHang = conHang;
    }
}