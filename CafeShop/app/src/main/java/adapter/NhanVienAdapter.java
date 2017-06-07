package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import model.NhanVien;

/**
 * Created by hoangkhoa on 6/7/17.
 */

public class NhanVienAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private ArrayList<NhanVien> ds;

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

        return convertView;
    }
}
