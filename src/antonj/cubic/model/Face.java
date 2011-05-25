package antonj.cubic.model;

import antonj.opengl.Collisions;
import antonj.opengl.TouchRay;
import antonj.utils.IntPair;
import antonj.utils.json.JSON;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: Anton
 * Date: 16.11.2009
 * Time: 15:48:28
 */
public class Face {
    public int id;
    private Cube cubic;

    public Face(int id) {
        this.id = id;
    }

    public void init(Cube cubic) {
        this.cubic = cubic;
        assignColors();
    }

    public void load(Cube cubic, JSONObject storedState)
            throws JSONException {
        this.cubic = cubic;
        load(storedState);
    }

    private void assignColors() {
        for (int i = 0; i < cubic.size; i++) {
            for (int j = 0; j < cubic.size; j++) {
                Piece piece = cubic.pieces.getPiece(id, i, j);
                piece.setColor(id, id);
            }
        }
    }

    public boolean isSolved() {
        Pieces pieces = cubic.pieces;
        int cubeSize = cubic.size;

        int currentColor = pieces.
                getPiece(id, 0, 0).getColor(id);

        for (int i = 0; i < cubeSize; i++) {
            for (int j = 0; j < cubeSize; j++) {
                Piece piece = pieces.getPiece(id, i, j);
                if (piece.getColor(id) != currentColor) {
                    return false;
                }
            }
        }

        return true; 
    }

    public boolean touch(TouchRay touch, IntPair coords) {
        for (int i = 0; i < cubic.size; i++) {
            for (int j = 0; j < cubic.size; j++) {
                Piece piece = cubic.pieces.getPiece(id, i, j);

                for (int k = 0; k < 2; k++) {
                    if (Collisions.intersectTriangle(
                            touch.origin, touch.direction, piece.vertices, (id * 2 + k) * 3 * 3)) {

                        coords.first = i;
                        coords.second = j;

                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static final String ELEMENTS_KEY = "ELEMENTS";

    private void load(JSONObject storedState)
            throws JSONException {
        int[] colors = JSON.toIntArray(storedState.getJSONArray(ELEMENTS_KEY));

        for (int i = 0; i < cubic.size; i++) {
            for (int j = 0; j < cubic.size; j++) {
                Piece piece = cubic.pieces.getPiece(id, i, j);
                piece.setColor(id, colors[i * cubic.size + j]);
            }
        }
    }

    public JSONObject save() throws JSONException {
        int[] colors = new int[cubic.size * cubic.size];

        for (int i = 0; i < cubic.size; i++) {
            for (int j = 0; j < cubic.size; j++) {
                Piece piece = cubic.pieces.getPiece(id, i, j);
                colors[i * cubic.size + j] = piece.getColors()[id];
            }
        }

        return new JSONObject().put(ELEMENTS_KEY, JSON.toJSONArray(colors));
    }
}
