package adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import androidapp.batru.cafeshop.ChonMonActivity;
import androidapp.batru.cafeshop.MainActivity;
import androidapp.batru.cafeshop.R;
import model.BanAn;

public class BanAnAdapter extends ArrayAdapter<BanAn> {
    private Activity context;
    private int resource;
    private ArrayList<BanAn> ds;

    public BanAnAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull ArrayList<BanAn> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.ds = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.item_ban_an, parent, false);
        }

        Button button = (Button) view.findViewById(R.id.btnBanAn);
        button.setText("Bàn " + (position + 1));
        final BanAn banAn = ds.get(position);
        if (banAn.getSoNguoi() != 0) {
            button.setTextColor(context.getResources().getColor(android.R.color.black));
            button.setBackground(context.getResources().getDrawable(R.drawable.button_co_khach));
        } else {
            button.setBackground(context.getResources().getDrawable(R.drawable.button_ban_trong));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked(banAn);
            }
        });

        return view;
    }

    private void clicked(BanAn banAn) {
        if (banAn.getSoNguoi() == 0) {
            khoiTaoDialog(banAn);
        } else {
            Intent intent = new Intent(context, ChonMonActivity.class);
            intent.putExtra(MainActivity.INTENT_BANAN, banAn);
            context.startActivity(intent);
        }
    }

    private void khoiTaoDialog(final BanAn banAn) {
        final Dialog dialogThemBanAn = new Dialog(context);
        dialogThemBanAn.setContentView(R.layout.dialog_them_ban);
        final EditText edtNhapSoNguoi = (EditText) dialogThemBanAn.findViewById(R.id.edtNhapSoNguoi);

        Button btnHuy = (Button) dialogThemBanAn.findViewById(R.id.btnHuy);
        Button btnXacNhan = (Button) dialogThemBanAn.findViewById(R.id.btnXacNhan);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soKhachMoiVao;
                if (edtNhapSoNguoi.getText().toString().equals("")) {
                    soKhachMoiVao = 1;
                } else {
                    soKhachMoiVao = Integer.parseInt(edtNhapSoNguoi.getText().toString());
                }
                Intent intent = new Intent(context, ChonMonActivity.class);
                banAn.setSoNguoi(soKhachMoiVao);
                intent.putExtra(MainActivity.INTENT_BANAN, banAn);
                intent.putExtra(MainActivity.INTENT_BANMOI, true);
                dialogThemBanAn.dismiss();
                context.startActivity(intent);
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemBanAn.dismiss();
            }
        });
        dialogThemBanAn.show();
    }
}
