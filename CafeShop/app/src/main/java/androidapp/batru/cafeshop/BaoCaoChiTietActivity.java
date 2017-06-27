package androidapp.batru.cafeshop;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.ChiTietAdapter;
import model.ChiTiet;

public class BaoCaoChiTietActivity extends AppCompatActivity {

    private ListView lvChiTiet;
    private ArrayList<ChiTiet> ds;
    private ChiTietAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_chi_tiet);
        initControls();
    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Chi tiết hóa đơn");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        
        int maHoaDon = getIntent().getIntExtra(BaoCaoActivity.INTENT_MaHoaDon, -1);
        int khuyenMai = getIntent().getIntExtra(BaoCaoActivity.INTENT_KhuyenMai, -1);

        lvChiTiet = (ListView) findViewById(R.id.lvChiTiet);
        ds = new ArrayList<>();
        Cursor cursor = MainActivity.db.getData("SELECT m.TenMonAn, c.DonGia, c.SoLuong, m.HinhAnh " +
                "FROM ChiTietHoaDon c JOIN MonAn m ON c.MaMonAn = m.MaMonAn " +
                "WHERE MaHoaDon = " + maHoaDon);
        while (cursor.moveToNext()) {
            String tenMonAn = cursor.getString(0);
            long donGia = cursor.getLong(1);
            int soLuong = cursor.getInt(2);
            byte[] hinhAnh = cursor.getBlob(3);
            ds.add(new ChiTiet(tenMonAn, soLuong, donGia, hinhAnh));
        }

        adapter = new ChiTietAdapter(this, R.layout.item_chi_tiet, ds);
        lvChiTiet.setAdapter(adapter);
    }
}
