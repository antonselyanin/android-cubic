package antonj.opengl;

import java.util.Arrays;

/**
 * User: Anton
 * Date: 05.12.2009
 * Time: 0:28:52
 */
public class TouchRay {
    public float[] origin = new float[3];
    public float[] destination = new float[3];
    public float[] direction = new float[3];

    

    public void setOrigin(float x, float y, float z) {
        origin[0] = x;
        origin[1] = y;
        origin[2] = z;
    }

    public void setDirection(float x, float y, float z) {
        direction[0] = x;
        direction[1] = y;
        direction[2] = z;
    }

    @Override
    public String toString() {
        return "TouchRay{" +
                "origin=" + Arrays.toString(origin) +
                ", direction=" + Arrays.toString(direction) +
                '}';
    }
}
