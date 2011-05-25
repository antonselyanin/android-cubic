package antonj.opengl.texture;

import antonj.opengl.Mesh;

/**
 * User: Anton
 * Date: 20.02.2010
 * Time: 13:41:02
 */
public class TexDetails {
    public float left;
    public float top;
    public float right;
    public float bottom;

    public int width;
    public int height;

    public TexDetails(
            float left,
            float top,
            float right,
            float bottom,
            int width, int height) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.width = width;
        this.height = height;
    }

    public void addTexturedQuad(Mesh mesh,
            float left, float top, float right, float bottom) {
        mesh.texcoords(this.left, this.top);
        mesh.vertex(left, top, 0);

        mesh.texcoords(this.left, this.bottom);
        mesh.vertex(left, bottom, 0);

        mesh.texcoords(this.right, this.bottom);
        mesh.vertex(right, bottom, 0);

        mesh.texcoords(this.left, this.top);
        mesh.vertex(left, top, 0);

        mesh.texcoords(this.right, this.bottom);
        mesh.vertex(right, bottom, 0);

        mesh.texcoords(this.right, this.top);
        mesh.vertex(right, top, 0);
    }

    public void addTexturedQuad(Mesh mesh, float left, float top) {
        float right1 = left + width;
        float bottom1 = top - height;

        addTexturedQuad(mesh, left, top, right1, bottom1);
    }

    @Override
    public String toString() {
        return "TexDetails{" +
                "left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
