package model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by hoangkhoa on 6/7/17.
 */

public class NhanVien implements Serializable {
    private int maNv;
    private String tenNhanVien;
    private Date ngayLamViec;

    public NhanVien() {

    }

    public NhanVien(int maNv, String tenNhanVien, Date ngayLamViec) {
        this.maNv = maNv;
        this.tenNhanVien = tenNhanVien;
        this.ngayLamViec = ngayLamViec;
    }

    public int getMaNv() {
        return maNv;
    }

    public void setMaNv(int maNv) {
        this.maNv = maNv;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public Date getNgayLamViec() {
        return ngayLamViec;
    }

    public void setNgayLamViec(Date ngayLamViec) {
        this.ngayLamViec = ngayLamViec;
    }
}
