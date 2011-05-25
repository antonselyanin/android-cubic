package antonj.cubic.input;

import android.view.MotionEvent;
import antonj.cubic.MainLoop;

/**
 * User: Anton
 * Date: 22.12.2009
 * Time: 22:14:41
 */
public class TouchRotateCubicHandler extends MotionEventHandler {
    public static final TouchRotateCubicHandler INSTANCE = new TouchRotateCubicHandler();

    private float theta;
    private float phi;

    private float prevX;
    private float prevY;

    private boolean touched;

    @Override
    public void handle(MotionEvent motionEvent, MainLoop loop) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:

                prevX = motionEvent.getX();
                prevY = motionEvent.getY();

                touched = true;

                break;

            case MotionEvent.ACTION_MOVE:

                if (!touched) {
                    break;
                }

                float deltaX = prevX - motionEvent.getX();
                theta += deltaX * 0.01f;
                prevX = motionEvent.getX();

                float deltaY = prevY - motionEvent.getY();
                phi += -deltaY * 0.01f;
                prevY = motionEvent.getY();

                loop.camera.rotate(deltaX, deltaY);

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
                touched = false;
                break;
        }
    }
}