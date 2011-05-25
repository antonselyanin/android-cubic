package antonj.opengl.widgets.button;

import android.view.MotionEvent;

/**
 * User: Anton
 * Date: 25.03.2010
 * Time: 19:28:58
 */
public class ToggleButtonConfig extends ButtonConfig {
    public static final ToggleButtonConfig INSTANCE = new ToggleButtonConfig();

    private static final String[] BUTTON_STATES = new String[] {"0", "1"};


    @Override
    public String[] getStateResourceNames() {
        return BUTTON_STATES;
    }

    @Override
    public boolean handleEvent(TexButton button, MotionEvent motionEvent) {
/*
        if (button.state == TexButton.DISABLED) {
            return false;
        }
*/

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (button.hit(motionEvent.getX(), motionEvent.getY())) {
                    int newState = (button.state + 1) % BUTTON_STATES.length;
                    button.setState(newState);
                    if (button.listener != null) {
                        button.listener.onClick();
                    }

                    return true;
                }
            }
            break;
            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL: {
/*
                if (button.state == TexButton.PRESSED) {
                    button.state = TexButton.ENABLED;
                    return true;
                }
*/
            }
            break;
        }

        return false;
    }
}