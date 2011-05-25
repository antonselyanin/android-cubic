package antonj.opengl.widgets;

import antonj.opengl.text.Font;

/**
 * User: Anton
 * Date: 10.02.2010
 * Time: 0:14:29
 */
public class TextLabel extends Label {
    private CharSequence text;

    public TextLabel(Font font, CharSequence text) {
        super(font);
        this.text = text;
    }

    public TextLabel(Font font, CharSequence text, float x, float y) {
        super(font, x, y);
        this.text = text;
    }

    public void setText(CharSequence text) {
        if (this.text.equals(text)) {
            return;
        }
        
        this.text = text;
        this.dirty = true;
    }

    @Override
    protected char getCharAt(int i) {
        return text.charAt(i);
    }

    @Override
    protected int getTextLength() {
        return text.length();
    }

    @Override
    protected int getMaxLength() {
        return text.length();
    }
}
