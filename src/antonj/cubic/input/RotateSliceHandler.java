package antonj.cubic.input;

import android.view.MotionEvent;
import antonj.cubic.MainLoop;
import antonj.cubic.model.FaceConfig;
import antonj.cubic.model.FaceSettings;
import antonj.cubic.model.SliceSet;
import antonj.opengl.Collisions;
import antonj.utils.IntPair;

/**
 * User: Anton
 * Date: 22.12.2009
 * Time: 22:05:44
 */
public class RotateSliceHandler extends MotionEventHandler {
    public static final RotateSliceHandler INSTANCE = new RotateSliceHandler();

    public static float MOVE_THRESHOLD = 2.0f;
    
    private int touchedFace = -1;
    private IntPair touchedPiece = new IntPair();


    private float[] startCoord = new float[3];
    private float[] currentCoord = new float[3];

    @Override
    public void handle(MotionEvent motionEvent, MainLoop loop) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (loop.cube.isLocked()) {
                    return;
                }

                handleActionDown(motionEvent, loop);
                
                break;

            case MotionEvent.ACTION_MOVE:
                handleActionMove(motionEvent, loop);

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
                touchedFace = -1;

                break;
        }
    }

    private void handleActionDown(MotionEvent motionEvent, MainLoop loop) {
        loop.projector.calcTouchRay(motionEvent.getX(), motionEvent.getY(), loop.touch);

        int face = loop.cube.getTouchedFace(loop.touch);

        if (face >= 0 && loop.cube.faces[face].touch(loop.touch, touchedPiece)) {
            touchedFace = face;

            FaceSettings faceSettings = FaceConfig.FACES[touchedFace];
            Collisions.findIntersectionCoord(
                    loop.touch.origin,
                    loop.touch.destination,
                    faceSettings.normal,
                    -loop.cube.config.modelSize,
                    startCoord);
        }
    }

    private void handleActionMove(MotionEvent motionEvent, MainLoop loop) {
        if (touchedFace < 0) {
            return;
        }

        loop.projector.calcTouchRay(motionEvent.getX(), motionEvent.getY(), loop.touch);

        FaceSettings faceSettings = FaceConfig.FACES[touchedFace];
        Collisions.findIntersectionCoord(
                loop.touch.origin,
                loop.touch.destination,
                faceSettings.normal,
                -loop.cube.config.modelSize,
                currentCoord);

        float x = 0;
        float y = 0;
        int sliceX = -1;
        int sliceY = -1;

        if (faceSettings.axis == SliceSet.X_AXIS) {
            x = currentCoord[1] - startCoord[1];
            y = currentCoord[2] - startCoord[2];
            sliceX = SliceSet.Z_AXIS;
            sliceY = SliceSet.Y_AXIS;
        } else if (faceSettings.axis == SliceSet.Y_AXIS) {
            x = currentCoord[0] - startCoord[0];
            y = currentCoord[2] - startCoord[2];
            sliceX = SliceSet.Z_AXIS;
            sliceY = SliceSet.X_AXIS;
        } else if (faceSettings.axis == SliceSet.Z_AXIS) {
            x = currentCoord[0] - startCoord[0];
            y = currentCoord[1] - startCoord[1];
            sliceX = SliceSet.Y_AXIS;
            sliceY = SliceSet.X_AXIS;
        }

        int direction = 0;
        int slice = -1;
        int rotationCorrection = 0;
        int axis = -1;
        if (Math.abs(x) > MOVE_THRESHOLD) {
            if (x > 0) {
                direction = 1;
            } else {
                direction = -1;
            }
            axis = sliceX;
            slice = touchedPiece.second;
            rotationCorrection = faceSettings.correctX;
        } else if (Math.abs(y) > MOVE_THRESHOLD) {
            if (y > 0) {
                direction = 1;
            } else {
                direction = -1;
            }
            slice = touchedPiece.first;
            rotationCorrection = faceSettings.correctY;
            axis = sliceY;
        }

        if (axis >=0) {
            loop.cube.rotate(axis, slice, direction * rotationCorrection);
            touchedFace = -1;
        }
    }
}
