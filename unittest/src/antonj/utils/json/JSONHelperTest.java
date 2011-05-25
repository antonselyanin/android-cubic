package antonj.utils.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * User: Anton
 * Date: 06.02.2010
 * Time: 19:08:58
 */
public class JSONHelperTest {
    @Test
    public void test_ToFloatArray() throws JSONException {
        JSONArray array = new JSONArray().put(1.23).put(3).put(4);

        assertTrue(Arrays.equals(new float[] {1.23f, 3, 4},
                JSON.toFloatArray(array)));
    }

    @Test
    public void test_FloatToJSONArray() throws JSONException {
        JSONArray array = new JSONArray().put(12).put(12.4f).put(5);
        
        assertEquals(JSON.toJSONArray(new float[] {12, 12.4f, 5}).toString(),
                array.toString());
    }

    @Test
    public void test_ToIntArray() throws JSONException {
        JSONArray array = new JSONArray().put(1).put(3).put(4);

        assertTrue(Arrays.equals(new int[] {1, 3, 4},
                JSON.toIntArray(array)));
    }

    @Test
    public void test_IntToJSONArray() throws JSONException {
        JSONArray array = new JSONArray().put(12).put(12).put(5);

        assertEquals(JSON.toJSONArray(new int[] {12, 12, 5}).toString(),
                array.toString());
    }
}
