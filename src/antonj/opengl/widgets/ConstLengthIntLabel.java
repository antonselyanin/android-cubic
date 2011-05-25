package antonj.opengl.widgets;

import antonj.opengl.text.Font;

/**
 * User: Anton
 * Date: 13.02.2010
 * Time: 12:13:59
 */
//TODO: test for overflow!
public class ConstLengthIntLabel extends IntLabel {
    private char symbol;
    private int labelLength;

    public ConstLengthIntLabel(Font font, int number, int labelLength, char symbol) {
        super(font, number);
        this.symbol = symbol;

        if (labelLength > IntLabel.MAX_LABEL_LENGTH) {
            throw new IllegalArgumentException();
        }

        this.labelLength = labelLength;
    }

    public ConstLengthIntLabel(Font font, int number, int labelLength, char symbol, float x, float y) {
        this(font, number, labelLength, symbol);
        this.x = x;
        this.y = y;
    }

    @Override
    public void setNumber(int number) {
        super.setNumber(number);

        if (dirty && length > labelLength) {
            labelLength = length;
        }
    }

    @Override
    protected char getCharAt(int i) {
        int delta = labelLength - length;
        if (i < delta) {
            return symbol;
        }

        return data[i - delta];
    }

    @Override
    protected int getTextLength() {
        return labelLength;
    }
}