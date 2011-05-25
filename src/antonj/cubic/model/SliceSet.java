package antonj.cubic.model;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 29.11.2009
 * Time: 21:33:58
 */
public class SliceSet {
    public static final int X_AXIS = 0;
    public static final int Y_AXIS = 1;
    public static final int Z_AXIS = 2;

    public static final float ROTATION_SPEED = 0.2f;

    private int axis;

    private Cube cubic;
    private boolean rotate;
    private float currentAngle;
    private int direction;
    private int rotatedSlice;
    private boolean autoRotation;
    private boolean historyMove;

    private Neighbours neighbours;
    private float targetAngle;
    private static final float AUTOROTATION_ANGLE = 90f;

    public SliceSet(int axis, Cube cubic) {
        this.axis = axis;
        this.cubic = cubic;
    }

    public int getAxis() {
        return axis;
    }

    public boolean isRotate() {
        return rotate;
    }

    public void setNeighbours(Neighbours neighbours) {
        this.neighbours = neighbours;
    }

    public void autorotate(int direction, int slice, boolean historyMove) {
        this.direction = direction;
        this.historyMove = historyMove;
        startRotation(slice);
        setAutorotationTarget();
    }

    public void instantRotation(int direction, int slice) {
        autorotate(direction, slice, true);
        finishRotation();
    }

    public void startRotation(int slice) {
        cubic.lock();
        rotatedSlice = slice;
        rotate = true;

        initSliceMesh();
    }

    //TODO: rename
    public void setAutorotationTarget() {
        targetAngle = AUTOROTATION_ANGLE;
        autoRotation = true;
    }

    public void updateState(long delta) {
        if (!autoRotation) {
            return;
        }

        currentAngle += ROTATION_SPEED * delta;

        if (currentAngle >= targetAngle) {
            finishRotation();
        }
    }

    public void setRotationAngle(float angle) {
        currentAngle = Math.abs(angle);
        direction = angle > 0 ? 1 : -1;
    }

    public void draw(GL10 gl) {
        if (!rotate) {
            return;
        }

        drawSlice(gl);
    }

    private void drawSlice(GL10 gl) {
        float angle = direction
                * currentAngle
                * FaceConfig.AXIS_ROTATION_CORRECTION[axis];

        gl.glPushMatrix();
        setRotationAngle(gl, axis, angle);
        cubic.getSliceMesh().draw(gl);
        gl.glPopMatrix();
    }

    private void setRotationAngle(GL10 gl, int axis, float angle) {
        float rotX = 0;
        float rotY = 0;
        float rotZ = 0;

        if (axis == SliceSet.X_AXIS) {
            rotX = 1.0f;
        } else if (axis == SliceSet.Y_AXIS) {
            rotY = 1.0f;
        } else if (axis == SliceSet.Z_AXIS) {
            rotZ = 1.0f;
        }

        gl.glRotatef(angle, rotX, rotY, rotZ);
    }

    private void finishRotation() {
        rotate = false;
        autoRotation = false;
        currentAngle = 0;
        applyRotation();
        cubic.loadMesh();
        cubic.getSliceMesh().reset();

        cubic.onFinishRotation(axis, rotatedSlice, direction, historyMove);
        cubic.unlock();
    }


    private void initSliceMesh() {
        cubic.getSliceMesh().rewind();
        cubic.getPrimaryMesh().rewind();

        int pieceNumber;
        if (rotatedSlice == 0 || rotatedSlice == cubic.size - 1) {
            pieceNumber = cubic.size * cubic.size;
        } else {
            pieceNumber = (cubic.size - 1) * 4;
        }

        //TODO: extract constant (Cubic.FACES_NUMBER * Cubic.VERTICES_PER_FACE)
        cubic.getSliceMesh().setVerticesNumber(
                pieceNumber * CubeConfig.FACES_NUMBER * CubeConfig.VERTICES_PER_FACE);

        cubic.getPrimaryMesh().setVerticesNumber(
                (cubic.config.pieceNumber - pieceNumber)
                        * CubeConfig.FACES_NUMBER * CubeConfig.VERTICES_PER_FACE);

        for (int i = 0; i < cubic.size; i++) {
            for (int j = 0; j < cubic.size; j++) {
                for (int k = 0; k < cubic.size; k++) {
                    if (!cubic.isVisible(i, j, k)) {
                        continue;
                    }

                    Piece piece = cubic.pieces.getPiece(axis, i, j, k);

                    if (i == rotatedSlice) {
                        piece.loadMesh(cubic.getSliceMesh());
                    } else {
                        piece.loadMesh(cubic.getPrimaryMesh());
                    }
                }
            }
        }
    }

    private void applyRotation() {
        Pieces pieces = cubic.pieces;

        int depth = 1;

        if (rotatedSlice == 0 || rotatedSlice == cubic.size - 1) {
            depth = cubic.size / 2;
        }

        for (int i = 0; i < depth; i++) {
            for (int j = i; j < cubic.size - 1 - i; j++) {
                Piece top = pieces.getPiece(axis, rotatedSlice, i, j);

                if (top == null) {
                    continue;
                }

                Piece right = pieces.getPiece(axis, rotatedSlice, j, cubic.size - 1 - i);
                Piece bottom = pieces.getPiece(axis, rotatedSlice, cubic.size - 1 - i, cubic.size - 1 - j);
                Piece left = pieces.getPiece(axis, rotatedSlice, cubic.size - 1 - j, i);

                int[] topColors = top.getColors();
                int[] rightColors = right.getColors();
                int[] bottomColors = bottom.getColors();
                int[] leftColors = left.getColors();

                if (direction > 0) {
                    top.setColors(leftColors);
                    right.setColors(topColors);
                    bottom.setColors(rightColors);
                    left.setColors(bottomColors);
                } else {
                    top.setColors(rightColors);
                    left.setColors(topColors);
                    bottom.setColors(leftColors);
                    right.setColors(bottomColors);
                }
            }
        }

        for (int i = 0; i < cubic.size; i++) {
            for (int j = 0; j < cubic.size; j++) {

                Piece piece = pieces.getPiece(axis, rotatedSlice, i, j);

                if (piece == null) {
                    continue;
                }

                int[] colors = piece.getColors();

                int topColor = colors[neighbours.top];
                int rightColor = colors[neighbours.right];
                int bottomColor = colors[neighbours.bottom];
                int leftColor = colors[neighbours.left];

                if (direction > 0) {
                    colors[neighbours.top] = leftColor;
                    colors[neighbours.right] = topColor;
                    colors[neighbours.bottom] = rightColor;
                    colors[neighbours.left] = bottomColor;
                } else {
                    colors[neighbours.top] = rightColor;
                    colors[neighbours.left] = topColor;
                    colors[neighbours.bottom] = leftColor;
                    colors[neighbours.right] = bottomColor;
                }
            }
        }
    }
}
