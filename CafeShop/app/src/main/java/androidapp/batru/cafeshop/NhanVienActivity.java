package androidapp.batru.cafeshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.NhanVienAdapter;
import database.Database;
import model.NhanVien;
import singleton.Singleton;

import static androidapp.batru.cafeshop.MainActivity.db;

public class NhanVienActivity extends AppCompatActivity {

    private final String TAG = "NHANVIEN_ACTIVITY";

    //region properties
    private ListView lvNhanVien;
    private ArrayList<NhanVien> ds;
    private NhanVienAdapter adapter;
    //endregion

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

        lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                suaItem(position);
            }
        });
    }

    private void suaItem(int position) {
        Intent intent = new Intent(this, QuanLyNhanVienActivity.class);
        NhanVien nv = ds.get(position);
        intent.putExtra("NhanVien", nv);
        intent.putExtra("FROM", "Sua");
        startActivity(intent);
    }

//    private void xoaItem(final int position) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        dialog.setTitle("Thông báo");
//        dialog.setMessage("Xác nhận xóa");
//        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                NhanVien nhanvien = ds.get(position);
//                db.queryData("UPDATE NhanVien SET ConLamViec = 0 WHERE MaNhanVien = " + nhanvien.getMaNv());
//                ds = docDuLieuTuDatabase();
//                adapter = new NhanVienAdapter(NhanVienActivity.this, R.layout.item_nhan_vien, ds);
//                lvNhanVien.setAdapter(adapter);
//            }
//        });
//        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        dialog.show();
//    }

    private ArrayList<NhanVien> docDuLieuTuDatabase() {
        ArrayList<NhanVien> dsNhanVien = new ArrayList<>();
        String query = "SELECT * FROM NhanVien";
        Cursor cursor = db.getData(query);
        while (cursor.moveToNext()) {
            try {
                NhanVien nv = new NhanVien();

                int id = cursor.getInt(0);
                nv.setMaNv(id);

                String ten = cursor.getString(1);
                nv.setTenNhanVien(ten);

                String ngayLamViec = cursor.getString(2);
                nv.setNgayLamViec(ngayLamViec);

                byte[] hinhAnh = cursor.getBlob(3);
                nv.setHinhAnh(hinhAnh);

                boolean isConLamViec = cursor.getString(4).equals("1");
                nv.setNghiViec(!isConLamViec);

                dsNhanVien.add(nv);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }
        return dsNhanVien;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(this, QuanLyNhanVienActivity.class);

            intent.putExtra("FROM", "Them");

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ds = docDuLieuTuDatabase();
        adapter = new NhanVienAdapter(this, R.layout.item_nhan_vien, ds);
        lvNhanVien.setAdapter(adapter);
    }
}
