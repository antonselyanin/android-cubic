package antonj.cubic.model;

import antonj.opengl.texture.TexDetails;

import static antonj.utils.Numbers.floats;

/**
 * User: Anton
 * Date: 16.11.2009
 * Time: 15:56:31
 */
public class FaceConfig {
    public static final int WHITE = 0;
    public static final int RED = 1;
    public static final int BLUE = 2;
    public static final int ORANGE = 3;
    public static final int GREEN = 4;
    public static final int YELLOW = 5;

    public static final byte GREY = 6;

    private static final boolean FIRST_SLICE = true;
    private static final boolean LAST_SLICE = false;

    public static FaceSettings[] FACES = {
            new FaceSettings(SliceSet.Z_AXIS, LAST_SLICE, floats(0, 0, -1), 1, 1),  //white
            new FaceSettings(SliceSet.X_AXIS, LAST_SLICE, floats(1, 0, 0), -1, -1),  //red
            new FaceSettings(SliceSet.Z_AXIS, FIRST_SLICE, floats(0, 0, 1), -1, -1), //blue
            new FaceSettings(SliceSet.X_AXIS, FIRST_SLICE, floats(-1, 0, 0), 1, 1), //orange
            new FaceSettings(SliceSet.Y_AXIS, LAST_SLICE, floats(0, 1, 0), 1, -1),  //green
            new FaceSettings(SliceSet.Y_AXIS, FIRST_SLICE, floats(0, -1, 0), -1, 1)  //yellow
    };

    public static float[][] COLORS = {
            {1.0f, 1.0f, 1.0f}, //white
            {1.0f, 0.0f, 0.0f}, //red
            {0.0f, 0.0f, 1.0f}, //blue
            {1.0f, 0.5f, 0.0f}, //orange
            {0.0f, 1.0f, 0.0f}, //green
            {1.0f, 1.0f, 0.0f}, //yellow

            {0.7f, 0.7f, 0.7f}, //grey
    };

    public static int[] AXIS_ROTATION_CORRECTION = {
            -1,  //red == X
            1,   //green == Y
            -1,  //white == Z
    };

    public static Neighbours[] NEIGHBOURS = {
            new Neighbours(YELLOW, WHITE, GREEN, BLUE), //red == X
            new Neighbours(ORANGE, WHITE, RED,   BLUE), //green == Y
            new Neighbours(ORANGE, GREEN, RED,   YELLOW) //white == Z
    };

    public static float[] EMPTY_UV = new float[CubeConfig.VERTICES_PER_FACE * 2];
    public static float[] FACE_UV;

    public static void initFaceTexture(TexDetails tex) {
        FACE_UV = new float[] {
                tex.left, tex.top,
                tex.right, tex.top,
                tex.right, tex.bottom,

                tex.left, tex.top,
                tex.right, tex.bottom,
                tex.left, tex.bottom
        };
    }
}
