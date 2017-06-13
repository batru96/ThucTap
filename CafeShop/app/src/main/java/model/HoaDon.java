package model;

import java.io.Serializable;

/**
 * Created by hoangkhoa on 6/13/17.
 */

public class HoaDon implements Serializable {
    private int maHoaDon;
    private int maNhanVien;
    private int maBanAn;
    private boolean daThanhToan;
    private int khuyenMai;
    private String thoiGian;

    public HoaDon() {
    }

    public HoaDon(int maHoaDon, int maNhanVien, int maBanAn, boolean daThanhToan, int khuyenMai, String thoiGian) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.maBanAn = maBanAn;
        this.daThanhToan = daThanhToan;
        this.khuyenMai = khuyenMai;
        this.thoiGian = thoiGian;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public int getMaBanAn() {
        return maBanAn;
    }

    public void setMaBanAn(int maBanAn) {
        this.maBanAn = maBanAn;
    }

    public boolean isDaThanhToan() {
        return daThanhToan;
    }

    public void setDaThanhToan(boolean daThanhToan) {
        this.daThanhToan = daThanhToan;
    }

    public int getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(int khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }
}
