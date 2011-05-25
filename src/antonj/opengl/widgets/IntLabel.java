package antonj.opengl.widgets;

import antonj.opengl.text.Font;
import antonj.utils.IntegerHelper;

/**
 * User: Anton
 * Date: 13.02.2010
 * Time: 12:13:59
 */
public class IntLabel extends Label {
    public static final int MAX_LABEL_LENGTH = 11;
    
    private int number;
    protected char[] data;
    protected int length;

    public IntLabel(Font font, int number) {
        super(font);
        data = new char[MAX_LABEL_LENGTH];
        this.number = number;
        length = IntegerHelper.getChars(number, data);
    }

    public IntLabel(Font font, int number, float x, float y) {
        this(font, number);
        this.x = x;
        this.y = y;
    }

    public void setNumber(int number) {
        if (this.number == number) {
            return;
        }

        this.number = number;
        length = IntegerHelper.getChars(number, data);
        dirty = true;
    }

    @Override
    protected int getMaxLength() {
        return MAX_LABEL_LENGTH;
    }

    @Override
    protected char getCharAt(int i) {
        return data[i];
    }

    @Override
    protected int getTextLength() {
        return length;
    }
}
