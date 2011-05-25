package antonj.cubic.input;

import android.view.MotionEvent;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * User: Anton
 * Date: 17.12.2009
 * Time: 23:45:47
 */
public class InputObject {
    public static final int TOUCH_SCREEN = 0;
    public static final int TRACK_BALL = 1;

    private final ArrayBlockingQueue<InputObject> pool;

    public int sourceType;

    public MotionEvent event;

    public InputObject(ArrayBlockingQueue<InputObject> pool) {
        this.pool = pool;
    }

    public void recycle() {
        if (event == null) {
            throw new RuntimeException("object already recycled");
        }

        event.recycle();
        event = null;
        pool.add(this);
    }
}
