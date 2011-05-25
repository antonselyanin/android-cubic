package antonj.opengl.widgets.button;

import android.view.MotionEvent;

/**
 * User: Anton
 * Date: 25.03.2010
 * Time: 19:36:09
 */
public abstract class ButtonConfig {
    public abstract String[] getStateResourceNames();

    public abstract boolean handleEvent(
            TexButton button, MotionEvent motionEvent);
}
