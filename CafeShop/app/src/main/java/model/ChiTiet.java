package model;

public class ChiTiet {
    private String tenMonAn;
    private int soLuong;
    private long donGia;
    private byte[] hinhAnh;


    public ChiTiet() {
    }

    public ChiTiet(String tenMonAn, int soLuong, long donGia, byte[] hinhAnh) {
        this.tenMonAn = tenMonAn;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.hinhAnh = hinhAnh;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public long getDonGia() {
        return donGia;
    }

    public void setDonGia(long donGia) {
        this.donGia = donGia;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
