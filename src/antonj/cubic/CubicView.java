package antonj.cubic;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.SystemClock;
import android.view.MotionEvent;
import antonj.cubic.input.InputObject;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 26.10.2009
 * Time: 22:44:29
 */
public class CubicView
        extends GLSurfaceView implements GLSurfaceView.Renderer {
    public static String TAG = CubicView.class.getSimpleName();

    private long lastDrawTime = 0;
    private long frameDelta = 0;

    private MainLoop loop;
    private int width;
    private int height;
    private float fov;
    private float near;
    private float far;
    private float ratio;

    public CubicView(Context context, MainLoop loop) {
        super(context);
        this.loop = loop;
        setRenderer(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        loop.input.feed(InputObject.TOUCH_SCREEN, event);
        
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }

        return true;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        loop.input.feed(InputObject.TRACK_BALL, event);

        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }

        return true;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glClearColor(0f, 0f, 0f, 1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glEnable(GL10.GL_COLOR_MATERIAL);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glFrontFace(GL10.GL_CCW);
        gl.glCullFace(GL10.GL_BACK);
    }

    public void onSurfaceChanged(GL10 gl, int w, int h) {
        width = w;
        height = h;
        ratio = ((float) w) / ((float) h);
        fov = 70.0f;
        near = 0.01f;
        far = 100.f;
        
        gl.glViewport(0, 0, w, h);
        loop.projector.setViewport(w, h);
        loop.projector.perspective(fov, ratio, near, far);

        loop.initWindow(gl, w, h);

        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);

        float ambient[] = {0.1f, 0.1f, 0.1f, 1.0f};
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambient, 0);

        float diffuse[] = {0.8f, 0.8f, 0.8f, 1.0f};
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuse, 0);

        float specular[] = {0.9f, 0.9f, 0.9f, 1.0f};
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specular, 0);

        float light0Position[] = {10.0f, 10.0f, 10.0f, 0.0f};
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, light0Position, 0);
    }

    public void onDrawFrame(GL10 gl) {
        perspectiveProjection(gl);
        loop.updateState(frameDelta);
        loop.draw(gl);

        hudProjection(gl);
        loop.drawHud(gl);

        if (lastDrawTime > 0) {
            frameDelta = SystemClock.uptimeMillis() - lastDrawTime;
        }
        lastDrawTime = SystemClock.uptimeMillis();
    }

    private void hudProjection(GL10 gl) {
        gl.glDisable(GL10.GL_LIGHTING);
        gl.glDisable(GL10.GL_DEPTH_TEST);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluOrtho2D(gl, 0, width, 0, height);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    private void perspectiveProjection(GL10 gl) {
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, fov, ratio, near, far);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    }
}
