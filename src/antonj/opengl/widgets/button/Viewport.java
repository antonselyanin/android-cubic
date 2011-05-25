package antonj.opengl.widgets.button;

/**
 * User: Anton
 * Date: 22.02.2010
 * Time: 23:44:09
 */
public class Viewport {
    public float width;
    public float height;

    public Viewport(float width, float height) {
        this.width = width;
        this.height = height;
    }

    //TODO: rename?
    public float toGraphicsY(float touchY) {
        return height - touchY;
    }
}
