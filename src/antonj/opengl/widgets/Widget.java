package antonj.opengl.widgets;

import antonj.opengl.Mesh;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 18.02.2010
 * Time: 23:19:23
 */
public abstract class Widget {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int CENTER = 2;

    protected Mesh mesh;
    protected float x;
    protected float y;
    public boolean dirty = true;
    protected int alignment = LEFT;

    public Widget() {
        dirty = true;
    }

    public void setCoord(float x, float y) {
        this.x = x;
        this.y = y;
        dirty = true;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
        dirty = true;
    }

    public abstract void draw(GL10 gl);

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
