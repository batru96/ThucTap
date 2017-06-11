package singleton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

import androidapp.batru.cafeshop.R;

/**
 * Created by hoangkhoa on 6/11/17.
 */

public class SingletonActivity {

    public static byte[] decodeByteStreamFromNullImageView(Context context) {
        Drawable drawable = context.getResources().getDrawable(R.drawable.profile);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
