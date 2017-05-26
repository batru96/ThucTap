package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidapp.batru.cafeshop.R;
import model.MonAn;

/**
 * Created by hoangkhoa on 5/25/17.
 */

public class ChonMonAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private ArrayList<MonAn> ds;

    public ChonMonAdapter(Context context, int resource, ArrayList<MonAn> ds) {
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(resource, null);

        ImageView imageListView = (ImageView) convertView.findViewById(R.id.image_listview);
        TextView txtTen = (TextView) convertView.findViewById(R.id.ten_item);
        TextView txtGia = (TextView) convertView.findViewById(R.id.gia_item);
        ImageButton btnTru = (ImageButton) convertView.findViewById(R.id.btnTru);
        ImageButton btnCong = (ImageButton) convertView.findViewById(R.id.btnCong);
        final TextView txtSoLuong = (TextView) convertView.findViewById(R.id.soLuong);

        MonAn monAn = ds.get(position);
        txtGia.setText(monAn.getGia() + "đ");
        txtTen.setText(monAn.getTen() + "");
        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = Integer.parseInt(txtSoLuong.getText().toString());
                soLuong++;
                txtSoLuong.setText(soLuong + "");
            }
        });

        btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = Integer.parseInt(txtSoLuong.getText().toString());
                if (soLuong > 0) {
                    soLuong--;
                }
                txtSoLuong.setText(soLuong + "");
            }
        });

        return convertView;
    }
}
