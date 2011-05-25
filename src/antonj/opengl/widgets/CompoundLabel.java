package antonj.opengl.widgets;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 15.02.2010
 * Time: 12:37:01
 */
public class CompoundLabel {
    public Label[] labels;
    private int dirtyIndex;

    private float x;
    private float y;
    private int align = Label.LEFT;

    public CompoundLabel(float x, float y, Label... labels) {
        this.x = x;
        this.y = y;
        this.labels = labels;
        dirtyIndex = 0;
    }

    public void draw(GL10 gl) {
        rebuild(gl);

        for (Label label : labels) {
            label.draw(gl);
        }
    }

    public void dirtyFrom(int index) {
        if (dirtyIndex < 0 || dirtyIndex > index) {
            dirtyIndex = index;
        }
    }

    public void rebuild(GL10 gl) {
        if (dirtyIndex < 0) {
            return;
        }

        Label first = labels[dirtyIndex];
        
        float prevEndX = x;
        if (dirtyIndex > 0) {
            prevEndX = labels[dirtyIndex - 1].getEndX();
        }

        first.setCoord(prevEndX, y);
        first.rebuild(gl);

        float endX = first.getEndX();

        for (int i = dirtyIndex + 1; i < labels.length; i++) {
            Label label = labels[i];

            label.setCoord(endX, y);
            label.rebuild(gl);

            endX = label.getEndX();
        }

        dirtyIndex = -1;
    }
}
