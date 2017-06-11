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
    private boolean isConHang;
    private byte[] hinhAnh;

    public MonAn() {

    }

    public MonAn(String ten, long gia, String donVi, boolean isConHang, byte[] hinhAnh) {
        this.ten = ten;
        this.gia = gia;
        this.donVi = donVi;
        this.isConHang = isConHang;
        this.hinhAnh = hinhAnh;
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

    public boolean isConHang() {
        return isConHang;
    }

    public void setConHang(boolean conHang) {
        isConHang = conHang;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
