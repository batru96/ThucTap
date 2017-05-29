package model;

import java.io.Serializable;

/**
 * Created by hoangkhoa on 5/24/17.
 */

public class BanAn implements Serializable{
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

    @Override
    public String toString() {
        return "SoBan: " + this.soBan + " -- SoNguoi: " + this.soNguoi;
    }
}
