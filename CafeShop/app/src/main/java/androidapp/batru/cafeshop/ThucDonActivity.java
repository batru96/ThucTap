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

import adapter.ThucDonAdapter;
import model.MonAn;

public class ThucDonActivity extends AppCompatActivity{

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
        lvThucDon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                xoaItem(position);
                return true;
            }
        });
    }

    private void xoaItem(final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Thông báo");
        dialog.setMessage("Xác nhận xóa");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MonAn monAn = dsMonAn.get(position);
                MainActivity.db.queryData("DELETE FROM MonAn WHERE MaMonAn = " + monAn.getId());
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
        Cursor cursor = MainActivity.db.getData("SELECT * FROM MonAn");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            long donGia = cursor.getLong(2);
            String donVi = cursor.getString(3);
            int hinhAnh = cursor.getInt(4);
            boolean isConBan = cursor.getString(5).equals("true");
            dsMonAn.add(new MonAn(id, ten, donGia, donVi, "", isConBan));
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
