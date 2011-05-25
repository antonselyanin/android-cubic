package antonj.cubic.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: Anton
 * Date: 09.12.2009
 * Time: 23:34:06
 */
public final class CubeConfig {
    private static final String CUBIC_SIZE_KEY = "CUBIC_SIZE";

    public static final float GAP = 0f;
    public static final float PIECE_SIZE = 2.0f;
    public static final int FACES_NUMBER = 6;
    public static final int VERTICES_PER_FACE = 6;
    public static final int HISTORY_SIZE = 300;

    public final int size;
    public final int pieceNumber;

    public final float shift;
    public final float modelSize;

    //TODO: use cube size from GameInfo
    public CubeConfig(JSONObject storedState)
            throws JSONException {
        this(storedState.getInt(CUBIC_SIZE_KEY));
    }

    public CubeConfig(int size) {
        this.size = size;

        pieceNumber =
                8 + (size - 2) * (size - 2) * 6 + ((size - 2) * 2 * 6);

        shift = (PIECE_SIZE + GAP) * (size - 1) / 2.0f;
        modelSize = (PIECE_SIZE * size + GAP * (size - 1)) / PIECE_SIZE;
    }

    //TODO: use cube size from GameInfo
    public JSONObject save() throws JSONException {
        return new JSONObject().put(CUBIC_SIZE_KEY, size);
    }

    public static float[] VERTICES = {
            //face 0
            -1, -1, 1,
            1, -1, 1,
            1, 1, 1,

            -1, -1, 1,
            1, 1, 1,
            -1, 1, 1,

            //face 1
            1, -1, 1,
            1, -1, -1,
            1, 1, -1,

            1, -1, 1,
            1, 1, -1,
            1, 1, 1,

            //face 2
            1, -1, -1,
            -1, -1, -1,
            -1, 1, -1,

            1, -1, -1,
            -1, 1, -1,
            1, 1, -1,

            //face 3
            -1, -1, -1,
            -1, -1, 1,
            -1, 1, 1,

            -1, -1, -1,
            -1, 1, 1,
            -1, 1, -1,

            //face 4
            1, 1, 1,
            1, 1, -1,
            -1, 1, -1,

            1, 1, 1,
            -1, 1, -1,
            -1, 1, 1,

            //face 5
            -1, -1, 1,
            -1, -1, -1,
            1, -1, -1,

            -1, -1, 1,
            1, -1, -1,
            1, -1, 1
    };

    public static float[] NORMALS = {
            //face 0
            0, 0, 1,
            0, 0, 1,
            0, 0, 1,

            0, 0, 1,
            0, 0, 1,
            0, 0, 1,

            //face 1
            1, 0, 0,
            1, 0, 0,
            1, 0, 0,

            1, 0, 0,
            1, 0, 0,
            1, 0, 0,

            //face 2
            0, 0, -1,
            0, 0, -1,
            0, 0, -1,

            0, 0, -1,
            0, 0, -1,
            0, 0, -1,

            //face 3
            -1, 0, 0,
            -1, 0, 0,
            -1, 0, 0,

            -1, 0, 0,
            -1, 0, 0,
            -1, 0, 0,

            //face 4
            0, 1, 0,
            0, 1, 0,
            0, 1, 0,

            0, 1, 0,
            0, 1, 0,
            0, 1, 0,

            //face 5
            0, -1, 0,
            0, -1, 0,
            0, -1, 0,

            0, -1, 0,
            0, -1, 0,
            0, -1, 0,
    };
}
