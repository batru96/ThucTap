package model;

/**
 * Created by hoangkhoa on 5/24/17.
 */

public class BanAn {
    private int soBan;
    private int soNguoi;
    private boolean isTrong;

    public BanAn(int soBan, int soNguoi, boolean isTrong) {
        this.soBan = soBan;
        this.soNguoi = soNguoi;
        this.isTrong = isTrong;
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

    public boolean isTrong() {
        return isTrong;
    }

    public void setTrong(boolean trong) {
        isTrong = trong;
    }
}
