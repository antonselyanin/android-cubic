package antonj.cubic.history;

/**
 * User: Anton
 * Date: 23.02.2010
 * Time: 13:26:01
 */
public class Move {
    public int axis;
    public int slice;
    public int direction;

    Move next;
    Move previous;

    public void set(int axis, int slice, int direction) {
        this.axis = axis;
        this.slice = slice;
        this.direction = direction;
    }

    void recycle() {
        next = null;
        previous = null;
    }

}
