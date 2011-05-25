package antonj.utils;

import junit.framework.Assert;
import org.junit.Test;

/**
 * User: Anton
 * Date: 21.03.2010
 * Time: 20:13:09
 */
public class DatesTest {
    @Test
    public void test_TimeToString() {
        assertTimeToString(40, "40 sec");
        assertTimeToString(minutes(40) + 4, "40 min 4 sec");
        assertTimeToString(hours(3) + minutes(40) + 4, "3 hrs 40 min 4 sec");
    }

    private int minutes(int min) {
        return min * 60;
    }

    private int hours(int hr) {
        return hr * minutes(60);
    }

    private void assertTimeToString(long time, String expected) {
        Assert.assertEquals(expected, Dates.timeToString(time * 1000));
    }
}
