package androidapp.batru.cafeshop;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Admin on 6/22/2017.
 */

public class HoaDonActivity extends AppCompatActivity {

    private Button btnXong;
    private Button btnHuy;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon);

        initControls();
        initEvents();
    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Thanh toán");
        setSupportActionBar(toolbar);

        btnXong = (Button) findViewById(R.id.btnXong);
        btnHuy = (Button) findViewById(R.id.btnHuy);
    }

    private void initEvents() {
        btnXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HoaDonActivity.this, "Thanh toan xong ne", Toast.LENGTH_SHORT).show();
            }
        });
        
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
