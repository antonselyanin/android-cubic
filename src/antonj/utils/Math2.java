package antonj.utils;

import java.util.Random;

/**
 * User: Anton
 * Date: 12.12.2009
 * Time: 13:45:28
 */
public final class Math2 {
    public static final float PI = 3.14159265358979323846f;
    public static final Random RNG = new Random();

    public static float toRadians(float degrees) {
        return degrees * PI / 180f; 
    }
}
