package antonj.cubic;

import antonj.cubic.model.CubeConfig;
import antonj.cubic.model.FaceConfig;
import antonj.cubic.model.Piece;
import antonj.cubic.model.Pieces;
import org.junit.Test;

import static org.junit.Assert.assertSame;

/**
 * User: Anton
 * Date: 16.11.2009
 * Time: 20:19:23
 */

//TODO: exclude from APK?
public class CubicModelTest {
    @Test
    public void test() {
        Pieces model = new Pieces(new CubeConfig(3));

        Piece[][][] pieces = model.model;

        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces.length; j++) {
                for (int k = 0; k < pieces.length; k++) {
                    pieces[i][j][k] = new Piece();
                }
            }
        }

        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces.length; j++) {
                assertSame(String.format("failure for i=%s, j=%s", i, j),
                        pieces[i][j][2], model.getPiece(FaceConfig.WHITE, i, j));
            }
        }

    }
}
