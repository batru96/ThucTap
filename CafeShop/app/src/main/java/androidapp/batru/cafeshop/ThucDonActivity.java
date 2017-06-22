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

import java.util.ArrayList;

import adapter.ThucDonAdapter;
import model.MonAn;

import static androidapp.batru.cafeshop.MainActivity.db;

public class ThucDonActivity extends AppCompatActivity{

    private final String TAG = "THUCDON_ACTIVITY";

    private ListView lvThucDon;
    private ArrayList<MonAn> dsMonAn;
    private ThucDonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuc_don);

        initControls();
    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Thực đơn");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lvThucDon = (ListView) findViewById(R.id.listviewThucDon);
        lvThucDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                suaItem(position);
            }
        });
        lvThucDon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                xoaItem(position);
                return true;
            }
        });
    }

    private void suaItem(int position) {
        Intent intent = new Intent(this, SuaThucDonActivity.class);
        MonAn monAn = dsMonAn.get(position);
        intent.putExtra("MONAN", monAn);
        startActivity(intent);
    }

    private void xoaItem(final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Thông báo");
        dialog.setMessage("Xác nhận xóa");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MonAn monAn = dsMonAn.get(position);
                db.queryData("DELETE FROM MonAn WHERE MaMonAn = " + monAn.getId());
                dsMonAn.remove(position);
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

    private ArrayList<MonAn> docDuLieuTuDatabase() {
        ArrayList<MonAn> dsMonAn = new ArrayList<>();
        Cursor cursor = db.getData("SELECT * FROM MonAn");
        while (cursor.moveToNext()) {
            try {
                MonAn monAn = new MonAn();

                int id = cursor.getInt(0);
                monAn.setId(id);

                String ten = cursor.getString(1);
                monAn.setTen(ten);

                long donGia = cursor.getLong(2);
                monAn.setGia(donGia);

                String donVi = cursor.getString(3);
                monAn.setDonVi(donVi);

                boolean isConBan = cursor.getString(4).equals("1");
                monAn.setConHang(isConBan);

                byte[] hinhAnh = cursor.getBlob(5);
                monAn.setHinhAnh(hinhAnh);

                dsMonAn.add(monAn);
            }catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }
        return dsMonAn;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thuc_don, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_chon_mon) {
            xuLyThemMon();
        }
        return super.onOptionsItemSelected(item);
    }

    private void xuLyThemMon() {
        startActivity(new Intent(this, ThemThucDonActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        dsMonAn = docDuLieuTuDatabase();
        adapter = new ThucDonAdapter(this, R.layout.item_thuc_don, dsMonAn);
        lvThucDon.setAdapter(adapter);
    }
}
