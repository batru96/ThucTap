package androidapp.batru.cafeshop;

import android.content.ContentValues;
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
import java.util.HashMap;
import java.util.Map;

import adapter.ChonMonAdapter;
import model.BanAn;
import model.ChonMon;
import model.NhanVien;
import singleton.Singleton;

import static androidapp.batru.cafeshop.MainActivity.db;

public class ChonMonActivity extends AppCompatActivity {

    //region Properties
    private final String TAG = "CHONMON_ACTVITY";

    private ListView lvChonMon;
    private ChonMonAdapter adapter;
    private ArrayList<ChonMon> dsChonMon;

    private Button btnHuy;
    private Button btnCat;
    private Button btnThuTien;

    private Spinner mySpinner;
    private ArrayList<NhanVien> dsNhanVien;
    private ArrayList<String> dsTenNhanVien;
    private ArrayAdapter adapterNhanVien;

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
        dsChonMon = new ArrayList<>();


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
        adapter = new ChonMonAdapter(this, R.layout.item_chon_mon, dsChonMon);
        lvChonMon.setAdapter(adapter);

        dsNhanVien = loadDanhSachNhanVien();
        dsTenNhanVien = new ArrayList<>();
        for (int i = 0; i < dsNhanVien.size(); i++) {
            dsTenNhanVien.add(dsNhanVien.get(i).getTenNhanVien());
        }
        mySpinner = (Spinner) findViewById(R.id.spinnerNhanVien);
        adapterNhanVien = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dsTenNhanVien);
        adapterNhanVien.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mySpinner.setAdapter(adapterNhanVien);
    }

    private ArrayList<NhanVien> loadDanhSachNhanVien() {
        ArrayList<NhanVien> ds = new ArrayList<>();
        Cursor cursor = MainActivity.db.getData("SELECT * FROM NHANVIEN");
        while (cursor.moveToNext()) {
            NhanVien nv = new NhanVien();
            int manv = cursor.getInt(0);
            nv.setMaNv(manv);
            String tenNV = cursor.getString(1);
            nv.setTenNhanVien(tenNV);
            ds.add(nv);
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
        /*cursor = db.getData("SELECT *\n" +
                "FROM MonAn m LEFT JOIN ChiTietHoaDon c\n" +
                "ON m.MaMonAn = c.MaMonAn\n" +
                "WHERE m.ConHang = 1");
        dsChonMon = docDuLieuTuCursor(cursor);*/
        cursor = db.getData("SELECT * FROM CHITIETHOADON WHERE MAHOADON = " + maHoaDon);
        // lay ra mamonan va soluong monan
        HashMap map = new HashMap();
        while(cursor.moveToNext()) {
            Log.v(TAG, "MaHoaDon: " + cursor.getInt(0) + " || MaMonAn: " + cursor.getInt(1) + " || SoLuong: " + cursor.getInt(2) + " || DonGia: " + cursor.getLong(3));
            map.put(cursor.getInt(1), cursor.getInt(2));
        }
        cursor = db.getData("SELECT * FROM MONAN WHERE ConHang = 1");
        while(cursor.moveToNext()) {
            ChonMon chonMon = new ChonMon();

            // Kiem tra ma mon an co bi ton tai trong danh sach tren khong. Neu co thi thay doi so luong.
            int maMon = cursor.getInt(0);
            chonMon.setId(maMon);

            int soluong = 0;
            if (map.containsKey(maMon)) {
                soluong = (int) map.get(maMon);
            }
            chonMon.setSoLuong(soluong);

            String tenmonan = cursor.getString(1);
            chonMon.setTen(tenmonan);

            long gia = cursor.getLong(2);
            chonMon.setGia(gia);

            byte[] hinhanh = cursor.getBlob(5);
            chonMon.setHinhAnh(hinhanh);

            dsChonMon.add(chonMon);
        }
    }

    private void loadThucDonChoBanMoi() {
        Cursor cursor = db.getData("SELECT * FROM MonAn WHERE ConHang = 1");
        dsChonMon = docDuLieuTuCursor(cursor);
    }

    private ArrayList<ChonMon> docDuLieuTuCursor(Cursor cursor) {
        ArrayList<ChonMon> arr = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            long gia = cursor.getLong(2);
            byte[] hinhAnh = cursor.getBlob(5);

            // Kiem tra xem mahoadon khi truy van co bang mahoadon cua bang an hien tai khong
            // Neu co thi lay ra so luong, con khong thi soluong = 0
            int soLuong;
            if (isBanMoi) {
                soLuong = 0;
            } else {
                soLuong = cursor.getInt(6) == maHoaDon ? cursor.getInt(8) : 0;
            }
            arr.add(new ChonMon(id, ten, gia, soLuong, hinhAnh));
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
        int position = (int) mySpinner.getSelectedItemId();
        int maNV = dsNhanVien.get(position).getMaNv();
        if (isBanMoi) {
            ContentValues banAnValues = new ContentValues();
            banAnValues.put("SoNguoi", banAn.getSoNguoi());
            Singleton.getInstance().database.update("BanAn", banAnValues, "SoBan = " + banAn.getSoBan(), null);

            ContentValues hoaDonValues = new ContentValues();
            hoaDonValues.put("MaBanAn", banAn.getSoBan());
            hoaDonValues.put("DaThanhToan", 0);
            hoaDonValues.put("KhuyenMai", 0);
            hoaDonValues.put("MaNV", maNV);
            Singleton.getInstance().database.insert("HoaDon", null, hoaDonValues);

            for (ChonMon chonMon : dsChonMon) {
                if (chonMon.getSoLuong() > 0) {
                    ContentValues chiTietValues = new ContentValues();

                    chiTietValues.put("MaHoaDon", maHoaDon);
                    chiTietValues.put("MaMonAn", chonMon.getId());
                    chiTietValues.put("SoLuong", chonMon.getSoLuong());
                    chiTietValues.put("DonGia", chonMon.getGia());

                    Singleton.getInstance().database.insert("ChiTietHoaDon", null, chiTietValues);
                }
            }
        }
        onBackPressed();
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    //endregion
}
