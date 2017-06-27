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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import model.MonAn;
import singleton.Singleton;
import singleton.SingletonActivity;

import static androidapp.batru.cafeshop.MainActivity.db;

public class ThucDonThemActivity extends AppCompatActivity {

    private final String TAG = "ThucDonThemActivity";
    private final int REQUEST_GALARY_CODE = 112;

    private EditText edtTen, edtGia, edtDonVi;
    private Button btnXacNhan;
    private ImageView imgHinhAnh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuc_don_them);

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

        imgHinhAnh = (ImageView) findViewById(R.id.imageMonAn);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyClicked();
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

    @Override
    public void onBackPressed() {
        finish();
    }

    private void xuLyClicked() {
        String ten = edtTen.getText().toString();
        String gia = edtGia.getText().toString();
        String donVi = edtDonVi.getText().toString();
        byte[] hinhAnh = Singleton.getInstance().getByteArrayForImageView(imgHinhAnh);

        if (hinhAnh == null) {
            hinhAnh = SingletonActivity.decodeByteStreamFromNullImageView(this);
        }

        if (gia.equals("")) {
            gia = "0";
        }
        
        if (ten.equals("") || gia.equals("") || donVi.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.toasl_nhap_lieu), Toast.LENGTH_SHORT).show();
            return;
        }

        MonAn monAn = new MonAn(ten, Long.parseLong(gia), donVi , true, hinhAnh);
        themMonAn(monAn);
    }
    
    private void themMonAn(MonAn monAn) {
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TenMonAn", monAn.getTen());
        values.put("DonGia", monAn.getGia());
        values.put("DonViTinh", monAn.getDonVi());
        values.put("ConHang", monAn.isConHang());
        values.put("HinhAnh", monAn.getHinhAnh());

        database.insert("MonAn", null, values);
        startActivity(new Intent(this, ThucDonActivity.class));
    }
}
