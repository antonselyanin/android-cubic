package antonj.cubic.components;

import android.view.MotionEvent;
import antonj.cubic.MainLoop;
import antonj.cubic.input.RotateSliceHandler;
import antonj.cubic.input.TouchRotateCubicHandler;
import antonj.cubic.input.TrackballRotateCubicHandler;
import antonj.opengl.widgets.button.ButtonListener;
import antonj.opengl.widgets.button.TexButton;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 04.03.2010
 * Time: 23:28:18
 */
public class CubeRotationComponent
        extends Component implements ButtonListener {
    private TexButton rotationToggle;

    private boolean touchRotation = false;

    @Override
    public void initWindow(GL10 gl, MainLoop loop) {
        rotationToggle = loop.buttons.createToggle(gl, "rotate", loop.viewport, 48, 48);
        rotationToggle.setCoord(loop.width / 2 - 24, 48);
        rotationToggle.setState(TexButton.ENABLED);
        rotationToggle.setListener(this);
    }

    @Override
    public void handleTouchEvent(MotionEvent event, MainLoop loop) {
        if (loop.state != MainLoop.STATE_PLAYING) {
            return;
        }

        if(rotationToggle.handleEvent(event)) {
            return;
        }

        if (touchRotation) {
            TouchRotateCubicHandler.INSTANCE.handle(event, loop);
        } else {
            RotateSliceHandler.INSTANCE.handle(event, loop);
        }
    }

    @Override
    public void handleTrackballEvent(MotionEvent event, MainLoop loop) {
        TrackballRotateCubicHandler.INSTANCE.handle(event, loop);
    }

    @Override
    public void onClick() {
        touchRotation = !touchRotation;
    }

    @Override
    public void drawHud(GL10 gl) {
        rotationToggle.draw(gl);
    }
}
