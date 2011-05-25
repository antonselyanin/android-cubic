package antonj.opengl.texture;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 08.02.2010
 * Time: 22:32:53
 */
public class Texture {
    private static Texture currentTexture = null;

    private int handle;

    private int currentX = 0;
    private int currentY = 0;
    private int maxItemHeight = 0;
    private int size;

    private Texture(int handle, int size) {
        this.handle = handle;
        this.size = size;
    }

    public void bind(GL10 gl) {
        if (currentTexture == this) {
            return;
        }

        currentTexture = this;
        gl.glBindTexture(GL10.GL_TEXTURE_2D, handle);
    }

    public TexDetails addBitmap(GL10 gl, Bitmap bitmap) {
        if (bitmap.getHeight() > maxItemHeight) {
            maxItemHeight = bitmap.getHeight();
        }

        if (currentX + bitmap.getWidth() > size) {
            if (currentY + bitmap.getHeight() > size) {
                throw new IllegalStateException("Texture is full of bitmaps");
            }
            currentX = 0;
            currentY += maxItemHeight;
        }

        bind(gl);
        GLUtils.texSubImage2D(GL10.GL_TEXTURE_2D, 0, currentX, currentY, bitmap);
        float left = (float) currentX / (float) size;
        float top = (float) currentY / (float) size;
        float right = left + ((float) bitmap.getWidth() / (float) size);
        float bottom = top + ((float) bitmap.getHeight() / (float) size);

        currentX += bitmap.getWidth();

        return new TexDetails(left, top, right, bottom,
                bitmap.getWidth(), bitmap.getHeight());
    }

    public static Texture create(GL10 gl, Bitmap bitmap) {
        if (bitmap.getWidth() != bitmap.getHeight()) {
            throw new IllegalArgumentException();
        }

        int[] textureIds = new int[1];
        gl.glGenTextures(1, textureIds, 0);

        int handle = textureIds[0];

        gl.glBindTexture(GL10.GL_TEXTURE_2D, handle);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        return new Texture(handle, bitmap.getWidth());
    }

    public static Texture create(GL10 gl, int size) {
        Bitmap bitmap = Bitmap.createBitmap(
                size, size, Bitmap.Config.RGB_565);
        try {
            return create(gl, bitmap);
        } finally {
            bitmap.recycle();
        }
    }
}
