package antonj.cubic.model;

import antonj.opengl.Mesh;

import java.nio.FloatBuffer;
import java.util.Arrays;

/**
* User: Anton
* Date: 16.11.2009
* Time: 19:24:33
*/

public class Piece {
    public float[] vertices;
    private int[] colors;

    public Piece() {
        colors = new int[CubeConfig.FACES_NUMBER];
        Arrays.fill(colors, FaceConfig.GREY);

        vertices = CubeConfig.VERTICES.clone();
    }

    public void setCoords(float x, float y, float z) {
        for (int i = 0; i < CubeConfig.VERTICES.length / 3; i++) {
            vertices[3 * i] = CubeConfig.VERTICES[3 * i] + x;
            vertices[3 * i + 1] = CubeConfig.VERTICES[3 * i + 1] + y;
            vertices[3 * i + 2] = CubeConfig.VERTICES[3 * i + 2] + z;
        }
    }

    public void setColor(int face, int color) {
        colors[face] = color;
    }

    public int getColor(int face) {
        return colors[face];
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public void loadMesh(Mesh mesh) {
        FloatBuffer verticesBuffer = mesh.getVerticesBuffer();
        FloatBuffer colorsBuffer = mesh.getColorsBuffer();
        FloatBuffer texBuffer = mesh.getTexcoordsBuffer();
        FloatBuffer normalsBuffer = mesh.getNormalsBuffer();

        verticesBuffer.put(vertices);

        for (int color : colors) {
            for (int j = 0; j < CubeConfig.VERTICES_PER_FACE; j++) {
                colorsBuffer.put(FaceConfig.COLORS[color]);
            }
        }

        for (int color : colors) {
            if (color == FaceConfig.GREY) {
                texBuffer.put(FaceConfig.EMPTY_UV);
            } else {
                texBuffer.put(FaceConfig.FACE_UV);
            }
        }

        normalsBuffer.put(CubeConfig.NORMALS);
    }
}
