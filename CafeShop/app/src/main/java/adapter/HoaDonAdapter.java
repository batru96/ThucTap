package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidapp.batru.cafeshop.R;
import model.ChonMon;
import model.ThongKeHoaDon;
import singleton.Singleton;

public class HoaDonAdapter extends BaseAdapter{

    private Context context;
    private int resource;
    private ArrayList<ThongKeHoaDon> ds;

    public HoaDonAdapter(Context context, int resource, ArrayList<ThongKeHoaDon> ds) {
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

        TextView txtMaHoaDon = (TextView) view.findViewById(R.id.txtSoHoaDon);
        TextView txtSoBan = (TextView) view.findViewById(R.id.txtSoBan);
        TextView txtThoiGian = (TextView) view.findViewById(R.id.txtThoiGian);
        TextView txtTongTien = (TextView) view.findViewById(R.id.txtTongTien);

        ThongKeHoaDon item = ds.get(i);
        txtMaHoaDon.setText("Số hóa đơn: " + item.getMaHoaDon());
        txtSoBan.setText("Số bàn: " + item.getMaBan());
        txtThoiGian.setText(item.getThoiGian());
        txtTongTien.setText("Tổng tiền: " + item.getTongTien() + " đ");
        return view;
    }
}
