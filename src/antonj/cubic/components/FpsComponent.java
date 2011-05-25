package antonj.cubic.components;

import antonj.cubic.MainLoop;
import antonj.opengl.utils.FpsCounter;
import antonj.opengl.widgets.CompoundLabel;
import antonj.opengl.widgets.IntLabel;
import antonj.opengl.widgets.TextLabel;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 28.02.2010
 * Time: 20:31:01
 */
public class FpsComponent extends Component {
    private FpsCounter counter;
    private IntLabel fpsInt;
    private CompoundLabel fpsLabel;

    @Override
    public void initWindow(GL10 gl, MainLoop loop) {
        counter = new FpsCounter();
        counter.init();
        fpsInt = new IntLabel(loop.font, 0);
        fpsLabel = new CompoundLabel(0, loop.height, fpsInt,
                new TextLabel(loop.font, " fps"));
    }

    @Override
    public void update(long frameDelta, MainLoop loop) {
        counter.onFrame();
        fpsInt.setNumber(counter.getFps());
        fpsLabel.dirtyFrom(0);
    }

    @Override
    public void drawHud(GL10 gl) {
        fpsLabel.draw(gl);
    }
}
