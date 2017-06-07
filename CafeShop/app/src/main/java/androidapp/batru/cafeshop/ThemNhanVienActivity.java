package androidapp.batru.cafeshop;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import singleton.Singleton;

public class ThemNhanVienActivity extends AppCompatActivity {

    private final int REQUEST_CAMERA_CODE = 112;
    private final int REQUEST_GALARY_CODE = 113;
    private final String TAG = "ThemNhanVienActivity";

    private ImageView imgNhanVien;
    private EditText edtTen;
    private TextView txtNgayLamViec;
    private Button btnChonNgayLam, btnXong;

    SimpleDateFormat formater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);

        initControls();
        ganEvents();
    }

    private void ganEvents() {
        btnChonNgayLam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgayLamViec();
            }
        });

        btnXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyXongClicked();
            }
        });

        imgNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHinhAnh();
            }
        });

        imgNhanVien.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickHinhAnh();
                return true;
            }
        });
    }

    private void xuLyXongClicked() {
        SQLiteDatabase database = MainActivity.db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TenNhanVien", edtTen.getText().toString());
        values.put("NgayLamViec", edtTen.getText().toString());

        byte[] hinhAnh = Singleton.getInstance().getByteArrayForImageView(imgNhanVien);
        if (hinhAnh!= null){
            values.put("HinhAnh", hinhAnh);
        }
        database.insert("NhanVien", null, values);
    }

    private void longClickHinhAnh() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }

    private void clickHinhAnh() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALARY_CODE);
    }

    private void chonNgayLamViec() {
        int ngay = Calendar.getInstance().get(Calendar.DATE);
        int thang = Calendar.getInstance().get(Calendar.MONTH);
        int nam = Calendar.getInstance().get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                txtNgayLamViec.setText(formater.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        dialog.show();
    }

    private void initControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Quản lý nhân viên");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        imgNhanVien = (ImageView) findViewById(R.id.imageNhanVien);
        edtTen = (EditText) findViewById(R.id.editTenNhanVien);
        txtNgayLamViec = (TextView) findViewById(R.id.ngayLamViecText);
        btnChonNgayLam = (Button) findViewById(R.id.buttonNgayLam);
        btnXong = (Button) findViewById(R.id.buttonXong);

        formater = new SimpleDateFormat("dd/MM/yyyy");

        txtNgayLamViec.setText(formater.format(Calendar.getInstance().getTime()));
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
        if (bitmap != null) {
            imgNhanVien.setImageBitmap(bitmap);
        }
    }
}
