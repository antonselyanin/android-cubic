package antonj.samples.buttons;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.view.MotionEvent;
import antonj.opengl.Mesh;
import antonj.opengl.text.Font;
import antonj.opengl.texture.Texture;
import antonj.opengl.widgets.IntLabel;
import antonj.opengl.widgets.TextLabel;
import antonj.opengl.widgets.Widget;
import antonj.opengl.widgets.button.ButtonListener;
import antonj.opengl.widgets.button.TexButton;
import antonj.opengl.widgets.button.TexButtonFactory;
import antonj.opengl.widgets.button.Viewport;
import antonj.utils.Bitmaps;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 18.02.2010
 * Time: 23:03:07
 */
public class ButtonsView extends GLSurfaceView
        implements GLSurfaceView.Renderer {
    private static final String TAG = ButtonsView.class.getSimpleName();
    private AssetManager assets;
    private TexButtonFactory buttonsFactory;
    private TexButton increment;
    private Mesh mesh;
    private Texture texture;
    private IntLabel counterLabel;
    private int counter;

    private Viewport viewport;
    private TexButton decrement;

    private ButtonListener incrementor = new ButtonListener() {
        @Override
        public void onClick() {
            counter++;
            counterLabel.setNumber(counter);
        }
    };

    private ButtonListener decrementor = new ButtonListener() {
        @Override
        public void onClick() {
            counter--;
/*
            if (counter <= 0) {
                decrement.setEnabled(false);
            }
*/
            counterLabel.setNumber(counter);
        }
    };
    private TextLabel textLabel;

    public ButtonsView(Context context) {
        super(context);
        setRenderer(this);

        assets = context.getAssets();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (increment.handleEvent(event)
                || decrement.handleEvent(event)) {
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glClearColor(0f, 0f, 0f, 1.0f);
        gl.glEnable(GL10.GL_COLOR_MATERIAL);
        gl.glFrontFace(GL10.GL_CCW);
        gl.glCullFace(GL10.GL_BACK);

        texture = Texture.create(gl, 256);
        Font font = new Font(Typeface.MONOSPACE, 50, true, texture);
        Font smallFont = new Font(Typeface.MONOSPACE, 10, true, texture);

        counterLabel = new IntLabel(font, 0, 5, 400);
        textLabel = new TextLabel(smallFont, "Hi there! This is a sample text.");

        buttonsFactory = new TexButtonFactory(assets, texture);

        Bitmap bitmap = Bitmaps.loadBitmap(assets, "textures/face.png");
        texture.addBitmap(gl, bitmap);
        bitmap.recycle();

        mesh = new Mesh(6, false, true, false);
        mesh.texcoords(0, 1);
        mesh.vertex(0, 0, 0);

        mesh.texcoords(0, 0);
        mesh.vertex(0, 255, 0);

        mesh.texcoords(1, 1);
        mesh.vertex(255, 0, 0);
//-----------------------------
        mesh.texcoords(0, 0);
        mesh.vertex(0, 255, 0);

        mesh.texcoords(1, 0);
        mesh.vertex(255, 255, 0);

        mesh.texcoords(1, 1);
        mesh.vertex(255, 0, 0);
    }

    public void onSurfaceChanged(GL10 gl, int w, int h) {
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glViewport(0, 0, w, h);
        GLU.gluOrtho2D(gl, 0, w, 0, h);
        gl.glMatrixMode(GL10.GL_MODELVIEW);

        viewport = new Viewport(w, h);

        increment = buttonsFactory.createSimple(gl, "sample", viewport, 40, 30);
        increment.setCoord(0, 30);
        increment.setState(TexButton.ENABLED);
        increment.setListener(incrementor);

        decrement = buttonsFactory.createSimple(gl, "sample", viewport, 40, 30);
        decrement.setCoord(w - 40, 30);
        decrement.setState(TexButton.ENABLED);
        decrement.setListener(decrementor);

        textLabel.setCoord(w / 2, h / 2);
        textLabel.setAlignment(Widget.CENTER);
    }

    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        increment.draw(gl);
        decrement.draw(gl);
        counterLabel.draw(gl);
//        mesh.draw(gl);
//        textLabel.draw(gl);
    }
}
