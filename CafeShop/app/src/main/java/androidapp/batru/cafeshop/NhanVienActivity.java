package androidapp.batru.cafeshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import adapter.NhanVienAdapter;
import model.NhanVien;

public class NhanVienActivity extends AppCompatActivity {

    private ListView lvNhanVien;
    private ArrayList<NhanVien> ds;
    private NhanVienAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        initControls();
    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Danh sách nhân viên");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        lvNhanVien = (ListView) findViewById(R.id.nhanVienListView);
        ds = new ArrayList<>();

        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        String thoiGian = formater.format(Calendar.getInstance().getTime());

        ds.add(new NhanVien(0, "Cristiano Ronaldo", thoiGian, null));
        ds.add(new NhanVien(1, "Leonel Messi", thoiGian, null));
        ds.add(new NhanVien(2, "Zalatan Ibrahimovic", thoiGian, null));
        ds.add(new NhanVien(3, "Wayne Rooney", thoiGian, null));

        adapter = new NhanVienAdapter(this, R.layout.item_nhan_vien, ds);
        lvNhanVien.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(this, ThemNhanVienActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
