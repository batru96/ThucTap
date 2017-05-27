package androidapp.batru.cafeshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import model.MonAn;

/**
 * Created by hoangkhoa on 5/27/17.
 */

public class SuaThucDonActivity extends AppCompatActivity {

    private EditText edtTen, edtGia, edtDonVi;
    private Button btnXacNhan;
    private CheckBox ckNgunBan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_thuc_don);

        initControls();
    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Chỉnh sửa thực đơn");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edtTen = (EditText) findViewById(R.id.editTenMon);
        edtGia = (EditText) findViewById(R.id.editGiaBan);
        edtDonVi = (EditText) findViewById(R.id.editDonVi);
        btnXacNhan = (Button) findViewById(R.id.xongButton);
        ckNgunBan = (CheckBox) findViewById(R.id.checkboxTrangThai);

        final MonAn monAn = (MonAn) getIntent().getSerializableExtra("MONAN");
        edtTen.setText(monAn.getTen());
        edtGia.setText(monAn.getGia() + "");
        edtDonVi.setText(monAn.getDonVi());
        ckNgunBan.setChecked(!monAn.isConHang());

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyClicked(monAn);
            }
        });
    }

    private void xuLyClicked(MonAn monAn) {
        monAn.setTen(edtTen.getText().toString());
        try {
            monAn.setGia(Long.parseLong(edtGia.getText().toString()));
        } catch (Exception e) {
            monAn.setGia(0);
        }
        monAn.setDonVi(edtDonVi.getText().toString());
        monAn.setConHang(!ckNgunBan.isChecked());

        suaMonAn(monAn);
    }

    private void suaMonAn(MonAn monAn) {
        MainActivity.db.queryData("UPDATE MonAn SET TenMonAn = '" + monAn.getTen() + "', DonGia = "
                + monAn.getGia() + ", DonViTinh = '" + monAn.getDonVi() + "'," +
                " ConHang = '" + monAn.isConHang() + "' WHERE MaMonAn = " + monAn.getId());
        Toast.makeText(this, "Sua thanh cong", Toast.LENGTH_SHORT).show();
    }
}
