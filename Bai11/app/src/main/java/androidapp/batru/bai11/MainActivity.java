package androidapp.batru.bai11;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    private EditText edtName, edtCMND, edtBoSung;
    private RadioButton radTrung, radCao, radDai;
    private CheckBox chkDocBao, chkDocSach, chkCoding;
    private Button btnGui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtBoSung = (EditText) findViewById(R.id.edtBoSung);
        edtName = (EditText) findViewById(R.id.editName);
        edtCMND = (EditText) findViewById(R.id.editCMND);
        radCao = (RadioButton) findViewById(R.id.radCaoDang);
        radTrung = (RadioButton) findViewById(R.id.radTrungCap);
        radDai = (RadioButton) findViewById(R.id.radDaiHoc);
        chkCoding = (CheckBox) findViewById(R.id.chkCoding);
        chkDocBao = (CheckBox) findViewById(R.id.chkDocBao);
        chkDocSach = (CheckBox) findViewById(R.id.chkDocSach);
        btnGui = (Button) findViewById(R.id.btnGuiThongTin);

        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String cmnd = edtCMND.getText().toString();
                String boSung = edtBoSung.getText().toString();
                String bangCap;
                if (radTrung.isChecked()) {
                    bangCap = "Trung cap";
                } else if (radCao.isChecked()) {
                    bangCap = "Cao hoc";
                } else {
                    bangCap = "Dai hoc";
                }
                String soThich = "";
                if (chkDocSach.isChecked()) {
                    soThich += "Doc sach ";
                }
                if (chkDocBao.isChecked()) {
                    soThich += "Doc bao ";
                }
                if (chkCoding.isChecked()) {
                    soThich += "Coding ";
                }
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Thong tin ca nhan");
                dialog.setPositiveButton("Dong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.setMessage("Ten: " + name +
                        "\nCMND: " + cmnd +
                        "\nBang Cap: " + bangCap +
                        "\nSo thich " + soThich + "\nThong tin bo sung: " + boSung);
                dialog.create().show();
            }
        });
    }
}
