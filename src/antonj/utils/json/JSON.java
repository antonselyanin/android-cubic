package antonj.utils.json;

import antonj.utils.IO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * User: Anton
 * Date: 06.02.2010
 * Time: 19:07:53
 */
public class JSON {
    public static float[] toFloatArray(JSONArray array)
            throws JSONException {
        float[] result = new float[array.length()];

        for (int i = 0; i < array.length(); i++) {
            result[i] = (float) array.getDouble(i);
        }

        return result;
    }

    public static JSONArray toJSONArray(float[] array)
            throws JSONException {
        JSONArray result = new JSONArray();

        for (float v : array) {
            result.put(v);
        }

        return result;
    }

    public static int[] toIntArray(JSONArray array)
            throws JSONException {
        int[] result = new int[array.length()];

        for (int i = 0; i < array.length(); i++) {
            result[i] = array.getInt(i);
        }

        return result;
    }

    public static JSONArray toJSONArray(int[] array)
            throws JSONException {
        JSONArray result = new JSONArray();

        for (float v : array) {
            result.put(v);
        }

        return result;
    }

    public static JSONObject load(InputStream is)
            throws IOException, JSONException {
        return new JSONObject(IO.getStringAndClose(is));
    }

    public static void save(OutputStream os, JSONObject object) {
        PrintWriter writer = new PrintWriter(os);
        try {
            writer.write(object.toString());
        } finally {
            IO.silentClose(writer);
        }
    }
}
