package androidapp.batru.cafeshop;

import android.app.Dialog;
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
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import database.Database;
import model.BanAn;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Database db;
    public static final String INTENT_BANAN = "BanAn";
    public static final String INTENT_SOKHACH = "SoKhach";
    private final String TAG = "MainActivity";

    private DrawerLayout drawer;
    private LinearLayout layoutContent;

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

        layoutContent = (LinearLayout) findViewById(R.id.listButton);
        hienThiBanAn();
    }

    private void hienThiBanAn() {
        final ArrayList<BanAn> dsBanAn = new ArrayList<>();

        // Doc du lieu tu database, roi hien thi thong tin cac ban an
        Cursor cursor = db.getData("SELECT * FROM BanAn");
        while (cursor.moveToNext()) {
            int idBanAn = cursor.getInt(0);
            int soNguoi = cursor.getInt(1);
            dsBanAn.add(new BanAn(idBanAn, soNguoi));
        }

        int soCot = 4;
        int soHang = dsBanAn.size() / soCot;
        if (soCot * soHang < dsBanAn.size())
            soHang++;

        int idxButton = 1;
        for (int i = 0; i < soHang; i++) {
            LinearLayout horizontal = new LinearLayout(this);
            horizontal.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < soCot && idxButton < dsBanAn.size(); j++) {
                final Button button = new Button(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(5, 5, 5, 5);
                button.setLayoutParams(params);
                button.setText("Bàn " + (idxButton));

                boolean isConTrong = dsBanAn.get(idxButton).getSoNguoi() == 0;
                if (isConTrong) {
                    button.setBackgroundResource(R.drawable.button_ban_trong);
                } else {
                    button.setBackgroundResource(R.drawable.button_co_khach);
                }

                final int idBanAn = idxButton;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        xuLyBanAnClicked(idBanAn);
//                        Intent intent = new Intent(MainActivity.this, ChonMonActivity.class);
//                        intent.putExtra("id", dsBanAn.get(idBanAn).getSoBan());
//                        startActivity(intent);
                    }
                });

                idxButton++;
                horizontal.addView(button);
            }
            layoutContent.addView(horizontal);
        }
    }

    private void xuLyBanAnClicked(int idBanAn) {
        Cursor cursor = db.getData("SELECT * FROM BanAn WHERE SoBan = " + idBanAn);
        cursor.moveToFirst();
        int soBan = cursor.getInt(0);
        int soNguoi = cursor.getInt(1);
        BanAn banAn = new BanAn(soBan, soNguoi);

        if (soNguoi == 0) {
            khoiTaoDialog(banAn);
        } else {
            Log.v(TAG, "Ban dang co khach");
        }
    }

    private void khoiTaoDialog(final BanAn banAn) {
        final Dialog dialogThemBanAn = new Dialog(this);
        dialogThemBanAn.setContentView(R.layout.dialog_them_ban);
        final EditText edtNhapSoNguoi = (EditText) dialogThemBanAn.findViewById(R.id.edtNhapSoNguoi);
        Button btnHuy = (Button) dialogThemBanAn.findViewById(R.id.btnHuy);
        Button btnXacNhan = (Button) dialogThemBanAn.findViewById(R.id.btnXacNhan);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soKhachMoiVao;
                if (edtNhapSoNguoi.getText().toString().equals("")) {
                    soKhachMoiVao = 1;
                } else {
                    soKhachMoiVao = Integer.parseInt(edtNhapSoNguoi.getText().toString());
                }
                Intent intent = new Intent(MainActivity.this, ChonMonActivity.class);
                intent.putExtra(INTENT_SOKHACH, soKhachMoiVao);
                intent.putExtra(INTENT_BANAN, banAn);
                startActivity(intent);
                //xuLyThemNguoiVaoBanAn(banAn, soKhachMoiVao);
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemBanAn.dismiss();
            }
        });
        dialogThemBanAn.show();
    }

    private void xuLyThemNguoiVaoBanAn(BanAn banAn, int soKhachMoiVao) {
        db.queryData("UPDATE BanAn SET SoNguoi = " + soKhachMoiVao + " WHERE SoBan = " + banAn.getSoNguoi());
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
                layoutContent.removeAllViews();
                hienThiBanAn();
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

        //themDuLieuBanAn();
    }

    private void themDuLieuBanAn() {
        db.queryData("INSERT INTO BanAn VALUES (null, 0);");
        db.queryData("INSERT INTO BanAn VALUES (null, 0);");
        db.queryData("INSERT INTO BanAn VALUES (null, 0);");
        db.queryData("INSERT INTO BanAn VALUES (null, 0);");
        db.queryData("INSERT INTO BanAn VALUES (null, 0);");
        db.queryData("INSERT INTO BanAn VALUES (null, 0);");
        db.queryData("INSERT INTO BanAn VALUES (null, 0);");
        db.queryData("INSERT INTO BanAn VALUES (null, 0);");
        Toast.makeText(this, "Them du lieu thanh cong", Toast.LENGTH_SHORT).show();
    }
    //endregion
}
