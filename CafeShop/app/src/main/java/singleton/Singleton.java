package singleton;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class Singleton {
    private static Singleton singleton = new Singleton();

    private Singleton() {}

    public static Singleton getInstance() {
        return singleton;
    }

    public byte[] getByteArrayForImageView(ImageView image) {
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        if (drawable == null)
            return null;
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
