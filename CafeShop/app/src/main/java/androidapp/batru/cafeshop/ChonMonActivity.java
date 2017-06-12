package androidapp.batru.cafeshop;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.ChonMonAdapter;
import model.BanAn;
import model.ChonMon;

public class ChonMonActivity extends AppCompatActivity {

    //region Properties
    private final String TAG = "CHONMON_ACTVITY";

    private ListView lvChonMon;
    private ChonMonAdapter adapter;
    private ArrayList<ChonMon> ds;

    private Button btnHuy;
    private Button btnCat;
    private Button btnThuTien;

    private Intent intent;
    private BanAn banAn;
    private boolean isBanMoi;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_mon);

        initControls();
        initEvents();
    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Chọn món");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnHuy = (Button) findViewById(R.id.buttonHuy);
        btnCat = (Button) findViewById(R.id.buttonCat);
        btnThuTien = (Button) findViewById(R.id.buttonThuTien);

        Intent intent = getIntent();
        banAn = (BanAn) intent.getSerializableExtra(MainActivity.INTENT_BANAN);
        isBanMoi = intent.getBooleanExtra(MainActivity.INTENT_BANMOI, false);

        lvChonMon = (ListView) findViewById(R.id.lvChonMon);
        ds = new ArrayList<>();

        Cursor cursor = MainActivity.db.getData(" SELECT * \n" +
                "FROM MONAN M LEFT JOIN CHITIETHOADON C\n" +
                " ON M.MaMonAn = C.MaMonAn");
        while (cursor.moveToNext()) {
            ChonMon chonMon = new ChonMon();
            int id = cursor.getInt(0);
            chonMon.setId(id);

            String ten = cursor.getString(1);
            chonMon.setTen(ten);

            long gia = cursor.getLong(2);
            chonMon.setGia(gia);

            byte[] hinhAnh = cursor.getBlob(5);
            chonMon.setHinhAnh(hinhAnh);

            int soLuong = cursor.getInt(8);
            chonMon.setSoLuong(soLuong);

            ds.add(chonMon);
        }
        adapter = new ChonMonAdapter(this, R.layout.item_chon_mon, ds);
        lvChonMon.setAdapter(adapter);
    }

    private void initEvents() {
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyHuy();
            }
        });

        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyCat();
            }
        });

        btnThuTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThuTien();
            }
        });
    }

    //region MyFunction
    private void xuLyCat() {
        if (isBanMoi) {
            //Ban moi, tao hoa don moi, cap nhat so nguoi cho ban an, tao chitiethoadon moi tuong ung
            SQLiteDatabase database = MainActivity.db.getWritableDatabase();
            ContentValues hoaDonValues = new ContentValues();
            hoaDonValues.put("MaBanAn", banAn.getSoBan());
            hoaDonValues.put("DaThanhToan", 0);
            hoaDonValues.put("KhuyenMai", 0);
            hoaDonValues.put("MaNV", MainActivity.MA_NV); // Chua lam dang nhap cho nguoi su dung
            database.insert("HoaDon", null, hoaDonValues);

            ContentValues banAnValues = new ContentValues();
            banAnValues.put("SoNguoi", banAn.getSoNguoi());
            database.update("BanAn", banAnValues, "SoBan = " + banAn.getSoBan(), null);

//            Cursor cursor = MainActivity.db.getData("SELECT * FROM HoaDon");
//            cursor.moveToLast();
//            int maHoaDon = cursor.getInt(0);
//            for (int i = 0; i < ds.size(); i++) {
//                ContentValues chiTietValues = new ContentValues();
//            }
            startActivity(new Intent(this, MainActivity.class));

        } else {
            // Khi ban dang co khach, chi cap nhat lai chitiethoadon
        }
    }

    private void xuLyHuy() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void xuLyThuTien() {
        Toast.makeText(this, "THU TIEN NE!!!", Toast.LENGTH_SHORT).show();
    }

    //endregion

    //region Override Function
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chon_mon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_tinh_tien:
                xuLyThuTien();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion
}
