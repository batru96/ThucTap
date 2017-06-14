package androidapp.batru.cafeshop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.ChonMonAdapter;
import model.BanAn;
import model.ChonMon;

import static androidapp.batru.cafeshop.MainActivity.db;

public class ChonMonActivity extends AppCompatActivity {

    //region Properties
    private final String TAG = "CHONMON_ACTVITY";

    private ListView lvChonMon;
    private ChonMonAdapter adapter;
    private ArrayList<ChonMon> ds;

    private Button btnHuy;
    private Button btnCat;
    private Button btnThuTien;
    private Spinner mySpinner;

    private Intent intent;
    private BanAn banAn;
    private boolean isBanMoi;

    private int maHoaDon;
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


        /*
        * Kiem tra xem ban an dang co khach hay khong
        * - Neu ban an khong co khach, load het cac mon an da ton tai thucdon voi dieu kien mon an do
        * van con.
        * - Neu ban an dang co khach(tuc la ban da co hoa don roi -> MaHoaDon), load danh sach cac mon an voi
        * cau truy van "MonAn m LEFT JOIN ChiTietHoaDon c ON m.MaMonAn = c.MaMonAn
        * WHERE c.MaHoaDon = 'MaHoaDon' OR c.MaHoaDon IS NULL"
        *
        * */
        if (isBanMoi) {
            loadThucDonChoBanMoi();
        } else {
            loadThucDonChoBanCoKhach();
        }
        adapter = new ChonMonAdapter(this, R.layout.item_chon_mon, ds);
        lvChonMon.setAdapter(adapter);

        mySpinner = (Spinner) findViewById(R.id.spinnerNhanVien);
        ArrayList<String> dsNhanVien = loadDanhSachTenNhanVien();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dsNhanVien);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mySpinner.setAdapter(adapter);
    }

    private ArrayList<String> loadDanhSachTenNhanVien() {
        ArrayList<String> ds = new ArrayList<>();
        Cursor cursor = MainActivity.db.getData("SELECT TenNhanVien FROM NHANVIEN");
        while (cursor.moveToNext()) {
            ds.add(cursor.getString(0));
        }
        return ds;
    }

    private void loadThucDonChoBanCoKhach() {
        Cursor cursor = db.getData("SELECT h.MaHoaDon FROM BanAn b JOIN HoaDon h ON b.SoBan = h.MaBanAn WHERE h.DaThanhToan = 0 AND h.MaBanAn = " + banAn.getSoBan());
        if (!cursor.moveToNext()) {
            Log.d(TAG, "Cursor Failed");
            return;
        }
        maHoaDon = cursor.getInt(0);
        cursor = db.getData("SELECT m.MaMonAn, m.TenMonAn, m.DonGia, m.ConHang, m.HinhAnh, c.SoLuong\n" +
                "FROM MonAn m LEFT JOIN ChiTietHoaDon c\n" +
                "ON m.MaMonAn = c.MaMonAn WHERE c.MaHoaDon IS NULL OR c.MaHoaDon = " + maHoaDon);
        ds = docDuLieuTuCursor(cursor);
    }

    private void loadThucDonChoBanMoi() {
        Cursor cursor = db.getData("SELECT m.MaMonAn, m.TenMonAn, m.DonGia, m.ConHang, m.HinhAnh, c.SoLuong\n" +
                "FROM MonAn m LEFT JOIN ChiTietHoaDon c\n" +
                "ON m.MaMonAn = c.MaMonAn");
        ds = docDuLieuTuCursor(cursor);
    }
    
    private ArrayList<ChonMon> docDuLieuTuCursor(Cursor cursor) {
        ArrayList<ChonMon> arr = new ArrayList<>();
        while (cursor.moveToNext()) {
            boolean isConHang = cursor.getString(3).equals("1");
            if (isConHang) {
                int maMonAn = cursor.getInt(0);
                String tenMonAn = cursor.getString(1);
                long gia = cursor.getLong(2);
                byte[] hinhAnh = cursor.getBlob(4);
                int soLuong = cursor.getInt(5);
                arr.add(new ChonMon(maMonAn, tenMonAn, gia, soLuong, hinhAnh));
            }
        }
        return arr;
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
//        if (isBanMoi) {
//            //Ban moi, tao hoa don moi, cap nhat so nguoi cho ban an, tao chitiethoadon moi tuong ung
//            ContentValues hoaDonValues = new ContentValues();
//            hoaDonValues.put("MaBanAn", banAn.getSoBan());
//            hoaDonValues.put("DaThanhToan", 0);
//            hoaDonValues.put("KhuyenMain", 0);
//            hoaDonValues.put("MaNV", 1); // Chua lam dang nhap cho nguoi su dung
//            Singleton.getInstance().database.insert("HoaDon", null, hoaDonValues);
//
//            ContentValues banAnValues = new ContentValues();
//            banAnValues.put("SoNguoi", banAn.getSoNguoi());
//            long kqAdd = Singleton.getInstance().database.update("BanAn", banAnValues, "SoBan = " + banAn.getSoBan(), null);
//            Log.v(TAG, "So luong dong add vao table ban an: " + kqAdd);
//
//            Cursor cursor = db.getData("SELECT m.MaMonAn, m.TenMonAn, m.DonGia, m.HinhAnh, c.SoLuong\n" +
//                    "FROM MonAn m LEFT JOIN ChiTietHoaDon c\n" +
//                    "On m.MaMonAn = c.MaMonAn\n" +
//                    "WHERE c.MaHoaDon = " + " OR c.MaHoaDon is NULL");
//            while (cursor.moveToNext()) {
//                int maMonAn = cursor.getInt(0);
//                String tenMonAn = cursor.getString(1);
//                long donGia = cursor.getLong(2);
//                byte[] hinhAnh = cursor.getBlob(3);
//                int soLuong = cursor.getInt(4);
//                ds.add(new ChonMon(maMonAn, tenMonAn, donGia, soLuong, hinhAnh));
//            }
//            startActivity(new Intent(this, MainActivity.class));
//
//        } else {
//            // Khi ban dang co khach, chi cap nhat lai chitiethoadon
//        }
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
