package antonj.cubic.components;

import android.view.MotionEvent;
import antonj.cubic.MainLoop;
import antonj.cubic.records.BestPlayer;
import antonj.cubic.saves.GameInfo;
import antonj.opengl.Mesh;
import antonj.opengl.widgets.Label;
import antonj.opengl.widgets.TextLabel;
import antonj.utils.Dates;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 28.02.2010
 * Time: 20:48:50
 */
public class FinishScreenComponent extends Component {
    private static final String BEST_TIME_PREFIX = "Best time: ";
    private static final String SPEND_TIME_PREFIX = "Your time: ";
    
    private TextLabel congratulationsLabel;
    private TextLabel spendTimeLabel;
    private TextLabel bestTimeLabel;
    private TextLabel tapToFinishLabel;
    private MainLoop loop;
    private Mesh screenCover;

    private boolean initialized = false;

    public FinishScreenComponent(MainLoop loop) {
        this.loop = loop;
    }

    @Override
    public void initWindow(GL10 gl, MainLoop loop) {
        int centerWidth = loop.width / 2;
        int height = loop.height / 2 + loop.bigFont.getLineHeight();

        congratulationsLabel = new TextLabel(loop.bigFont, "SOLVED");
        congratulationsLabel.setCoord(centerWidth, height);
        congratulationsLabel.setAlignment(Label.CENTER);

        height -= 2 * loop.bigFont.getLineHeight();
        spendTimeLabel = new TextLabel(loop.font, SPEND_TIME_PREFIX);
        spendTimeLabel.setCoord(centerWidth, height);
        spendTimeLabel.setAlignment(Label.CENTER);

        height -= loop.font.getLineHeight();
        bestTimeLabel = new TextLabel(loop.font, BEST_TIME_PREFIX);
        bestTimeLabel.setCoord(centerWidth, height);
        bestTimeLabel.setAlignment(Label.CENTER);

        height -= 2 * loop.font.getLineHeight();
        tapToFinishLabel = new TextLabel(loop.font, "tap to return to main menu");
        tapToFinishLabel.setCoord(centerWidth, height);
        tapToFinishLabel.setAlignment(Label.CENTER);

        screenCover = new Mesh(6, false, true, false);

        screenCover.vertex(0, loop.height, 0);
        screenCover.vertex(0, 0, 0);
        screenCover.vertex(loop.width, 0, 0);
        screenCover.vertex(0, loop.height, 0);
        screenCover.vertex(loop.width, 0, 0);
        screenCover.vertex(loop.width, loop.height, 0);
    }

    @Override
    public void drawHud(GL10 gl) {
        if (loop.state != MainLoop.STATE_SOLVED) {
            return;
        }

        if (!initialized) {
            initLabels();
            initialized = true;
        }

        drawScreenCover(gl);
        congratulationsLabel.draw(gl);
        spendTimeLabel.draw(gl);
        bestTimeLabel.draw(gl);
        tapToFinishLabel.draw(gl);
    }

    private void initLabels() {
        GameInfo gameInfo = loop.gameInfo;
        spendTimeLabel.setText(SPEND_TIME_PREFIX +
                Dates.timeToString(gameInfo.spendTime));

        BestPlayer record = loop.record;

        if (record == null ||
                record.spendTime > gameInfo.spendTime) {
            bestTimeLabel.setText(BEST_TIME_PREFIX + "YOURS!");
        } else {
            bestTimeLabel.setText(BEST_TIME_PREFIX
                    + Dates.timeToString(record.spendTime));
        }
    }

    private void drawScreenCover(GL10 gl) {
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glColor4f(1.0f, 1.0f, 1.0f, 0.6f);
        screenCover.draw(gl);
        gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void handleTouchEvent(MotionEvent event, MainLoop loop) {
        if (loop.state != MainLoop.STATE_SOLVED) {
            return;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                loop.finishGame();
                break;
        }
    }
}