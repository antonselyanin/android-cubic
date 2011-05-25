package antonj.cubic.model;

/**
 * User: Anton
 * Date: 20.12.2009
 * Time: 23:51:09
 */
public class FaceSettings {
    public int axis;
    public boolean firstSlice;
    public float[] normal;

    //TODO: rename
    public int correctX;
    //TODO: rename
    public int correctY;

    public FaceSettings(int axis, boolean firstSlice,
                        float[] normal, int correctX, int correctY) {
        this.axis = axis;
        this.firstSlice = firstSlice;
        this.normal = normal;
        this.correctX = correctX;
        this.correctY = correctY;
    }
}
