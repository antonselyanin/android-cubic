package antonj.cubic.components;

import android.view.MotionEvent;
import antonj.cubic.MainLoop;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 28.02.2010
 * Time: 20:48:50
 */
//TODO: turn off in production
public class TestComponent extends Component {
    private MainLoop loop;
    private int soundId;

    public TestComponent(MainLoop loop) {
        this.loop = loop;
    }

    @Override
    public void initWindow(GL10 gl, MainLoop loop) {
/*
        try {
            AssetFileDescriptor descriptor = loop.assets.openFd("sounds/rotate.wav");
            soundId = loop.soundPool.load(descriptor, 1);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
*/
    }

    @Override
    public void handleTouchEvent(MotionEvent event, MainLoop loop) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            int volume = loop.audioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
//            loop.soundPool.play(soundId, volume, volume, 1, 0, 1);
        }
    }
}