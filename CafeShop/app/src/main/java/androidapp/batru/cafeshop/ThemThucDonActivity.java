package androidapp.batru.cafeshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.MonAn;

public class ThemThucDonActivity extends AppCompatActivity {

    private EditText edtTen, edtGia, edtDonVi;
    private Button btnXacNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thuc_don);

        initControls();
    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Thêm món ăn");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edtTen = (EditText) findViewById(R.id.editTenMon);
        edtGia = (EditText) findViewById(R.id.editGiaBan);
        edtDonVi = (EditText) findViewById(R.id.editDonVi);
        btnXacNhan = (Button) findViewById(R.id.xongButton);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyClicked();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "DESTROY", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void xuLyClicked() {
        String ten = edtTen.getText().toString();
        long gia;
        try {
            gia = Long.parseLong(edtGia.getText().toString());
        } catch (Exception e) {
            gia = 0;
        }
        String donVi = edtDonVi.getText().toString();

        if (ten.equals("") || gia == 0 || donVi.equals("")) {
            Toast.makeText(this, "Vui long nhap du lieu day du", Toast.LENGTH_SHORT).show();
        } else {
            MonAn monAn = new MonAn(ten, gia, donVi);
            themMonAn(monAn);
        }
    }

    private void themMonAn(MonAn monAn) {
        MainActivity.db.queryData("INSERT INTO MonAn VALUES (null, '" + monAn.getTen() + "', " 
                + monAn.getGia() + ", '" + monAn.getDonVi() + "', null, 'true');");
        Toast.makeText(this, "Thanh Cong", Toast.LENGTH_SHORT).show();
    }
}
