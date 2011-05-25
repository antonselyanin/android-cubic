package antonj.cubic.components;

import android.view.MotionEvent;
import antonj.cubic.MainLoop;
import antonj.cubic.history.MoveHistory;
import antonj.cubic.model.Cube;
import antonj.opengl.widgets.button.ButtonListener;
import antonj.opengl.widgets.button.TexButton;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 28.02.2010
 * Time: 21:08:46
 */
public class HistoryComponent extends Component {
    private TexButton undoButton;
    private TexButton redoButton;
    private MainLoop loop;
    private MoveHistory history;

    public HistoryComponent(MainLoop loop) {
        this.loop = loop;
    }

    @Override
    public void initWindow(GL10 gl, MainLoop loop) {
        history = loop.cube.history;
        undoButton = loop.buttons.createSimple(gl, "undo", loop.viewport, 48, 48);
        undoButton.setCoord(0, 48);
        undoButton.setState(TexButton.ENABLED);
        undoButton.setListener(new UndoHandler());

        redoButton = loop.buttons.createSimple(gl, "redo", loop.viewport, 48, 48);
        redoButton.setCoord(loop.width - 48, 48);
        redoButton.setState(TexButton.ENABLED);
        redoButton.setListener(new RedoHandler());
    }

    @Override
    public void handleTouchEvent(MotionEvent event, MainLoop loop) {
        if (loop.state != MainLoop.STATE_PLAYING) {
            return;
        }

        undoButton.handleEvent(event);
        redoButton.handleEvent(event);
    }

    @Override
    public void drawHud(GL10 gl) {
        undoButton.draw(gl);
        redoButton.draw(gl);
    }

    @Override
    public void update(long frameDelta, MainLoop loop) {
        if (history.hasUndo()) {
            if (undoButton.state == TexButton.DISABLED) {
                undoButton.setState(TexButton.ENABLED);
            }
        } else {
            undoButton.setState(TexButton.DISABLED);
        }

        if (history.hasRedo()) {
            if (redoButton.state == TexButton.DISABLED) {
                redoButton.setState(TexButton.ENABLED);
            }
        } else {
            redoButton.setState(TexButton.DISABLED);
        }
    }

    private class UndoHandler implements ButtonListener {
        @Override
        public void onClick() {
            Cube cube = loop.cube;
            if (!cube.isLocked()) {
                cube.undo();
            }
        }
    }

    private class RedoHandler implements ButtonListener {
        @Override
        public void onClick() {
            Cube cube = loop.cube;
            if (!cube.isLocked()) {
                cube.redo();
            }
        }
    }
}
