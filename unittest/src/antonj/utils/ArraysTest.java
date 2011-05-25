package antonj.utils;

import org.junit.Assert;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

/**
 * User: Anton
 * Date: 24.11.2009
 * Time: 0:24:23
 */
public class ArraysTest {
    @Test
    public void test_Reverse() {
        int[] array = {1, 2, 3};
        Arrays2.reverse(array);
        assertArrayEquals(new int[] {3, 2, 1}, array);

        int[] array2 = {1, 2, 3, 4};
        Arrays2.reverse(array2);
        assertArrayEquals(new int[] {4, 3, 2, 1}, array2);
    }
}
