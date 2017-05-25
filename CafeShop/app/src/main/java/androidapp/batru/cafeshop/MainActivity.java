package androidapp.batru.cafeshop;

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

import database.Database;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private LinearLayout layoutContent;

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        khoiTaoDatabase();
        //addDefaultValue();
        initControls();
    }

    private void hienThiSoBan() {
        Cursor data = db.getData("SELECT * FROM BanAn");

        int soLuongButtonTrenDong = 4;
        int indexLayout = 1, soLuongLayout = data.getCount() / soLuongButtonTrenDong;
        int indexButton = 1;

        for (int i = 1; i <= soLuongLayout; i++) {
            LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            while(indexButton <= soLuongButtonTrenDong * indexLayout) {
                final Button button = new Button(this);
                button.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                button.setText("Ban " + indexButton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenBan = button.getText().toString();
                        String soBan = tenBan.replaceAll("[^0-9]", "");
                        int idBan = Integer.parseInt(soBan) - 1;
                        Toast.makeText(MainActivity.this, idBan + "", Toast.LENGTH_SHORT).show();
                    }
                });
                horizontalLayout.addView(button);
                indexButton++;
            }
            indexLayout++;
            layoutContent.addView(horizontalLayout);
        }
    }

    private void addDefaultValue() {
        db.queryData("INSERT INTO ThucDon VALUES (null, 'Chim cuc chien bo', 15000, 'Cai', null, 'TRUE')");
        Cursor data = db.getData("SELECT * FROM ThucDon");
        while (data.moveToNext()) {
            String name = data.getString(1);
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
    }

    private void khoiTaoDatabase() {
        db = new Database(this, "BanHang.sqlite", null, 1);

        db.queryData("CREATE TABLE IF NOT EXISTS ThucDon" +
                "(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Ten VARCHAR," +
                "Gia INTEGER," +
                "DonViTinh VARCHAR," +
                "HinhAnh TEXT," +
                "ConHang BOOL)");

        db.queryData("CREATE TABLE IF NOT EXISTS BanAn" +
                "(" +
                "SoBan INTEGER PRIMARY KEY AUTOINCREMENT," +
                "SoNguoi INTEGER DEFAULT 0," +
                "ConTrong BOOL DEFAULT TRUE" +
                ")");

        db.queryData("CREATE TABLE IF NOT EXISTS BanAn_MonAn(" +
                "SoBan INTEGER," +
                "MonAn INTEGER," +
                "SoLuong INTEGER," +
                "PRIMARY KEY (SoBan, MonAn)," +
                "FOREIGN KEY (SoBan) REFERENCES BanAn(SoBan)," +
                "FOREIGN KEY (MonAn) REFERENCES ThucDon(Id))");

        db.queryData("CREATE TABLE IF NOT EXISTS HoaDon" +
                "(" +
                "SoHoaDon INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ThoiGian DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "BanAn INTEGER," +
                "FOREIGN KEY (BanAn) REFERENCES BanAn(SoBan))");

    }

    private void themThucDon(ThucDon thucDon) {
        db.queryData("INSERT INTO ThucDon VALUES (null,'" + thucDon.getTen() + "'," + thucDon.getGia() + ",'" + thucDon.getDonVi() + "','" + thucDon.getHinhAnh() + "','" + thucDon.isConHang() + "')");
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
        hienThiSoBan();
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
                break;
            case R.id.nav_thuc_don:
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
}
