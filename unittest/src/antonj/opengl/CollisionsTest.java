package antonj.opengl;

import junit.framework.Assert;
import org.junit.Test;

/**
 * User: Anton
 * Date: 20.12.2009
 * Time: 21:37:30
 */
//TODO: implement
public class CollisionsTest {
    @Test
    public void test_GetIntersectionCoordinate() {
        float[] R0 = {2, 3, 4};
        float[] RD = {0.577f, 0.577f, 0.577f};

        float[] Pn = {1, 0, 0};
        float D = -7;

        float[] result = new float[3];

        Collisions.findIntersectionCoord(R0, RD, Pn, D, result);

        Assert.assertEquals(7.0f, result[0], 0.000001);
        Assert.assertEquals(8.0f, result[1], 0.000001);
        Assert.assertEquals(9.0f, result[2], 0.000001);
    }
}
