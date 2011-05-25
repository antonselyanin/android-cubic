package antonj.opengl.widgets;

import antonj.opengl.Mesh;
import antonj.opengl.text.Font;
import antonj.opengl.text.Glyph;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 13.02.2010
 * Time: 19:14:42
 */
public abstract class Label extends Widget {
    protected Font font;

    //TODO: rename
    private float endX;

    protected Label(Font font) {
        super();
        this.font = font;
    }

    public Label(Font font, float x, float y) {
        this(font);
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(GL10 gl) {
        if (dirty) {
            rebuild(gl);
        }

        font.bind(gl);

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mesh.draw(gl);
        gl.glDisable(GL10.GL_BLEND);
    }

    public void rebuild(GL10 gl) {
        if (mesh == null) {
            mesh = new Mesh(getMaxLength() * 6, false, true, false);
        } else {
            mesh.reset();
        }

        float alignmentShift = 0;
        int textLength = getTextLength();

        // calculate alignment shift
        if (alignment != LEFT) {
            for (int i = 0; i < textLength; i++) {
                char c = getCharAt(i);
                Glyph glyph = font.getGlyph(gl, c);
                alignmentShift += glyph.advance;
            }
        }

        if (alignment == CENTER) {
            alignmentShift /= 2;
        }

        float realX = x - alignmentShift;

        //fill up mesh
        float shift = 0;
        for (int i = 0; i < textLength; i++) {
            char c = getCharAt(i);
            Glyph glyph = font.getGlyph(gl, c);
            glyph.tex.addTexturedQuad(mesh, realX + shift, y);
            shift += glyph.advance;
        }
        endX = x + shift;

        dirty = false;
    }

    public float getEndX() {
        return endX;
    }

    protected abstract char getCharAt(int i);

    protected abstract int getMaxLength();

    protected abstract int getTextLength();
}
