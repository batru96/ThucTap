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
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import model.NhanVien;
import singleton.Singleton;
import singleton.SingletonActivity;

import static androidapp.batru.cafeshop.MainActivity.db;

public class QuanLyNhanVienActivity extends AppCompatActivity {

    //region properties
    private final int REQUEST_CAMERA_CODE = 112;
    private final int REQUEST_GALARY_CODE = 113;
    private final String TAG = "QuanLyNhanVienActivity";

    private ImageView imgNhanVien;
    private EditText edtTen;
    private TextView txtNgayLamViec, txtTitle;
    private Button btnChonNgayLam, btnXong;

    SimpleDateFormat formater;

    private Intent intent;
    private String nhiemVu;
    private int maNhanVienFromIntent;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nhan_vien);

        initControls();
        ganEvents();
    }

    //region myfunction

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
                xuLyButtonXongClicked();
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

    private void xuLyButtonXongClicked() {
        if (edtTen.getText().toString().equals("")) {
            Toast.makeText(this, getResources().getString(R.string.toasl_nhap_lieu), Toast.LENGTH_SHORT).show();
            return;
        }
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TenNhanVien", edtTen.getText().toString());
        values.put("NgayLamViec", txtNgayLamViec.getText().toString());

        byte[] hinhAnh = Singleton.getInstance().getByteArrayForImageView(imgNhanVien);
        // Tao bytearray tu image profile mac dinh
        if (hinhAnh == null) {
            hinhAnh = SingletonActivity.decodeByteStreamFromNullImageView(this);
        }
        values.put("HinhAnh", hinhAnh);

        if (nhiemVu.equals("Them")) {
            database.insert("NhanVien", null, values);
        } else if (nhiemVu.equals("Sua")) {
            database.update("NhanVien", values, "MaNhanVien = " + maNhanVienFromIntent, null);
        }
        chuyenVeParentActivity();
    }

    private void chuyenVeParentActivity() {
        startActivity(new Intent(this, NhanVienActivity.class));
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
        intent = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Quản lý nhân viên");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        imgNhanVien = (ImageView) findViewById(R.id.imageNhanVien);
        edtTen = (EditText) findViewById(R.id.editTenNhanVien);
        txtNgayLamViec = (TextView) findViewById(R.id.ngayLamViecText);
        txtTitle = (TextView) findViewById(R.id.textTitle);
        btnChonNgayLam = (Button) findViewById(R.id.buttonNgayLam);
        btnXong = (Button) findViewById(R.id.buttonXong);

        formater = new SimpleDateFormat("dd/MM/yyyy");

        nhiemVu = intent.getStringExtra("FROM");

        if (nhiemVu.equals("Sua")) {
            NhanVien nv = (NhanVien) intent.getSerializableExtra("NhanVien");
            edtTen.setText(nv.getTenNhanVien());
            txtNgayLamViec.setText(nv.getNgayLamViec());
            txtTitle.setText("Cập nhật thông tin");
            maNhanVienFromIntent = nv.getMaNv();

            Bitmap bitmap = Singleton.getInstance().decodeBitmapFromByteArray(nv.getHinhAnh());
            imgNhanVien.setImageBitmap(bitmap);

        } else if (nhiemVu.equals("Them")) {
            txtTitle.setText("Thêm nhân viên");
            txtNgayLamViec.setText(formater.format(Calendar.getInstance().getTime()));
        }
    }

    //endregion

    //region override function
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

    @Override
    public void onBackPressed() {
        chuyenVeParentActivity();
    }
    //endregion
}
