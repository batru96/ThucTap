package androidapp.batru.cafeshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.ThucDonAdapter;
import model.ThucDon;

public class ChonMonActivity extends AppCompatActivity {

    private ListView listViewMonAn;
    private ThucDonAdapter adapter;
    private ArrayList<ThucDon> ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_mon);

        initControls();
    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Chọn món");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listViewMonAn = (ListView) findViewById(R.id.listView);
        ds = new ArrayList<>();
        ds.add(new ThucDon("Chim cuc chien bo", 25000, "Cai", "", true));
        ds.add(new ThucDon("Bao xeo", 2000, "Cai", "", true));
        ds.add(new ThucDon("Banh mi bo kho", 15000, "Phan", "", true));
        ds.add(new ThucDon("Cafe sua da", 16000, "Ly", "", true));
        adapter = new ThucDonAdapter(this, R.layout.chon_mon_item, ds);
        listViewMonAn.setAdapter(adapter);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chon_mon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_tinh_tien:
                Toast.makeText(this, "Tinh tien click", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
