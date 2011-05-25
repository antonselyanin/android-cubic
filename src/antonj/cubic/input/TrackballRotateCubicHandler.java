package antonj.cubic.input;

import android.view.MotionEvent;
import antonj.cubic.MainLoop;

/**
 * User: Anton
 * Date: 22.12.2009
 * Time: 22:14:41
 */
public class TrackballRotateCubicHandler extends MotionEventHandler {
    public static final TrackballRotateCubicHandler INSTANCE = new TrackballRotateCubicHandler();

    private static final float ROTATION_SPEED = 80f;
    
    @Override
    public void handle(MotionEvent motionEvent, MainLoop loop) {
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            float deltaX = -1 * ROTATION_SPEED * motionEvent.getX();
            float deltaY = -1 * ROTATION_SPEED * motionEvent.getY();

            loop.camera.rotate(deltaX, deltaY);
        }
    }
}
