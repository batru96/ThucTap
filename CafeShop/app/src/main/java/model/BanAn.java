package model;

/**
 * Created by hoangkhoa on 5/24/17.
 */

public class BanAn {
    private int soBan;
    private int soNguoi;

    public BanAn(int soBan, int soNguoi) {
        this.soBan = soBan;
        this.soNguoi = soNguoi;
    }

    public int getSoBan() {
        return soBan;
    }

    public void setSoBan(int soBan) {
        this.soBan = soBan;
    }

    public int getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(int soNguoi) {
        this.soNguoi = soNguoi;
    }
}
