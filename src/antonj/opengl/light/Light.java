package antonj.opengl.light;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 10.03.2010
 * Time: 11:34:23
 */
//TODO: implement/remove?
public class Light {
    private float[] position;
    private float[] direction;
    private float[] target;

    public static Light create(GL10 gl, int lightId) {
        gl.glEnable(lightId);

        float ambient[] = {0.05f, 0.05f, 0.05f, 1.0f};
        gl.glLightfv(lightId, GL10.GL_AMBIENT, ambient, 0);

        float diffuse[] = {0.5f, 0.5f, 0.5f, 1.0f};
        gl.glLightfv(lightId, GL10.GL_DIFFUSE, diffuse, 0);

        float specular[] = {0.7f, 0.7f, 0.7f, 1.0f};
        gl.glLightfv(lightId, GL10.GL_SPECULAR, specular, 0);

        float position[] = {10.0f, 10.0f, 10.0f, 0.0f};
        gl.glLightfv(lightId, GL10.GL_POSITION, position, 0);

        float direction[] = {0.0f, 0.0f, -1.0f};
        gl.glLightfv(lightId, GL10.GL_SPOT_DIRECTION, direction, 0);

        return new Light();
    }
}
