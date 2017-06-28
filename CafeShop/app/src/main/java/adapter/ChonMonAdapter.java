package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidapp.batru.cafeshop.R;
import model.ChonMon;
import singleton.Singleton;

/**
 * Created by hoangkhoa on 6/12/17.
 */

public class ChonMonAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private ArrayList<ChonMon> ds;

    public ChonMonAdapter(Context context, int resource, ArrayList<ChonMon> ds) {
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
        convertView = inflater.inflate(this.resource, null);

        TextView txtTen = (TextView) convertView.findViewById(R.id.ten_item);
        TextView txtGia = (TextView) convertView.findViewById(R.id.gia_item);
        ImageButton btnTru = (ImageButton) convertView.findViewById(R.id.btnTru);
        ImageButton btnCong = (ImageButton) convertView.findViewById(R.id.btnCong);
        ImageView imgHinhAnh = (ImageView) convertView.findViewById(R.id.image_listview);
        final TextView txtSoLuong = (TextView) convertView.findViewById(R.id.soLuong);

        final ChonMon chonMon = ds.get(position);

        Bitmap bitmap = Singleton.getInstance().decodeBitmapFromByteArray(chonMon.getHinhAnh());
        imgHinhAnh.setImageBitmap(bitmap);

        txtGia.setText(Singleton.getInstance().decimalFormat.format(chonMon.getGia()) + " đ");
        txtTen.setText(chonMon.getTen() + "");
        txtSoLuong.setText(chonMon.getSoLuong() + "");


        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = Integer.parseInt(txtSoLuong.getText().toString());
                soLuong++;
                chonMon.setSoLuong(soLuong);
                txtSoLuong.setText(chonMon.getSoLuong() + "");
            }
        });

        btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = Integer.parseInt(txtSoLuong.getText().toString() + "");
                if (soLuong > 0) {
                    soLuong--;
                    chonMon.setSoLuong(soLuong);
                }
                txtSoLuong.setText(soLuong + "");
            }
        });

        return convertView;
    }
}
