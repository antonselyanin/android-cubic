package antonj.cubic.input;

import android.view.MotionEvent;
import antonj.cubic.MainLoop;

/**
 * User: Anton
 * Date: 22.12.2009
 * Time: 22:04:31
 */
public abstract class MotionEventHandler {
    public abstract void handle(MotionEvent event, MainLoop loop);
}
