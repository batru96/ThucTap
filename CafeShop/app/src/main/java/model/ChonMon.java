package model;

import java.io.Serializable;

/**
 * Created by hoangkhoa on 6/12/17.
 */

public class ChonMon implements Serializable {
    private int id;
    private String ten;
    private long gia;
    private int soLuong;
    private byte[] hinhAnh;

    public ChonMon() {

    }

    public ChonMon(int id, String ten, long gia, int soLuong, byte[] hinhAnh) {
        this.id = id;
        this.ten = ten;
        this.gia = gia;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
