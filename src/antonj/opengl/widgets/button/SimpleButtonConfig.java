package antonj.opengl.widgets.button;

import android.view.MotionEvent;

/**
 * User: Anton
 * Date: 25.03.2010
 * Time: 19:28:58
 */
public class SimpleButtonConfig extends ButtonConfig {
    public static final SimpleButtonConfig INSTANCE = new SimpleButtonConfig();
    
    public static final String[] BUTTON_STATES =
            {"enabled", "disabled", "pressed"};

    @Override
    public String[] getStateResourceNames() {
        return BUTTON_STATES;
    }

    @Override
    public boolean handleEvent(TexButton button, MotionEvent motionEvent) {
        if (button.state == TexButton.DISABLED) {
            return false;
        }

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (button.hit(motionEvent.getX(), motionEvent.getY())) {
                    if (button.listener != null) {
                        button.listener.onClick();
                    }

                    button.setState(TexButton.PRESSED);
                    return true;
                }
            }
            break;
            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL: {
                if (button.state == TexButton.PRESSED) {
                    button.setState(TexButton.ENABLED);
                    return true;
                }
            }
            break;
        }

        return false;
    }
}
