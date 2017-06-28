package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidapp.batru.cafeshop.R;
import model.MonAn;
import singleton.Singleton;

/**
 * Created by hoangkhoa on 5/26/17.
 */

public class ThucDonAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private ArrayList<MonAn> ds;

    public ThucDonAdapter(Context context, int resource, ArrayList<MonAn> monen) {
        this.context = context;
        this.resource = resource;
        this.ds = monen;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(this.resource, null);
        TextView txtTen = (TextView) convertView.findViewById(R.id.ten_item);
        TextView txtGia = (TextView) convertView.findViewById(R.id.gia_item);
        TextView txtNgungBan = (TextView) convertView.findViewById(R.id.textNgungBan);
        ImageButton btnChiTiet = (ImageButton) convertView.findViewById(R.id.chiTietButton);
        ImageView imgHinhAnh = (ImageView) convertView.findViewById(R.id.image_listview);

        MonAn sanPham = ds.get(position);
        txtTen.setText(sanPham.getTen());
        txtGia.setText(Singleton.getInstance().decimalFormat.format(sanPham.getGia()) + " đ");
        if (!sanPham.isConHang()) {
            txtNgungBan.setVisibility(View.VISIBLE);
        }
        btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
            }
        });

        Bitmap bitmap;
        if (sanPham.getHinhAnh() != null) {
            bitmap = BitmapFactory.decodeByteArray(sanPham.getHinhAnh(), 0, sanPham.getHinhAnh().length);
        } else {
            Drawable drawable = this.context.getResources().getDrawable(R.drawable.profile);
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        imgHinhAnh.setImageBitmap(bitmap);

        return convertView;
    }
}
