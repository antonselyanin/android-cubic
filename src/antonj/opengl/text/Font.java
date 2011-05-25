package antonj.opengl.text;

import android.graphics.*;
import android.util.FloatMath;
import android.util.SparseArray;
import antonj.opengl.texture.Texture;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 09.02.2010
 * Time: 20:58:03
 */
public class Font {
    private static final int BITMAP_SIZE = 256;
    private static final int GLYPH_GAP = 5;

    public Texture texture;

    private Paint paint;

    private Paint.FontMetrics metrics;
    private SparseArray<Glyph> glyphs;

    public Font(GL10 gl, Typeface typeface, int size, boolean antialiasing) {
        this(typeface, size, antialiasing, Texture.create(gl, BITMAP_SIZE));
    }

    public Font(Typeface typeface, int size, boolean antialiasing, Texture texture) {
        paint = new Paint();
        paint.setTypeface(typeface);
        paint.setTextSize(size);
        paint.setAntiAlias(antialiasing);
        this.metrics = paint.getFontMetrics();
        this.glyphs = new SparseArray<Glyph>();
        this.texture = texture;
    }

    public int getLineGap() {
        return (int) FloatMath.ceil(metrics.leading);
    }

    public int getLineHeight() {
        return (int) FloatMath.ceil(
                Math.abs(metrics.ascent) + Math.abs(metrics.descent));
    }

    public Glyph getGlyph(GL10 gl, char c) {
        Glyph glyph = glyphs.get(c);

        if (glyph == null) {
            glyph = getGlyph0(gl, c);
            glyphs.append(c, glyph);
        }

        return glyph;
    }

    private Glyph getGlyph0(GL10 gl, char c) {
        Rect rect = new Rect();
        String str = Character.toString(c);
        paint.getTextBounds(str, 0, 1, rect);
        int width = rect.width() == 0 ? 1 : rect.width() + GLYPH_GAP;
        Bitmap bitmap = Bitmap.createBitmap(
                width,
                getLineHeight(),
                Bitmap.Config.ARGB_8888);

        Rect bitmapBounds = new Rect(0, 0, width, getLineHeight());
        Canvas g = new Canvas(bitmap);
        paint.setColor(0x00000000);
        paint.setStyle(Paint.Style.FILL);
        g.drawRect(bitmapBounds, paint);
        paint.setColor(0xFFFFFFFF);
        g.drawText(str, 0, -metrics.ascent, paint);

        try {
            return new Glyph(
                    texture.addBitmap(gl, bitmap),
                    getGlyphAdvance(str));
        } finally {
            bitmap.recycle();
        }
    }

    public void bind(GL10 gl) {
        texture.bind(gl);
    }

    public int getGlyphAdvance(String str) {
        float[] width = new float[1];
        paint.getTextWidths(str, width);
        return (int) (FloatMath.ceil(width[0]));
    }
}
