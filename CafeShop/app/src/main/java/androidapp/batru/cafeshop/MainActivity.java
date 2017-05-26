package androidapp.batru.cafeshop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import database.Database;
import model.BanAn;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private LinearLayout layoutContent;

    public static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        khoiTaoDatabase();

        initControls();
    }

    private void khoiTaoDatabase() {
        db = new Database(this, "banhang.sqlite", null, 1);
        db.queryData("CREATE TABLE IF NOT EXISTS NhanVien\n" +
                "(\n" +
                "MaNhanVien INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "TenNhanVien VARCHAR,\n" +
                "NgayLamViec DATETIME DEFAULT CURRENT_DATE\n" +
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
                "HinhAnh INTEGER,\n" +
                "ConHang BOOL\n" +
                ");");

        db.queryData("CREATE TABLE IF NOT EXISTS HoaDon\n" +
                "(\n" +
                "MaHoaDon INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "MaNV INTEGER,\n" +
                "MaBanAn INTEGER,\n" +
                "ThoiGian DATETIME DEFAULT CURRENT_TIME,\n" +
                "FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNhanVien),\n" +
                "FOREIGN KEY (MaBanAn) REFERENCES BanAn(SoBan)\n" +
                ");");

        db.queryData("CREATE TABLE IF NOT EXISTS ChiTietHoaDon\n" +
                "(\n" +
                "MaHoaDon INTEGER,\n" +
                "MaMonAn INTEGER,\n" +
                "SoLuong INTEGER,\n" +
                "PRIMARY KEY (MaHoaDon, MaMonAn),\n" +
                "FOREIGN KEY (MaHoaDon) REFERENCES HoaDon(MaHoaDon),\n" +
                "FOREIGN KEY (MaMonAn) REFERENCES MonAn(MaMonAn)\n" +
                ")");

        //Toast.makeText(this, "Khoi tao database thanh cong!", Toast.LENGTH_SHORT).show();
        //themDuLieuBanAn();
        //themDuLieuNhanVien();
        //themMonAn();
    }

    private void themMonAn() {
        db.queryData("INSERT INTO MonAn VALUES (null, 'Chim cúc chiên bơ', 25000, 'Con', null, 'true');");
        db.queryData("INSERT INTO MonAn VALUES (null, 'Thịt bò khô', 15000, 'Phần', null, 'true');");
        db.queryData("INSERT INTO MonAn VALUES (null, 'Gà hầm thuốc bắc', 275000, 'Con', null, 'true')");
        db.queryData("INSERT INTO MonAn VALUES (null, 'Gòi gà', 30000, 'Phần', null, 'true')");
        Toast.makeText(this, "Them MONAN thanh cong", Toast.LENGTH_SHORT).show();
    }

    private void themDuLieuNhanVien() {
        db.queryData("INSERT INTO NhanVien VALUES (null, 'Cristiano Ronaldo', null);");
        db.queryData("INSERT INTO NhanVien VALUES (null, 'Son Tung MTP', null);");
        db.queryData("INSERT INTO NhanVien VALUES (null, 'My Tam', null);");
        Toast.makeText(this, "Them NHANVIEN thanh cong", Toast.LENGTH_SHORT).show();
    }

    private void themDuLieuBanAn() {
        db.queryData("INSERT INTO BanAn VALUES (null, 0);");
        db.queryData("INSERT INTO BanAn VALUES (null, 3);");
        db.queryData("INSERT INTO BanAn VALUES (null, 0);");
        db.queryData("INSERT INTO BanAn VALUES (null, 2);");
        db.queryData("INSERT INTO BanAn VALUES (null, 2);");
        db.queryData("INSERT INTO BanAn VALUES (null, 0);");
        db.queryData("INSERT INTO BanAn VALUES (null, 0);");
        db.queryData("INSERT INTO BanAn VALUES (null, 1);");
        Toast.makeText(this, "Them du lieu thanh cong", Toast.LENGTH_SHORT).show();
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

        layoutContent = (LinearLayout) findViewById(R.id.listButton);
        //hienThiSoBan();
        fakeBanAn();
    }

    private void fakeBanAn() {
        ArrayList<BanAn> dsBanAn = new ArrayList<>();

        // Doc du lieu tu database, roi hien thi thong tin cac ban an
        Cursor cursor = db.getData("SELECT * FROM BanAn");
        while (cursor.moveToNext()) {
            int idBanAn = cursor.getInt(0);
            int soNguoi = cursor.getInt(1);
            dsBanAn.add(new BanAn(idBanAn, soNguoi));
        }

        int soLuongBanTrenDong = 4;
        int indexLayout = 1, soLuongLayout = dsBanAn.size() / soLuongBanTrenDong;
        int indexBanAn = 1;
        for (int i = 1; i <= soLuongLayout; i++) {
            LinearLayout horizontal = new LinearLayout(this);
            horizontal.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            while (indexBanAn <= soLuongBanTrenDong * indexLayout) {
                final Button button = new Button(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                boolean isConTrong = dsBanAn.get(indexBanAn - 1).getSoNguoi() == 0;
                if (isConTrong) {
                    button.setBackgroundResource(R.drawable.button_ban_trong);
                } else {
                    button.setBackgroundResource(R.drawable.button_co_khach);
                }
                params.setMargins(5, 5, 5, 5);
                button.setLayoutParams(params);
                button.setText("Ban " + indexBanAn);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ChonMonActivity.class);
                        intent.putExtra("id", button.getText().toString());
                        startActivity(intent);
                    }
                });
                horizontal.addView(button);
                indexBanAn++;
            }
            indexLayout++;
            layoutContent.addView(horizontal);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.nav_ban_hang:
                xuLyBanHangClicked();
                break;
            case R.id.nav_thuc_don:
                xuLyThucDonClicked();
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

    private void xuLyThucDonClicked() {
        startActivity(new Intent(MainActivity.this, ThucDonActivity.class));
    }

    private void xuLyBanHangClicked() {
    }
}
