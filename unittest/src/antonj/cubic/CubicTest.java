package antonj.cubic;

import antonj.cubic.model.*;
import org.junit.Before;
import org.junit.Test;

import static antonj.cubic.model.FaceConfig.WHITE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: Anton
 * Date: 18.11.2009
 * Time: 23:01:33
 */
//TODO: fix
public class CubicTest {
    private Pieces pieces;
    private Cube cubic;

    @Before
    public void setUp() {
        cubic = new Cube();
        pieces = cubic.pieces;
    }

    @Test
    public void test_Init() {

        cubic.init(new CubeConfig(3));

        assertFaceInitialized(WHITE, cubic.config.size - 1, xyPlaneGetter());
        assertFaceInitialized(FaceConfig.BLUE, 0, xyPlaneGetter());
        assertFaceInitialized(FaceConfig.RED, cubic.size - 1, yzPlaneGetter());
        assertFaceInitialized(FaceConfig.ORANGE, 0, yzPlaneGetter());
        assertFaceInitialized(FaceConfig.GREEN, cubic.size - 1, xzPlaneGetter());
        assertFaceInitialized(FaceConfig.YELLOW, 0, xzPlaneGetter());
    }

    private void assertFaceInitialized(int face, int coord, PieceGetter getter) {
        for (int i = 0; i < cubic.size; i++) {
            for (int j = 0; j < cubic.size; j++) {
                Piece piece = getter.getPiece(i, j, coord);

                String note = String.format("failure for i=%s, j=%s", i, j);
                assertNotNull(note, piece);
                assertEquals(note, face, piece.getColors()[face]);
            }
        }
    }

    private static int[] colors(int front, int right, int back, int left, int top, int bottom) {
        return new int[] {front, right, back, left, top, bottom};
    }

    private static abstract class PieceGetter {
        public abstract Piece getPiece(int i, int j, int coord);
    }

    private CubicTest.PieceGetter xyPlaneGetter() {
        return new PieceGetter() {
            public Piece getPiece(int i, int j, int coord) {
                return pieces.model[i][j][coord];
            }
        };
    }

    private CubicTest.PieceGetter xzPlaneGetter() {
        return new PieceGetter() {
            public Piece getPiece(int i, int j, int coord) {
                return pieces.model[i][coord][j];
            }
        };
    }

    private CubicTest.PieceGetter yzPlaneGetter() {
        return new PieceGetter() {
            public Piece getPiece(int i, int j, int coord) {
                return pieces.model[coord][i][j];
            }
        };
    }
}
