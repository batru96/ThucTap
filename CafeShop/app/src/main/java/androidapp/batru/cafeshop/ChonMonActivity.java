package androidapp.batru.cafeshop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.ChonMonAdapter;
import model.BanAn;
import model.MonAn;

import static androidapp.batru.cafeshop.MainActivity.db;

public class ChonMonActivity extends AppCompatActivity {

    private final String TAG = "CHONMON_ACTVITY";

    private ListView listViewMonAn;
    private ChonMonAdapter adapter;
    private ArrayList<MonAn> ds;

    private Button btnHuy;
    private Button btnCat;
    private Button btnThuTien;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_mon);

        initControls();
        initEvents();
    }

    private void initEvents() {
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChonMonActivity.this, MainActivity.class));
            }
        });

        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChonMonActivity.this, "Cap nhat ban an va hoa don", Toast.LENGTH_SHORT).show();
            }
        });

        btnThuTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThuTien();
            }
        });
    }

    private void xuLyThuTien() {
        Toast.makeText(this, "THU TIEN NE!!!", Toast.LENGTH_SHORT).show();
    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Chọn món");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        BanAn banAn = (BanAn) intent.getSerializableExtra(MainActivity.INTENT_BANAN);
        int soKhach = intent.getIntExtra(MainActivity.INTENT_SOKHACH, -1);

        btnHuy = (Button) findViewById(R.id.buttonHuy);
        btnCat = (Button) findViewById(R.id.buttonCat);
        btnThuTien = (Button) findViewById(R.id.buttonThuTien);


        listViewMonAn = (ListView) findViewById(R.id.listView);
        ds = new ArrayList<>();
        if (banAn.getSoNguoi() == 0) {
            Cursor cursor = db.getData("SELECT * FROM MonAn");
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                int donGia = cursor.getInt(2);
                String donVi = cursor.getString(3);
                boolean isConHang = cursor.getString(4).equals("true");
                ds.add(new MonAn(id, ten, donGia, donVi, isConHang));
            }
        } else {
            Toast.makeText(this, "BANG, BAN CO KHACH", Toast.LENGTH_SHORT).show();
        }
        adapter = new ChonMonAdapter(this, R.layout.item_chon_mon, ds);
        listViewMonAn.setAdapter(adapter);
    }

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
}
