package androidapp.batru.cafeshop;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import adapter.HoaDonAdapter;
import model.ThongKeHoaDon;

public class BaoCaoActivity extends AppCompatActivity {

    public static final String INTENT_MaHoaDon = "MaHoaDon";
    public static final String INTENT_KhuyenMai = "KhuyenMai";

    private Spinner spinner;
    private ArrayList<String> dsSpinner;
    private ArrayAdapter<String> adapterSpin;

    private ListView lvBaoCao;
    private ArrayList<ThongKeHoaDon> dsThongKe;
    private HoaDonAdapter adapter;

    private TextView txtTongThu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao);

        initControls();
        addEvents();
    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Báo cáo");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtTongThu = (TextView) findViewById(R.id.txtTongThu);

        spinner = (Spinner) findViewById(R.id.spinnerBaoCao);
        dsSpinner = new ArrayList<>();
        dsSpinner.add("Hôm nay");
        dsSpinner.add("Hôm qua");
        dsSpinner.add("Tất cả");
        adapterSpin = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dsSpinner);
        adapterSpin.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapterSpin);

        lvBaoCao = (ListView) findViewById(R.id.lvBaoCao);
        dsThongKe = new ArrayList<>();
        adapter = new HoaDonAdapter(this, R.layout.item_thong_ke, dsThongKe);
        lvBaoCao.setAdapter(adapter);
    }

    private void addEvents() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadListView(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lvBaoCao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listviewItemClick(i);
            }
        });
    }

    private void listviewItemClick(int position) {
        int maHoaDon = dsThongKe.get(position).getMaHoaDon();
        Cursor cursor = MainActivity.db.getData("SELECT KhuyenMai FROM HoaDon WHERE MaHoaDon = " + maHoaDon);
        if (cursor.moveToNext()) {
            int khuyenMai = cursor.getInt(0);
            Intent intent = new Intent(this, BaoCaoChiTietActivity.class);
            intent.putExtra(INTENT_MaHoaDon, dsThongKe.get(position).getMaHoaDon());
            intent.putExtra(INTENT_KhuyenMai, khuyenMai);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadListView(int position) {
        Calendar dateQuery = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Cursor cursor = null;
        switch (position) {
            case 0:
                String currentDate = formater.format(dateQuery.getTime());
                cursor = MainActivity.db.getData("SELECT * FROM HoaDon WHERE DaThanhToan = 1 AND DATE(ThoiGian) = '" + currentDate + "'");
                break;
            case 1:
                dateQuery.add(Calendar.DATE, -1);
                String yesterdayDate = formater.format(dateQuery.getTime());
                cursor = MainActivity.db.getData("SELECT * FROM HoaDon WHERE DaThanhToan = 1 AND DATE(ThoiGian) = '" + yesterdayDate + "'");
                break;
            case 2:
                cursor = MainActivity.db.getData("SELECT * FROM HoaDon WHERE DaThanhToan = 1");
                break;
            default:
                break;
        }
        if (cursor != null) {
            dsThongKe.clear();
            int tongThu = 0;
            while (cursor.moveToNext()) {
                int maHoaDon = cursor.getInt(0);
                int khuyenMai = cursor.getInt(4);
                Cursor cursorTongTien = MainActivity.db.getData("SELECT SoLuong, DonGia FROM ChiTietHoaDon WHERE MaHoaDon = " + maHoaDon);
                long tongTien = 0;
                if (cursorTongTien.getCount() > 0) {
                    while (cursorTongTien.moveToNext()) {
                        int soLuong = cursorTongTien.getInt(0);
                        long donGia = cursorTongTien.getLong(1);
                        tongTien += soLuong * donGia;
                    }
                }
                long tienKhuyenMai = tongTien * khuyenMai / 100;
                tongTien -= tienKhuyenMai;
                int soBan = cursor.getInt(2);
                String thoiGian = cursor.getString(5);
                tongThu += tongTien;
                dsThongKe.add(new ThongKeHoaDon(maHoaDon, soBan, thoiGian, tongTien));
            }
            if (dsThongKe.size() > 0)
                adapter.notifyDataSetChanged();
            else {
                // Chua chac dung
                adapter = new HoaDonAdapter(this, R.layout.item_thong_ke, dsThongKe);
                lvBaoCao.setAdapter(adapter);
            }
            txtTongThu.setText("Tổng thu: " + tongThu + " đ");
        }
    }
}
