package androidapp.batru.cafeshop;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.ThucDonAdapter;
import model.ThucDon;

public class ThucDonActivity extends AppCompatActivity {

    private ListView lvThucDon;
    private ArrayList<ThucDon> dsThucDon;
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
        dsThucDon = new ArrayList<>();
        Cursor cursor = MainActivity.db.getData("SELECT * FROM MonAn");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            long donGia = cursor.getLong(2);
            String donVi = cursor.getString(3);
            int hinhAnh = cursor.getInt(4);
            boolean isConBan = cursor.getString(5).equals("true");
            dsThucDon.add(new ThucDon(id, ten, donGia, donVi, "", isConBan));

        }
        adapter = new ThucDonAdapter(this, R.layout.item_thuc_don, dsThucDon);
        lvThucDon.setAdapter(adapter);
    }
}
