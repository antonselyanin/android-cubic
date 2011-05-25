package antonj.cubic.components;

import android.os.SystemClock;
import antonj.cubic.MainLoop;
import antonj.cubic.model.CubeConfig;
import antonj.opengl.widgets.Label;
import antonj.opengl.widgets.TimeLabel;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 28.02.2010
 * Time: 20:48:50
 */
public class TimeComponent extends Component {
    private TimeLabel timeLabel;
    private MainLoop loop;

    public TimeComponent(MainLoop loop) {
        this.loop = loop;
    }

    @Override
    public void init(CubeConfig config) {
        loop.gameInfo.startTime = SystemClock.uptimeMillis();
        loop.gameInfo.spendTime = 0;
    }

    @Override
    public void update(long frameDelta, MainLoop loop) {
        if (loop.state != MainLoop.STATE_PLAYING) {
            return;
        }

        loop.gameInfo.spendTime += frameDelta;
        timeLabel.setTime(loop.gameInfo.spendTime);
    }

    @Override
    public void initWindow(GL10 gl, MainLoop loop) {
        timeLabel = new TimeLabel(loop.font, loop.width, loop.height);
        timeLabel.setAlignment(Label.RIGHT);
    }

    @Override
    public void drawHud(GL10 gl) {
        timeLabel.draw(gl);
    }
}
