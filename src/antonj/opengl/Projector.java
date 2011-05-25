package antonj.opengl;

import android.opengl.Matrix;
import antonj.utils.Arrays2;

import javax.microedition.khronos.opengles.GL10;
import java.util.Arrays;

/**
 * User: Anton
 * Date: 06.12.2009
 * Time: 12:08:26
 */
public class Projector {
    private float[] projection = new float[16];
    private float[] modelview = new float[16];
    //TODO: rename
    //TODO: this is a temp buffer
    public float[] prevModelview2 = new float[16];
    //TODO: rename
    public float[] modelview2 = new float[16];

    private int[] viewport = new int[4];

    public void setViewport(int w, int h) {
        viewport[0] = 0;
        viewport[1] = 0;
        viewport[2] = w;
        viewport[3] = h;
    }

    public void frustum(float left, float right,
                        float bottom, float top, float near, float far) {
        Matrix.frustumM(projection, 0, left, right, top, bottom, near, far);
    }

    public void perspective(float fov, float ratio, float near, float far) {
        float ymax = near * (float) Math.tan(fov * Math.PI / 360f);
        float ymin = -ymax;

        float xmin = ymin * ratio;
        float xmax = ymax * ratio;
        frustum(xmin, xmax, ymin, ymax, near, far);
    }

    private float[] forward = new float[3];
    private float[] side = new float[3];
    private float[] up = new float[3];

    public void lootAt(float eyex, float eyey, float eyez, float centerx,
                       float centery, float centerz, float upx, float upy,
                       float upz) {
        forward[0] = centerx - eyex;
        forward[1] = centery - eyey;
        forward[2] = centerz - eyez;

        up[0] = upx;
        up[1] = upy;
        up[2] = upz;

        normalize(forward);

        /* Side = forward x up */
        Arrays2.cross3(side, forward, up);
        normalize(side);

        /* Recompute up as: up = side x forward */
        Arrays2.cross3(up, side, forward);

        modelview[0] = side[0];
        modelview[1] = up[0];
        modelview[2] = -forward[0];
        modelview[3] = 0.0f;

        modelview[4] = side[1];
        modelview[5] = up[1];
        modelview[6] = -forward[1];
        modelview[7] = 0.0f;

        modelview[8] = side[2];
        modelview[9] = up[2];
        modelview[10] = -forward[2];
        modelview[11] = 0.0f;

        modelview[12] = 0.0f;
        modelview[13] = 0.0f;
        modelview[14] = 0.0f;
        modelview[15] = 1.0f;

        System.arraycopy(modelview, 0, modelview2, 0, modelview.length);
        Matrix.translateM(modelview, 0, -eyex, -eyey, -eyez);
    }

    private static void makeIdentity(float[] m) {
        m[0 + 4 * 0] = 1;
        m[0 + 4 * 1] = 0;
        m[0 + 4 * 2] = 0;
        m[0 + 4 * 3] = 0;

        m[1 + 4 * 0] = 0;
        m[1 + 4 * 1] = 1;
        m[1 + 4 * 2] = 0;
        m[1 + 4 * 3] = 0;

        m[2 + 4 * 0] = 0;
        m[2 + 4 * 1] = 0;
        m[2 + 4 * 2] = 1;
        m[2 + 4 * 3] = 0;

        m[3 + 4 * 0] = 0;
        m[3 + 4 * 1] = 0;
        m[3 + 4 * 2] = 0;
        m[3 + 4 * 3] = 1;
    }

    public static void normalize(float[] vector) {
        float length = Matrix.length(vector[0], vector[1], vector[2]);
        vector[0] /= length;
        vector[1] /= length;
        vector[2] /= length;
    }

    private static final float[] _in = new float[4];
    private static final float[] _out = new float[4];
    private static final float[] _m = new float[16];
    private static final float[] _a = new float[16];

    public int unproject(float winx, float winy, float winz,
                          float[] xyz, int offset) {

        float model[] = modelview;
        float proj[] = projection;

        /* Normalize between -1 and 1 */
        _in[0] = (winx - viewport[0]) * 2f / viewport[2] - 1.0f;
        _in[1] = (winy - viewport[1]) * 2f / viewport[3] - 1.0f;
        _in[2] = 2f * winz - 1.0f;
        _in[3] = 1.0f;

        /* Get the inverse */
        Matrix.multiplyMM(
                _a, 0,
                proj, 0,
                model, 0);
        Matrix.invertM(
                _m, 0,
                _a, 0);

        Matrix.multiplyMV(
                _out, 0,
                _m, 0,
                _in, 0);

        //TODO: epsilon
        if (_out[3] == 0.0f) {
            return GL10.GL_FALSE;
        }

        xyz[offset] = _out[0] / _out[3];
        xyz[offset + 1] = _out[1] / _out[3];
        xyz[offset + 2] = _out[2] / _out[3];

        return GL10.GL_TRUE;
    }

    public void calcTouchRay(float x, float y, TouchRay touch) {
        unproject(x, y, 0, touch.origin, 0);
        unproject(x, y, 1f, touch.destination, 0);

        touch.direction[0] = touch.destination[0] - touch.origin[0];
        touch.direction[1] = touch.destination[1] - touch.origin[1];
        touch.direction[2] = touch.destination[2] - touch.origin[2];

        normalize(touch.direction);
    }

    @Override
    public String toString() {
        return "Projector{" +
                "projection=" + Arrays.toString(projection) +
                ", modelview=" + Arrays.toString(modelview) +
                ", viewport=" + Arrays.toString(viewport) +
                '}';
    }
}
