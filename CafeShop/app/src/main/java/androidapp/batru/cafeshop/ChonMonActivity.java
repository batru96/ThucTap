package androidapp.batru.cafeshop;

import android.content.Intent;
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
import model.ChonMon;

public class ChonMonActivity extends AppCompatActivity {

    private final String TAG = "CHONMON_ACTVITY";

    private ListView listViewMonAn;
    private ChonMonAdapter adapter;
    private ArrayList<ChonMon> ds;

    private Button btnHuy;
    private Button btnCat;
    private Button btnThuTien;

    private Intent intent;
    private boolean isBanMoi;
    private int[] dsSoLuong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_mon);

        initControls();
        //initEvents();
    }

    private void initEvents() {
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyHuy();
            }
        });

        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnThuTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThuTien();
            }
        });
    }


    private void xuLyHuy() {
        startActivity(new Intent(this, MainActivity.class));
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

        btnHuy = (Button) findViewById(R.id.buttonHuy);
        btnCat = (Button) findViewById(R.id.buttonCat);
        btnThuTien = (Button) findViewById(R.id.buttonThuTien);

        Intent intent = getIntent();
        BanAn banAn = (BanAn) intent.getSerializableExtra(MainActivity.INTENT_BANAN);
        isBanMoi = intent.getBooleanExtra(MainActivity.INTENT_BANMOI, false);

        ListView lvChonMon = (ListView) findViewById(R.id.lvChonMon);
        ArrayList<ChonMon> ds = new ArrayList<>();
        adapter = new ChonMonAdapter(this, R.layout.item_chon_mon, ds);
        lvChonMon.setAdapter(adapter);

        if (isBanMoi) {
        } else {

        }
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
