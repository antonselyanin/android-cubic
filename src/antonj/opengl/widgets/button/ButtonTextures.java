package antonj.opengl.widgets.button;

import antonj.opengl.Mesh;
import antonj.opengl.texture.TexDetails;
import antonj.opengl.texture.Texture;

/**
 * User: Anton
 * Date: 20.02.2010
 * Time: 15:59:37
 */
public class ButtonTextures {
    public Texture texture;
    public TexDetails[] stateDetails;

    public ButtonTextures(Texture texture, TexDetails[] stateDetails) {
        this.texture = texture;
        this.stateDetails = stateDetails;
    }

    public void setStateTexture(int state, Mesh mesh,
            float x, float y, float width, float height) {
        mesh.reset();
        stateDetails[state].addTexturedQuad(mesh,
                x, y, x + width, y - height);
    }
}
