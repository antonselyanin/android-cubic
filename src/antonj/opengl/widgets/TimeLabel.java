package antonj.opengl.widgets;

import antonj.opengl.text.Font;
import antonj.utils.IntegerHelper;

/**
 * User: Anton
 * Date: 17.02.2010
 * Time: 22:23:02
 */
public class TimeLabel extends Label {
    private char[] data;
    private long time;

    public TimeLabel(Font font) {
        this(font, 0, 0);
    }

    public TimeLabel(Font font, float x, float y) {
        super(font, x, y);
        data = new char[] {'0', '0', ':', '0', '0', ':', '0', '0'};
    }

    public void setTime(long time) {
        time /= 1000;

        if (this.time == time) {
            return;
        }

        this.time = time;
        dirty = true;

        int seconds = (int) (time % 60);

        time /= 60;
        int minutes = (int) (time % 60);

        time /= 60;

        int hours = (int) time;

        IntegerHelper.getTwoDigitsChars(seconds, data, 6);
        IntegerHelper.getTwoDigitsChars(minutes, data, 3);
        IntegerHelper.getTwoDigitsChars(hours, data, 0);
    }

    @Override
    protected char getCharAt(int i) {
        return data[i];
    }

    @Override
    protected int getMaxLength() {
        return data.length;
    }

    @Override
    protected int getTextLength() {
        return data.length;
    }
}
