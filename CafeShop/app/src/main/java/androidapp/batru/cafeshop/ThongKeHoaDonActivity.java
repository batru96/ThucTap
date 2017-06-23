package androidapp.batru.cafeshop;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import adapter.HoaDonAdapter;
import model.HoaDon;
import model.ThongKeHoaDon;

public class ThongKeHoaDonActivity extends AppCompatActivity {

    private ListView lvThongKe;
    private ArrayList<ThongKeHoaDon> dsHoaDon;
    private HoaDonAdapter hoaDonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_hoa_don);

        initControls();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadListView();
    }

    private void loadListView() {
        Cursor cursor = MainActivity.db.getData("SELECT * FROM HoaDon WHERE DaThanhToan = 1");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int maHoaDon = cursor.getInt(0);
                int khuyeMai = cursor.getInt(4);
                Cursor cursorTongTien = MainActivity.db.getData("SELECT SoLuong, DonGia FROM ChiTietHoaDon WHERE MaHoaDon = " + maHoaDon);
                long tongTien = 0;
                if (cursorTongTien.getCount() > 0) {
                    while (cursorTongTien.moveToNext()) {
                        int soLuong = cursorTongTien.getInt(0);
                        long donGia = cursorTongTien.getLong(1);
                        tongTien += soLuong * donGia;
                    }
                }
                long tienKhuyenMai = tongTien * khuyeMai / 100;
                tongTien -= tienKhuyenMai;
                int soBan = cursor.getInt(2);
                String thoiGian = cursor.getString(5);
                dsHoaDon.add(new ThongKeHoaDon(maHoaDon, soBan, thoiGian, tongTien));
            }
        }
        hoaDonAdapter = new HoaDonAdapter(this, R.layout.item_thong_ke, dsHoaDon);
        lvThongKe.setAdapter(hoaDonAdapter);
    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Thống kê hóa đơn");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lvThongKe = (ListView) findViewById(R.id.listviewThongKe);
        dsHoaDon = new ArrayList<>();
    }
}
