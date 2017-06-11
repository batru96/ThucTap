package androidapp.batru.cafeshop;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import model.MonAn;
import singleton.Singleton;

public class ThemThucDonActivity extends AppCompatActivity {

    private final String TAG = "ThemThucDonActivity";
    private final int REQUEST_CAMERA_CODE = 111;
    private final int REQUEST_GALARY_CODE = 112;

    private EditText edtTen, edtGia, edtDonVi;
    private Button btnXacNhan;
    private ImageView imgHinhAnh;

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

        imgHinhAnh.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longLickHinhAnh();
                return true;
            }
        });
    }

    private void clickHinhAnh() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALARY_CODE);
    }

    private void longLickHinhAnh() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA_CODE) {
                bitmap = (Bitmap) data.getExtras().get("data");
            } else if (requestCode == REQUEST_GALARY_CODE) {
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
            Drawable drawable = getResources().getDrawable(R.drawable.profile);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            hinhAnh = stream.toByteArray();
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
        SQLiteDatabase database = MainActivity.db.getWritableDatabase();

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
