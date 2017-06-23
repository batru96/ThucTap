package androidapp.batru.cafeshop;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import model.MonAn;
import singleton.Singleton;
import singleton.SingletonActivity;

import static androidapp.batru.cafeshop.MainActivity.db;

public class SuaThucDonActivity extends AppCompatActivity {

    private final int REQUEST_GALARY_CODE = 112;
    private final String TAG = "SuaThucDonActivity";

    private EditText edtTen, edtGia, edtDonVi;
    private Button btnXacNhan;
    private CheckBox ckNgunBan;
    private ImageView imgHinhAnh;

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
        imgHinhAnh = (ImageView) findViewById(R.id.imgHinhAnh);

        final MonAn monAn = (MonAn) getIntent().getSerializableExtra("MONAN");
        edtTen.setText(monAn.getTen());
        edtGia.setText(monAn.getGia() + "");
        edtDonVi.setText(monAn.getDonVi());
        ckNgunBan.setChecked(!monAn.isConHang());

        Bitmap bitmap = Singleton.getInstance().decodeBitmapFromByteArray(monAn.getHinhAnh());
        imgHinhAnh.setImageBitmap(bitmap);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyClicked(monAn);
            }
        });

        imgHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHinhAnh();
            }
        });
    }

    private void clickHinhAnh() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALARY_CODE);
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

        byte[] hinhAnh = Singleton.getInstance().getByteArrayForImageView(imgHinhAnh);
        if (hinhAnh == null) {
            hinhAnh = SingletonActivity.decodeByteStreamFromNullImageView(this);
        }
        monAn.setHinhAnh(hinhAnh);

        suaMonAn(monAn);
    }

    private void suaMonAn(MonAn monAn) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenMonAn", monAn.getTen());
        values.put("DonGia", monAn.getGia());
        values.put("DonViTinh", monAn.getDonVi());
        values.put("ConHang", monAn.isConHang());
        values.put("HinhAnh", monAn.getHinhAnh());

        database.update("MonAn", values, "MaMonAn = " + monAn.getId(), null);

        startActivity(new Intent(this, ThucDonActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALARY_CODE) {
                try {
                    Uri uri = data.getData();
                    InputStream is = getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "Pick Image failed");
                }
            }
        }
        imgHinhAnh.setImageBitmap(bitmap);
    }
}
