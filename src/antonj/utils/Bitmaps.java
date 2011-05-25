package antonj.utils;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * User: Anton
 * Date: 10.03.2010
 * Time: 14:58:53
 */
public class Bitmaps {
    public static Bitmap loadBitmap(AssetManager assets, String resource) {
        InputStream is = null;
        try {
            is = assets.open(resource);
            Bitmap source = BitmapFactory.decodeStream(is);
            Bitmap result = source.copy(Bitmap.Config.RGB_565, false);
            source.recycle();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IO.silentClose(is);
        }
    }
}
