package androidapp.batru.cafeshop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.ChiTietHoaDonAdapter;
import model.ChiTietHoaDon;
import singleton.Singleton;

public class HoaDonActivity extends AppCompatActivity {

    private TextView txtSoHoaDon, txtThanhTien;
    private Button btnXong;
    private Button btnHuy;

    private ListView listView;
    private ArrayList<ChiTietHoaDon> ds;
    private ChiTietHoaDonAdapter adapter;

    private Spinner spinKhuyenMai;
    private ArrayList<String> dsKhuyenMai;
    private ArrayAdapter<String> spinAdapter;

    private int maHoaDon;
    private int soBan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon);

        initControls();
        initEvents();
    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Thanh toán");
        setSupportActionBar(toolbar);

        txtSoHoaDon = (TextView) findViewById(R.id.txtSoHoaDon);
        txtThanhTien = (TextView) findViewById(R.id.txtThanhTien);
        btnXong = (Button) findViewById(R.id.btnXong);
        btnHuy = (Button) findViewById(R.id.btnHuy);

        Intent intent = getIntent();
        maHoaDon = intent.getIntExtra(ChonMonActivity.INTENT_MaHoaDon, -1);
        soBan = intent.getIntExtra(ChonMonActivity.INTENT_SoBan, -1);

        listView = (ListView) findViewById(R.id.lvHoaDon);
        ds = new ArrayList<>();

        spinKhuyenMai = (Spinner) findViewById(R.id.spinnerKhuyenMai);
        dsKhuyenMai = new ArrayList<String>();
        for (int i = 0; i <= 90; i += 10) {
            dsKhuyenMai.add(i + "%");
        }
        spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dsKhuyenMai);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinKhuyenMai.setAdapter(spinAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (maHoaDon == -1) {
            Toast.makeText(this, "Khong nhan dc du lieu", Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor cursor = MainActivity.db.getData("SELECT * FROM ChiTietHoaDon WHERE MaHoaDon = " + maHoaDon);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int maMonAn = cursor.getInt(1);
                int soLuong = cursor.getInt(2);
                long donGia = cursor.getLong(3);
                Cursor monAnCursor = MainActivity.db.getData("SELECT TenMonAn FROM MonAn WHERE MaMonAn = " + maMonAn);
                if (monAnCursor.moveToNext()) {
                    String tenMonAn = monAnCursor.getString(0);
                    ds.add(new ChiTietHoaDon(tenMonAn, soLuong, donGia));
                } else {
                    ds.add(new ChiTietHoaDon("", soLuong, donGia));
                }
            }
        } else {
            Toast.makeText(this, "Bàn ăn có không có món gì để thanh toán", Toast.LENGTH_SHORT).show();
        }
        adapter = new ChiTietHoaDonAdapter(this, R.layout.item_chitiet_hoadon, ds);
        listView.setAdapter(adapter);

        txtSoHoaDon.setText("Số hóa đơn: " + maHoaDon);

        spinKhuyenMai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                long tongtien = 0;
                for (ChiTietHoaDon chiTiet : ds) {
                    tongtien += (chiTiet.getDonGia() * chiTiet.getSoluong());
                }
                int khuyenMai = getNumberFromString(dsKhuyenMai.get(i));
                int tienKhuyenMai = (int) (tongtien * khuyenMai / 100);
                tongtien = tongtien - tienKhuyenMai;
                txtThanhTien.setText(Singleton.getInstance().decimalFormat.format(tongtien) + " đ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private int getNumberFromString(String s) {
        String str = s.replace("%", "");
        return Integer.parseInt(str);
    }

    private void initEvents() {
        btnXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThanhToan();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void xuLyThanhToan() {
        if (soBan == -1 || maHoaDon == -1) {
            return;
        }
        int khuyenMai = getNumberFromString(spinKhuyenMai.getSelectedItem().toString());
        MainActivity.db.queryData("UPDATE BanAn SET SoNguoi = 0 WHERE SoBan = " + soBan);
        MainActivity.db.queryData("UPDATE HoaDon SET DaThanhToan = 1, KhuyenMai = " + khuyenMai + " WHERE MaHoaDon = " + maHoaDon);
        startActivity(new Intent(this, MainActivity.class));
    }
}
