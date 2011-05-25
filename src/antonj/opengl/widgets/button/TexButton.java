package antonj.opengl.widgets.button;

import android.view.MotionEvent;
import antonj.opengl.Mesh;
import antonj.opengl.widgets.Widget;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 18.02.2010
 * Time: 23:11:04
 */
public class TexButton extends Widget {
    private static final int DEFAULT_STATE = 0;
    
    public static final int ENABLED = DEFAULT_STATE;
    public static final int DISABLED = 1;
    public static final int PRESSED = 2;

    private ButtonTextures textures;
    public int state;

    private float width;
    private float height;
    private Viewport viewport;

    public ButtonListener listener;

    private ButtonConfig handler;

    public TexButton(ButtonTextures textures,
            ButtonConfig handler,
            float width, float height, Viewport viewport) {
        this.textures = textures;
        this.handler = handler;
        this.width = width;
        this.height = height;
        this.viewport = viewport;

        this.state = DEFAULT_STATE;
        this.mesh = new Mesh(6, false, true, false);
        this.dirty = true;
    }

    public void setState(int state) {
        dirty = this.state != state || dirty;
        this.state = state;
    }

    public void setListener(ButtonListener listener) {
        this.listener = listener;
    }

    public void rebuild() {
        textures.setStateTexture(state, mesh, x, y, width, height);
        dirty = false;
    }

    public boolean handleEvent(MotionEvent motionEvent) {
        return handler.handleEvent(this, motionEvent);
    }

    public void draw(GL10 gl) {
        if (dirty) {
            rebuild();
        }

        textures.texture.bind(gl);
        mesh.draw(gl);
    }

    public boolean hit(float x, float y) {
        y = viewport.toGraphicsY(y);

        return x > this.x && x < this.x + width
                && y < this.y && y > this.y - height;
    }
}
