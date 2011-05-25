package antonj.opengl.widgets.button;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import antonj.opengl.texture.TexDetails;
import antonj.opengl.texture.Texture;
import antonj.utils.Bitmaps;

import javax.microedition.khronos.opengles.GL10;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Anton
 * Date: 18.02.2010
 * Time: 23:10:28
 */
public class TexButtonFactory {

    private Map<String, ButtonTextures> configs =
            new HashMap<String, ButtonTextures>();
    private AssetManager assets;
    private Texture texture;

    public TexButtonFactory(AssetManager assets, Texture texture) {
        this.assets = assets;
        this.texture = texture;
    }

    public TexButton createSimple(GL10 gl, String resourceName,
            Viewport viewport, int width, int height) {
        return create(gl,
                SimpleButtonConfig.INSTANCE,
                resourceName, viewport, width, height);
    }

    public TexButton createToggle(GL10 gl, String resourceName,
            Viewport viewport, int width, int height) {
        return create(gl,
                ToggleButtonConfig.INSTANCE,
                resourceName, viewport, width, height);
    }

    public TexButton create(GL10 gl,
            ButtonConfig config,
            String resourceName,
            Viewport viewport, int width, int height) {
        return new TexButton(loadResource(gl, resourceName, config), config,
                width, height, viewport);
    }

    private ButtonTextures loadResource(GL10 gl,
            String resourceName,
            ButtonConfig config) {
        ButtonTextures textures = configs.get(resourceName);

        if (textures == null) {
            textures = loadButtonResources(gl, resourceName, config);
            configs.put(resourceName, textures);
        }
        return textures;
    }

    private ButtonTextures loadButtonResources(GL10 gl,
            String resourceName, ButtonConfig config) {
        String[] stateResources = config.getStateResourceNames();
        TexDetails[] details = new TexDetails[stateResources.length];

        for (int i = 0; i < stateResources.length; i++) {
            String stateName = stateResources[i];
            Bitmap bitmap = Bitmaps.loadBitmap(
                    assets, buttonFile(resourceName, stateName));
            details[i] = texture.addBitmap(gl, bitmap);
            bitmap.recycle();
        }

        return new ButtonTextures(texture, details);
    }

    private static String buttonFile(String resourceName, String state) {
        return "buttons/" + resourceName + "/" + state + ".png";
    }
}
