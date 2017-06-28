package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidapp.batru.cafeshop.R;
import model.ChiTiet;
import model.ThongKeHoaDon;
import singleton.Singleton;

public class ChiTietAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private ArrayList<ChiTiet> ds;

    public ChiTietAdapter(Context context, int resource, ArrayList<ChiTiet> ds) {
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(this.resource, null);
        ImageView imgHinhAnh = (ImageView) view.findViewById(R.id.imgHinhAnh);
        TextView txtTen = (TextView) view.findViewById(R.id.txtTen);
        TextView txtSoLuong = (TextView) view.findViewById(R.id.txtSoLuong);
        TextView txtDonGia = (TextView) view.findViewById(R.id.txtDonGia);

        ChiTiet item = ds.get(i);
        if (item != null) {
            if (item.getHinhAnh() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(item.getHinhAnh(), 0, item.getHinhAnh().length);
                imgHinhAnh.setImageBitmap(bitmap);
            }
            txtTen.setText(item.getTenMonAn());
            txtSoLuong.setText("Số lượng: " + item.getSoLuong());
            txtDonGia.setText("Chi tiết: " +
                    Singleton.getInstance().decimalFormat.format(item.getDonGia()) + " đ");
        }

        return view;
    }
}
