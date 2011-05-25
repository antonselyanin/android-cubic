package antonj.cubic.input;

import android.view.MotionEvent;
import antonj.cubic.MainLoop;
import antonj.opengl.widgets.button.TexButton;

/**
 * User: Anton
 * Date: 28.02.2010
 * Time: 22:15:10
 */

//TODO: use/remove?
public class ButtonEventHandler extends MotionEventHandler {
    private TexButton button;

    public ButtonEventHandler(TexButton button) {
        this.button = button;
    }

    @Override
    public void handle(MotionEvent event, MainLoop loop) {
        button.handleEvent(event);
    }
}
