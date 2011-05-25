package antonj.cubic.history;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * User: Anton
 * Date: 23.02.2010
 * Time: 13:20:51
 */
public class MoveHistoryTest {
    private MoveHistory history;

    @Before
    public void setUp() {
        history = new MoveHistory(5);
        assertFalse(history.hasUndo());
    }

    @Test
    public void test_SimpleUndoRedo() {
        addMove(1);

        undo(1, false, true);
        redo(1, true, false);
    }

    @Test
    public void test_MultipleUndo() {
        addMove(1);
        addMove(2);
        addMove(3);

        undo(3, true, true);
        undo(2, true, true);
        undo(1, false, true);
    }

    @Test
    public void test_UndoOverflow() {
        addMove(1);
        addMove(2);
        addMove(3);
        addMove(4);
        addMove(5);
        addMove(6);
        addMove(7);

        undo(7, true, true);
        undo(6, true, true);
        undo(5, true, true);
        undo(4, true, true);
        undo(3, false, true);
    }

    @Test
    public void test_MoveAfterUndo() {
        addMove(1);
        addMove(2);
        addMove(3);
        addMove(4);

        undo(4, true, true);
        undo(3, true, true);

        addMove(5);

        undo(5, true, true);
        undo(2, true, true);
        undo(1, false, true);
    }

    @Test
    public void test_PartHistory_SaveLoad() throws JSONException {
        history = new MoveHistory(6);

        addMove(1);
        addMove(2);
        addMove(3);
        addMove(4);

        history.undo();
        history.undo();

        JSONObject saved = history.save();
        MoveHistory restored = MoveHistory.load(saved);

        redo(3, true, true, restored);
        redo(4, true, false, restored);

        undo(4, true, true, restored);
        undo(3, true, true, restored);
        undo(2, true, true, restored);
        undo(1, false, true, restored);
    }

    @Test
    public void test_FullHistory_SaveLoad() throws JSONException {
        history = new MoveHistory(6);

        addMove(1);
        addMove(2);
        addMove(3);
        addMove(4);
        addMove(5);
        addMove(6);

        history.undo();
        history.undo();
        history.undo();

        JSONObject saved = history.save();
        MoveHistory restored = MoveHistory.load(saved);

        redo(4, true, true, restored);
        redo(5, true, true, restored);
        redo(6, true, false, restored);

        undo(6, true, true, restored);
        undo(5, true, true, restored);
        undo(4, true, true, restored);
        undo(3, true, true, restored);
        undo(2, true, true, restored);
        undo(1, false, true, restored);
    }

    @Test
    public void test_PartHistory_End_SaveLoad() throws JSONException {
        history = new MoveHistory(6);

        addMove(1);
        addMove(2);
        addMove(3);

        JSONObject saved = history.save();
        MoveHistory restored = MoveHistory.load(saved);

        undo(3, true, true, restored);
        undo(2, true, true, restored);
        undo(1, false, true, restored);
    }

    @Test
    public void test_PartHistory_Start_SaveLoad() throws JSONException {
        history = new MoveHistory(6);

        addMove(1);
        addMove(2);
        addMove(3);

        undo(3, true, true);
        undo(2, true, true);
        undo(1, false, true);

        JSONObject saved = history.save();
        MoveHistory restored = MoveHistory.load(saved);

        redo(1, true, true, restored);
        redo(2, true, true, restored);
        redo(3, true, false, restored);
    }

    @Test
    public void test_Empty_SaveLoad() throws JSONException {
        history = new MoveHistory(6);

        JSONObject saved = history.save();
        MoveHistory restored = MoveHistory.load(saved);

        assertFalse(restored.hasUndo());
        assertFalse(restored.hasRedo());
    }

    private void redo(int moveId, boolean hasUndo, boolean hasRedo) {
        redo(moveId, hasUndo, hasRedo, history);
    }

    private void redo(int moveId, boolean hasUndo, boolean hasRedo, MoveHistory history) {
        assertMove(history.redo(), moveId);
        assertEquals(hasUndo, history.hasUndo());
        assertEquals(hasRedo, history.hasRedo());
    }

    private void undo(int moveId, boolean hasUndo, boolean hasRedo) {
        undo(moveId, hasUndo, hasRedo, history);
    }

    private void undo(int moveId, boolean hasUndo, boolean hasRedo, MoveHistory history) {
        assertMove(history.undo(), moveId);
        assertEquals(hasUndo, history.hasUndo());
        assertEquals(hasRedo, history.hasRedo());
    }

    private void assertMove(Move move, int moveId) {
        assertMove(move, moveId, moveId, moveId);
    }

    private void addMove(int move) {
        history.addMove(move, move, move);
        assertTrue(history.hasUndo());
        assertFalse(history.hasRedo());
    }

    private void assertMove(Move move,
            int axis, int slice, int direction) {
        assertNotNull(move);
        assertEquals("axis", axis, move.axis);
        assertEquals("slice", slice, move.slice);
        assertEquals("direction", direction, move.direction);
    }
}
