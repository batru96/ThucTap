package androidapp.batru.cafeshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.NhanVienAdapter;
import model.NhanVien;

import static androidapp.batru.cafeshop.MainActivity.db;

public class NhanVienActivity extends AppCompatActivity {

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
        ds = docDuLieuTuDatabase();
        adapter = new NhanVienAdapter(this, R.layout.item_nhan_vien, ds);
        lvNhanVien.setAdapter(adapter);

        lvNhanVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                xoaItem(position);
                return true;
            }
        });

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

    private void xoaItem(final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Thông báo");
        dialog.setMessage("Xác nhận xóa");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NhanVien nhanvien = ds.get(position);
                db.queryData("DELETE FROM NhanVien WHERE MaNhanVien = " + nhanvien.getMaNv());
                ds.remove(position);
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

    private ArrayList<NhanVien> docDuLieuTuDatabase() {
        ArrayList<NhanVien> dsNhanVien = new ArrayList<>();
        Cursor cursor = db.getData("SELECT * FROM NhanVien");
        while (cursor.moveToNext()) {
            NhanVien nv = new NhanVien();

            int id = cursor.getInt(0);
            nv.setMaNv(id);

            String ten = cursor.getString(1);
            nv.setTenNhanVien(ten);

            String ngayLamViec = cursor.getString(2);
            nv.setNgayLamViec(ngayLamViec);

            byte[] hinhAnh = cursor.getBlob(3);
            nv.setHinhAnh(hinhAnh);

            dsNhanVien.add(nv);
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
}
