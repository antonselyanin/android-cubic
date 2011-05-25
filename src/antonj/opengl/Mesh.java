package antonj.opengl;

import javax.microedition.khronos.opengles.GL10;
import java.nio.FloatBuffer;

/**
 * User: Anton
 * Date: 08.02.2010
 * Time: 22:32:43
 */
public class Mesh {

    private float[] vertices;
    private float[] texcoords;
    private float[] colors;
    private float[] normals;

    private FloatBuffer verticesBuffer;
    private FloatBuffer texcoordsBuffer;
    private FloatBuffer colorsBuffer;
    private FloatBuffer normalsBuffer;

    private int verticesNumber;

    public Mesh(int maxVertices, boolean withColors, boolean withTexture, boolean withNormals) {
        vertices = new float[maxVertices * 3];
        verticesBuffer = FloatBuffer.wrap(vertices);

        if (withTexture) {
            texcoords = new float[maxVertices * 2];
            texcoordsBuffer = FloatBuffer.wrap(texcoords);
        }

        if (withColors) {
            colors = new float[maxVertices * 3];
            colorsBuffer = FloatBuffer.wrap(colors);
        }

        if (withNormals) {
            normals = new float[maxVertices * 3];
            normalsBuffer = FloatBuffer.wrap(normals);
        }

        verticesNumber = 0;
    }

    public FloatBuffer getVerticesBuffer() {
        return verticesBuffer;
    }

    public FloatBuffer getColorsBuffer() {
        return colorsBuffer;
    }

    public FloatBuffer getNormalsBuffer() {
        return normalsBuffer;
    }

    public FloatBuffer getTexcoordsBuffer() {
        return texcoordsBuffer;
    }

    public void setVerticesNumber(int verticesNumber) {
        this.verticesNumber = verticesNumber;
    }

    public void reset() {
        verticesNumber = 0;
    }

    public void vertex(float x, float y, float z) {
        int index = verticesNumber * 3;

        vertices[index] = x;
        vertices[index + 1] = y;
        vertices[index + 2] = z;
        verticesNumber++;
    }

    public void nextVertex() {
        verticesNumber++;
    }

    public void texcoords(float u, float v) {
        int index = verticesNumber * 2;
        texcoords[index] = u;
        texcoords[index + 1] = v;
    }

    public void color(float r, float g, float b) {
        int index = verticesNumber * 3;

        colors[index] = r;
        colors[index + 1] = g;
        colors[index + 2] = b;
    }

    public void rewind() {
        verticesBuffer.rewind();

        if (texcoords != null) {
            texcoordsBuffer.rewind();
        }

        if (colors != null) {
            colorsBuffer.rewind();
        }

        if (normals != null) {
            normalsBuffer.rewind();
        }
    }

    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);

        if (colors != null) {
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            gl.glColorPointer(3, GL10.GL_FLOAT, 0, colorsBuffer);
        }

        if (normals != null) {
            gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
            gl.glNormalPointer(GL10.GL_FLOAT, 0, normalsBuffer);
        }

        if (texcoords != null) {
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glEnable(GL10.GL_TEXTURE_2D);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texcoordsBuffer);
        }

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, verticesNumber);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        if (texcoords != null) {
            gl.glDisable(GL10.GL_TEXTURE_2D);
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }

        if (colors != null) {
            gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        }

        if (normals != null) {
            gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        }
    }
}
