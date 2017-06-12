package androidapp.batru.cafeshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;

import adapter.BanAnAdapter;
import database.Database;
import model.BanAn;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Database db;
    public static final String INTENT_BANAN = "BanAn";
    public static final String INTENT_BANMOI = "BanMoi";
    public static String MA_NV = "1";
    private final String TAG = "MainActivity";

    private DrawerLayout drawer;
    private GridView gvBanAn;
    private ArrayList<BanAn> ds;
    private BanAnAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        khoiTaoDatabase();
        initControls();
    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bán hàng");
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        gvBanAn = (GridView) findViewById(R.id.gvBanAn);
        ds = new ArrayList<>();
        ds = docDuLieuTuDatabase();
        adapter = new BanAnAdapter(this, R.layout.item_ban_an, ds);
        gvBanAn.setAdapter(adapter);
    }

    private ArrayList<BanAn> docDuLieuTuDatabase() {
        ArrayList<BanAn> ds = new ArrayList<>();
        Cursor cursor = db.getData("SELECT * FROM BanAn");
        while (cursor.moveToNext()) {
            int soBan = cursor.getInt(0);
            int soNguoi = cursor.getInt(1);
            BanAn banAn = new BanAn(soBan, soNguoi);
            ds.add(banAn);
        }
        return ds;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            taoBanAn();
        }
        return super.onOptionsItemSelected(item);
    }

    private void taoBanAn() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Thông báo");
        dialog.setMessage("Thêm bàn ăn mới?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.queryData("INSERT INTO BanAn VALUES (null, 0)");
                Cursor cursor = db.getData("SELECT * FROM BanAn");
                cursor.moveToLast();
                BanAn banAn = new BanAn(cursor.getInt(0), cursor.getInt(1));
                ds.add(banAn);
                adapter.notifyDataSetChanged();
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_ban_hang:
                xuLyBanHangClicked();
                break;
            case R.id.nav_thuc_don:
                xuLyThucDonClicked();
                break;
            case R.id.nav_nhan_vien:
                xuLyNhanVienClicked();
                break;
            case R.id.nav_thong_ke:
                break;
            case R.id.nav_thong_bao:
                break;
            case R.id.nav_cai_dat:
                break;
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //region myfunction
    private void xuLyNhanVienClicked() {
        startActivity(new Intent(this, NhanVienActivity.class));
    }

    private void xuLyThucDonClicked() {
        startActivity(new Intent(this, ThucDonActivity.class));
    }

    private void xuLyBanHangClicked() {
    }
    //endregion

    //region Database

    private void khoiTaoDatabase() {
        db = new Database(this, "banhang.sqlite", null, 1);
        db.queryData("CREATE TABLE IF NOT EXISTS NhanVien\n" +
                "(\n" +
                "MaNhanVien INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "TenNhanVien VARCHAR,\n" +
                "NgayLamViec DATETIME DEFAULT CURRENT_DATE,\n" +
                "HinhAnh BLOB\n" +
                ");\n");

        db.queryData("CREATE TABLE IF NOT EXISTS BanAn\n" +
                "(\n" +
                "SoBan INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "SoNguoi INTEGER\n" +
                ");");

        db.queryData("CREATE TABLE IF NOT EXISTS MonAn\n" +
                "(\n" +
                "MaMonAn INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "TenMonAn VARCHAR,\n" +
                "DonGia INTEGER,\n" +
                "DonViTinh VARCHAR,\n" +
                "ConHang BOOL,\n" +
                "HinhAnh BLOB" +
                ");");

        db.queryData("CREATE TABLE IF NOT EXISTS HoaDon\n" +
                "(\n" +
                "MaHoaDon INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "MaNV INTEGER,\n" +
                "MaBanAn INTEGER,\n" +
                "DaThanhToan BOOL,\n" +
                "KhuyenMain INTEGER,\n" +
                "ThoiGian DATETIME DEFAULT CURRENT_TIME,\n" +
                "FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNhanVien),\n" +
                "FOREIGN KEY (MaBanAn) REFERENCES BanAn(SoBan)\n" +
                ");");

        db.queryData("CREATE TABLE IF NOT EXISTS ChiTietHoaDon\n" +
                "(\n" +
                "MaHoaDon INTEGER,\n" +
                "MaMonAn INTEGER,\n" +
                "SoLuong INTEGER,\n" +
                "DonGia INTEGER,\n" +
                "PRIMARY KEY (MaHoaDon, MaMonAn),\n" +
                "FOREIGN KEY (MaHoaDon) REFERENCES HoaDon(MaHoaDon),\n" +
                "FOREIGN KEY (MaMonAn) REFERENCES MonAn(MaMonAn)\n" +
                ")");

        //themDuLieuBanAn();
    }
    //endregion
}
