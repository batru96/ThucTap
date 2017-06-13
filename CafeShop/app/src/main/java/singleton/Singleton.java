package singleton;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import androidapp.batru.cafeshop.MainActivity;

public class Singleton {
    private static Singleton singleton = new Singleton();

    private Singleton() {}

    public static Singleton getInstance() {
        return singleton;
    }

    public int maNhanVien = -1;
    public SQLiteDatabase database = MainActivity.db.getWritableDatabase();

    public byte[] getByteArrayForImageView(ImageView image) {
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        if (drawable == null)
            return null;
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public Bitmap decodeBitmapFromByteArray(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
