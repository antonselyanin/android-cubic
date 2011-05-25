package antonj.cubic.model;

import antonj.cubic.history.Move;
import antonj.cubic.history.MoveHistory;
import antonj.opengl.Collisions;
import antonj.opengl.Mesh;
import antonj.opengl.TouchRay;
import org.json.JSONException;
import org.json.JSONObject;

import javax.microedition.khronos.opengles.GL10;

import static antonj.cubic.model.CubeConfig.*;

/**
 * User: Anton
 * Date: 16.11.2009
 * Time: 15:49:34
 */
public class Cube {
    private static final String TAG = Cube.class.getSimpleName();
    private static final String FACE_KEY_PREFIX = "FACE_";
    private static final String HISTORY_STATE_KEY = "HISTORY_STATE_KEY";

    private Mesh primaryMesh;
    private Mesh sliceMesh;

    public Pieces pieces;
    public Face[] faces;
    public SliceSet[] slices;
    
    private float[] model;

    private boolean locked;

    public CubeConfig config;
    public int size;

    public boolean solved = false;

    public MoveHistory history;

    private static final int MAX_LISTENERS = 10;
    private IListener[] cubeListeners = new IListener[MAX_LISTENERS];

    public interface IListener {
        void onStartRotation(int axis, int slice, int direction, boolean historical);
        void onStopRotation(int axis, int slice, int direction, boolean historical);
    }

    public boolean isLocked() {
        return locked;
    }

    public Cube() {
    }

    private void initModel() {
        size = config.size;
        pieces = new Pieces(config);
        faces = new Face[FACES_NUMBER];

        for (int i = 0; i < faces.length; i++) {
            faces[i] = new Face(i);
        }

        slices = new SliceSet[3];

        model = CubeConfig.VERTICES.clone();

        for (int i = 0; i < model.length; i++) {
            model[i] *= config.modelSize;
        }

        primaryMesh = new Mesh(config.pieceNumber *
                FACES_NUMBER * VERTICES_PER_FACE, true, true, true);
        sliceMesh = new Mesh(size * size *
                FACES_NUMBER * VERTICES_PER_FACE, true, true, true);
    }

    public void init(CubeConfig config) {
        this.config = config; 
        initModel();
        initPieces();
        initFaces();
        history = new MoveHistory(CubeConfig.HISTORY_SIZE);
    }

    public void load(CubeConfig config, JSONObject storedState)
            throws JSONException {
        this.config = config;
        initModel();
        initPieces();
        loadFaces(storedState);
        history = MoveHistory.load(
                storedState.getJSONObject(HISTORY_STATE_KEY));
    }

    public void lock() {
        if (locked) {
            throw new RuntimeException("Cubic is already locked");
        }

        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    public Mesh getPrimaryMesh() {
        return primaryMesh;
    }

    public Mesh getSliceMesh() {
        return sliceMesh;
    }

    public boolean isVisible(int i, int j, int k) {
        return isVisible(i) || isVisible(j) || isVisible(k);
    }

    private boolean isVisible(int i) {
        return i == 0 || i == size - 1;
    }

    private void initFaces() {
        for (Face face : faces) {
            face.init(this);
        }

        basicInit();
    }

    private void loadFaces(JSONObject storedState)
            throws JSONException {
        for (Face face : faces) {
            face.load(this, storedState.getJSONObject(FACE_KEY_PREFIX + face.id));
        }

        basicInit();
    }

    private void basicInit() {
        for (int i = 0; i < slices.length; i++) {
            slices[i] = new SliceSet(i, this);
        }

        for (SliceSet slice : slices) {
            slice.setNeighbours(FaceConfig.NEIGHBOURS[slice.getAxis()]);
        }
    }

    private void initPieces() {
        for (int i = 0; i < pieces.model.length; i++) {
            for (int j = 0; j < pieces.model.length; j++) {
                for (int k = 0; k < pieces.model.length; k++) {
                    if (!isVisible(i, j, k)) {
                        continue;
                    }

                    Piece piece = new Piece();
                    pieces.model[i][j][k] = piece;

                    piece.setCoords(
                            (PIECE_SIZE + GAP) * i - config.shift,
                            (PIECE_SIZE + GAP) * j - config.shift,
                            (PIECE_SIZE + GAP) * k - config.shift);
                }
            }
        }
    }

    public void rotate(int axis, int slice, int direction) {
        rotate0(axis, slice, direction, false);
    }

    public void instantRotation(int axis, int slice, int direction) {
        slices[axis].instantRotation(direction, slice);
    }

    private void rotate0(int axis, int slice, int direction, boolean historical) {
        slices[axis].autorotate(direction, slice, historical);
        onStartListeners(axis, slice, direction, historical);
    }

    public void onFinishRotation(int axis, int slice, int direction, boolean historical) {
        if (!historical) {
            history.addMove(axis, slice, direction);
        }

        solved = calcSolved();

        onStopListeners(axis, slice, direction, historical);
    }

    private void onStartListeners(int axis, int slice, int direction, boolean historical) {
        for (IListener cubeListener : cubeListeners) {
            if (cubeListener == null) {
                break;
            }

            cubeListener.onStartRotation(axis, slice, direction, historical);
        }
    }

    private void onStopListeners(int axis, int slice, int direction, boolean historical) {
        for (IListener cubeListener : cubeListeners) {
            if (cubeListener == null) {
                break;
            }

            cubeListener.onStopRotation(axis, slice, direction, historical);
        }
    }

    public void undo() {
        if (history.hasUndo()) {
            Move move = history.undo();
            rotate0(move.axis, move.slice, -move.direction, true);
        }
    }

    public void redo() {
        if (history.hasRedo()) {
            Move move = history.redo();
            rotate0(move.axis, move.slice, move.direction, true);
        }
    }

    public int getTouchedFace(TouchRay touch) {
        for (int i = 0; i < FACES_NUMBER * 2; i++) {
            if (Collisions.intersectTriangle(
                    touch.origin, touch.direction, model, i * 3 * 3)) {
                return i / 2;
            }
        }

        return -1;
    }

    public void updateState(long frameDelta) {
        for (SliceSet slice : slices) {
            slice.updateState(frameDelta);
        }
    }

    private boolean calcSolved() {
        for (Face face : faces) {
            if (!face.isSolved()) {
                return false;
            }
        }

        return true;
    }

    public void draw(GL10 gl) {
        for (SliceSet slice : slices) {
            slice.draw(gl);
        }

        gl.glPushMatrix();
        primaryMesh.draw(gl);
        gl.glPopMatrix();
    }

    public void addListener(IListener listener) {
        for (int i = 0; i < cubeListeners.length; i++) {
            if(cubeListeners[i] == null) {
                cubeListeners[i] = listener;
                return;
            }
        }

        throw new IllegalStateException("Too many listeners");
    }

    public void loadMesh() {
        primaryMesh.setVerticesNumber(config.pieceNumber * FACES_NUMBER * VERTICES_PER_FACE);
        primaryMesh.rewind();

        for (int i = 0; i < pieces.model.length; i++) {
            for (int j = 0; j < pieces.model.length; j++) {
                for (int k = 0; k < pieces.model.length; k++) {
                    if (!isVisible(i, j, k)) {
                        continue;
                    }

                    pieces.model[i][j][k].
                            loadMesh(primaryMesh);
                }
            }
        }
    }

    public JSONObject save() 
            throws JSONException {
        JSONObject object = new JSONObject();

        for (Face face : faces) {
            object.put(FACE_KEY_PREFIX + face.id, face.save());
        }

        object.put(HISTORY_STATE_KEY, history.save());

        return object;
    }
}
