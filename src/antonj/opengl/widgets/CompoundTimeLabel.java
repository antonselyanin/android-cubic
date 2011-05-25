package antonj.opengl.widgets;

import antonj.opengl.text.Font;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 15.02.2010
 * Time: 17:01:14
 */
public class CompoundTimeLabel {
    private static int HOURS = 0;
    private static int MINUTES = 2;
    private static int SECONDS = 4;

    private CompoundLabel label;
    private IntLabel hoursLabel;
    private IntLabel minutesLabel;
    private IntLabel secondsLabel;

    private long time;

    public CompoundTimeLabel(Font font, long time, float x, float y) {
        this.time = time / 1000;

        hoursLabel = createLabel(font);
        minutesLabel = createLabel(font);
        secondsLabel = createLabel(font);

        label = new CompoundLabel(x, y,
                hoursLabel, delimiterLabel(font),
                minutesLabel, delimiterLabel(font),
                secondsLabel);
    }

    public void setTime(long time) {
        time /= 1000;

        if (this.time == time) {
            return;
        }

        this.time = time;

        int seconds = (int) (time % 60);

        time /= 60;
        int minutes = (int) (time % 60);

        time /= 60;

        int hours = (int) time;

        hoursLabel.setNumber(hours);
        if (hoursLabel.dirty) {
            label.dirtyFrom(HOURS);
        }

        minutesLabel.setNumber(minutes);
        if (minutesLabel.dirty) {
            label.dirtyFrom(MINUTES);
        }

        secondsLabel.setNumber(seconds);
        if (secondsLabel.dirty) {
            label.dirtyFrom(SECONDS);
        }
    }

    private TextLabel delimiterLabel(Font font) {
        return new TextLabel(font, ":");
    }

    private ConstLengthIntLabel createLabel(Font font) {
        return new ConstLengthIntLabel(font, 0, 2, '0');
    }

    public void draw(GL10 gl) {
        label.draw(gl);
    }
}
