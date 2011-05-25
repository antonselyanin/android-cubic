package antonj.cubic;

import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.FloatMath;
import antonj.opengl.Projector;
import antonj.utils.json.JSON;
import org.json.JSONException;
import org.json.JSONObject;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 17.12.2009
 * Time: 23:04:00
 */
public class Camera {
    public float[] eye = new float[4];
    public float[] up = new float[4];

    private float[] tempUp = {0, 1, 0, 0};
    private float[] tempEye = {0, 0, 0, 1};

    private Projector projector;
    private float distance;

    public Camera(Projector projector) {
        this.projector = projector;
    }

    public void setAngles(float distance, float phi, float theta) {
        float cosPhi = FloatMath.cos(phi);
        float sinPhi = FloatMath.sin(phi);
        float sinTheta = FloatMath.sin(theta);
        float cosTheta = FloatMath.cos(theta);

        eye[0] = distance * cosPhi * sinTheta;
        eye[1] = distance * sinPhi;
        eye[2] = distance * cosPhi * cosTheta;
        eye[3] = 1;

        up[0] = -sinPhi * sinTheta;
        up[1] = cosPhi;
        up[2] = -sinPhi * cosTheta;
        up[3] = 0;

        this.distance = distance;
    }

    public void rotate(float sideAngle, float upAngle) {
        Matrix.invertM(projector.prevModelview2, 0, projector.modelview2, 0);

        Matrix.rotateM(projector.prevModelview2, 0, upAngle, 1, 0, 0);
        Matrix.rotateM(projector.prevModelview2, 0, sideAngle, 0, 1, 0);

        Matrix.multiplyMV(up, 0, projector.prevModelview2, 0, tempUp, 0);
        tempEye[2] = this.distance;
        Matrix.multiplyMV(eye, 0, projector.prevModelview2, 0, tempEye, 0);
    }

    public void position(GL10 gl) {
        lookAt(gl,
                eye[0], eye[1], eye[2],
                0, 0, 0,
                up[0], up[1], up[2]);
    }
    
    private void lookAt(GL10 gl, float eyeX, float eyeY, float eyeZ,
                        float centerX, float centerY, float centerZ,
                        float upX, float upY, float upZ) {
        GLU.gluLookAt(gl, eyeX, eyeY,
                eyeZ, centerX, centerY, centerZ, upX, upY, upZ);

        projector.lootAt(eyeX, eyeY,
                eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    private static final String EYE_KEY = "EYE";
    private static final String UP_KEY = "UP";
    private static final String DISTANCE_KEY = "DISTANCE";

    public void load(JSONObject stored)
            throws JSONException {
        eye = JSON.toFloatArray(stored.getJSONArray(EYE_KEY));
        up = JSON.toFloatArray(stored.getJSONArray(UP_KEY));
        distance = (float) stored.getDouble(DISTANCE_KEY);
    }

    public JSONObject save() throws JSONException {
        return new JSONObject().put(EYE_KEY, JSON.toJSONArray(eye))
                .put(UP_KEY, JSON.toJSONArray(up))
                .put(EYE_KEY, JSON.toJSONArray(eye))
                .put(DISTANCE_KEY, distance);
    }
}
