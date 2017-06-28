package adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidapp.batru.cafeshop.R;
import model.ChiTietHoaDon;
import singleton.Singleton;

public class ChiTietHoaDonAdapter extends BaseAdapter{
    private Activity context;
    private int resource;
    private ArrayList<ChiTietHoaDon> ds;

    public ChiTietHoaDonAdapter(Activity context, int resource, ArrayList<ChiTietHoaDon> ds) {
        this.context = context;
        this.resource = resource;
        this.ds = ds;
    }

    @Override
    public int getCount() {
        return ds.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(this.resource, null);

        TextView txtTen = (TextView) view.findViewById(R.id.txtTen);
        TextView txtSoLuong = (TextView) view.findViewById(R.id.txtSoLuong);
        TextView txtDonGia = (TextView) view.findViewById(R.id.txtDonGia);
        TextView txtTongTien = (TextView) view.findViewById(R.id.txtTongTien);

        ChiTietHoaDon chiTietHoaDon = ds.get(i);
        txtTen.setText(chiTietHoaDon.getTen());
        txtSoLuong.setText(chiTietHoaDon.getSoluong() + "");
        txtDonGia.setText(Singleton.getInstance().decimalFormat.format(chiTietHoaDon.getDonGia()) + " đ");
        txtTongTien.setText(Singleton.getInstance().decimalFormat.format(chiTietHoaDon.getSoluong() * chiTietHoaDon.getDonGia()) + "đ");

        return view;
    }
}
