package antonj.cubic.model;

/**
 * User: Anton
 * Date: 16.11.2009
 * Time: 16:48:29
 */
//TODO: implement
//TODO: merge with cubic?
public class Pieces {
    public Piece[][][] model;
    private CubeConfig config;

    public Pieces(CubeConfig config) {
        this.config = config;
        model = new Piece[config.size][config.size][config.size];
    }

    public Piece getPiece(int face, int x, int y) {
        FaceSettings faceSettings = FaceConfig.FACES[face];

        int axis = faceSettings.axis;
        int slice = getSlice(faceSettings);

        return getPiece(axis, slice, x, y);
    }

    private int getSlice(FaceSettings faceSettings) {
        if (faceSettings.firstSlice) {
            return 0;
        } else {
            return config.size - 1;
        }
    }

    public Piece getPiece(int axis, int slice, int x, int y) {
        if (axis == SliceSet.X_AXIS) {
            return model[slice][x][y];
        } else if (axis == SliceSet.Y_AXIS) {
            return model[x][slice][y];
        } else if (axis == SliceSet.Z_AXIS) {
            return model[x][y][slice];
        }

        throw new RuntimeException("Unknown axis");
    }
}
