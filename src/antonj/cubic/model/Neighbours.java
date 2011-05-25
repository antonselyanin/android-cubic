package antonj.cubic.model;

/**
 * User: Anton
 * Date: 19.12.2009
 * Time: 23:23:41
 */
public class Neighbours {
    public Neighbours(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public int left;
    public int top;
    public int right;
    public int bottom;
}
