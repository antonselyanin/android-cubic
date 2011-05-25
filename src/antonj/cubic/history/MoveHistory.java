package antonj.cubic.history;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: Anton
 * Date: 23.02.2010
 * Time: 13:19:55
 */
public class MoveHistory {
    private static final String SIZE_KEY = "SIZE";
    private static final String MOVES_NUMBER_KEY = "MOVES_NUMBER";
    private static final String CURRENT_KEY = "CURRENT";
    private static final String AXIS_PREFIX = "AXIS_";
    private static final String SLICE_PREFIX = "SLICE_";
    private static final String DIRECTION_PREFIX = "DIRECTION_";

    private Move poolHead;
    private Move end = new Move();
    private Move current;
    private int size;

    public MoveHistory(int size) {
        poolHead = new Move();

        Move currentPooled = poolHead;

        this.size = size;
        for (int i = 0; i < this.size; i++) {
            Move pooled = new Move();
            currentPooled.next = pooled;
            currentPooled = pooled;
        }

        current = end;
    }

    public void addMove(int axis, int slice, int direction) {
        Move move = createMove();
        move.set(axis, slice, direction);

        if (current.next != null) {
            recycleRedoList();
        }

        current.next = move;
        move.previous = current;
        current = move;
    }

    private void recycleRedoList() {
        Move node = current.next;

        while (node != null) {
            Move next = node.next;

            moveToPool(node);

            node = next;
        }
    }

    public Move undo() {
        if (!hasUndo()) {
            throw new IllegalStateException("No undo elements");
        }

        current = current.previous;
        return current.next;
    }

    public Move redo() {
        if (!hasRedo()) {
            throw new IllegalStateException("No redo elements");
        }

        current = current.next;
        return current;
    }

    private Move createMove() {
        if (poolHead.next != null) {
            return getFromPool();
        }

        return recycleLast();
    }

    private void moveToPool(Move move) {
        move.recycle();
        move.next = poolHead.next;
        poolHead.next = move;
    }

    private Move getFromPool() {
        Move result = poolHead.next;
        poolHead.next = result.next;
        result.recycle();

        return result;
    }

    private Move recycleLast() {
        Move move;
        move = end.next;
        end.next = end.next.next;
        end.next.previous = end;
        move.recycle();
        return move;
    }

    public boolean hasUndo() {
        return current.previous != null;
    }

    public boolean hasRedo() {
        return current.next != null;
    }

    public JSONObject save() throws JSONException {
        JSONObject result = new JSONObject()
                .put(SIZE_KEY, size);

        int movesCounter = 0;
        int currentIndex = -1;
        Move currentSaved = end.next;

        while (currentSaved != null) {
            if (currentSaved == current) {
                currentIndex = movesCounter;
            }

            result.put(AXIS_PREFIX + movesCounter, currentSaved.axis)
                    .put(SLICE_PREFIX + movesCounter, currentSaved.slice)
                    .put(DIRECTION_PREFIX + movesCounter, currentSaved.direction);

            movesCounter++;

            currentSaved = currentSaved.next;
        }

        result.put(MOVES_NUMBER_KEY, movesCounter);
        result.put(CURRENT_KEY, currentIndex);

        return result;
    }

    public static MoveHistory load(JSONObject state) throws JSONException {
        MoveHistory result = new MoveHistory(state.getInt(SIZE_KEY));

        int movesNumber = state.getInt(MOVES_NUMBER_KEY);

        for (int i = 0; i < movesNumber; i++) {
            int axis = state.getInt(AXIS_PREFIX + i);
            int slice = state.getInt(SLICE_PREFIX + i);
            int direction = state.getInt(DIRECTION_PREFIX + i);

            result.addMove(axis, slice, direction);
        }

        int currentIndex = state.getInt(CURRENT_KEY);

        for (int i = 0; i < movesNumber - currentIndex - 1; i++) {
            result.undo();
        }

        return result;
    }
}
