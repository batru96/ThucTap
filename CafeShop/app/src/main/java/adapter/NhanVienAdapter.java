package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidapp.batru.cafeshop.R;
import model.NhanVien;

public class NhanVienAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private ArrayList<NhanVien> ds;

    public NhanVienAdapter(Context context, int resource, ArrayList<NhanVien> ds) {
        this.context = context;
        this.resource = resource;
        this.ds = ds;
    }

    @Override
    public int getCount() {
        return ds.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(this.resource, null);
        ImageView imgHinhAnh = (ImageView) convertView.findViewById(R.id.imageNhanVien);
        TextView txtTen = (TextView) convertView.findViewById(R.id.nhanVienTenText);
        TextView txtNgayLam = (TextView) convertView.findViewById(R.id.nhanVienDateText);
        TextView txtNghiViec = (TextView) convertView.findViewById(R.id.txtNghiViec);

        NhanVien nhanVien = ds.get(position);
        if (nhanVien != null) {
            txtTen.setText(nhanVien.getTenNhanVien());
            txtNgayLam.setText("Ngày làm việc: " + nhanVien.getNgayLamViec());
            if (nhanVien.getHinhAnh() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(nhanVien.getHinhAnh(), 0, nhanVien.getHinhAnh().length);
                imgHinhAnh.setImageBitmap(bitmap);
            }
            if (nhanVien.isNghiViec()) {
                txtNghiViec.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }
}
