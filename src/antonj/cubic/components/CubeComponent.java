package antonj.cubic.components;

import android.graphics.Bitmap;
import android.os.Handler;
import antonj.cubic.Camera;
import antonj.cubic.MainLoop;
import antonj.cubic.app.CubicActivity;
import antonj.cubic.model.Cube;
import antonj.cubic.model.CubeConfig;
import antonj.cubic.model.FaceConfig;
import antonj.opengl.Projector;
import antonj.utils.Bitmaps;
import antonj.utils.Math2;
import org.json.JSONException;
import org.json.JSONObject;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 02.03.2010
 * Time: 23:34:16
 */
public class CubeComponent extends Component {
    private static final String CAMERA_STATE_KEY = "CAMERA";
    private static final String CUBIC_CONFIG_KEY = "CUBIC_CONFIG";
    private static final String CUBIC_STATE_KEY = "CUBIC_STATE";

    private Cube cube;
    public Projector projector;
    public Camera camera;
    private boolean needRandomization = false;

    private float[] lightPosition = new float[4];
    private static final int RANDOM_ROTATIONS = 100;

    public CubeComponent(MainLoop loop) {
        this.cube = loop.cube;
        this.projector = loop.projector;
        this.camera = loop.camera;
    }

    @Override
    public void draw(GL10 gl) {
        camera.position(gl);
        //TODO: try to find a better position for the light
/*
        Arrays2.cross3(lightPosition, camera.eye, camera.up);
        Arrays2.normalize3(lightPosition);
        Arrays2.multiply(lightPosition, 1);
        Arrays2.add3(lightPosition, camera.eye);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosition, 0);
*/

        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, camera.eye, 0);
        cube.draw(gl);
    }

    @Override
    public void update(long frameDelta, MainLoop loop) {
        cube.updateState(frameDelta);
    }

    @Override
    public void init(CubeConfig config) {
        cube.init(config);

        camera.setAngles(
                cube.config.modelSize + 11,
                Math2.toRadians(45),
                Math2.toRadians(45));

        needRandomization = true;
    }

    private void randomizeCube() {
        for (int i = 0; i < RANDOM_ROTATIONS; i++) {
            int direction = 1 - 2 * Math2.RNG.nextInt(2);
            int axis = Math2.RNG.nextInt(cube.slices.length);
            int slice = Math2.RNG.nextInt(cube.config.size);
            cube.instantRotation(axis, slice, direction);
        }
    }

    @Override
    public void initWindow(GL10 gl, MainLoop loop) {
        Bitmap bitmap = Bitmaps.loadBitmap(loop.assets, "textures/face.png");
        FaceConfig.initFaceTexture(loop.texture.addBitmap(gl, bitmap));
        bitmap.recycle();

        cube.loadMesh();

        if (needRandomization) {
            Handler handler = loop.handler;
            handler.sendMessage(
                    handler.obtainMessage(CubicActivity.SHOW_PROGRESS_MESSAGE));
            randomizeCube();
            handler.sendMessage(
                    handler.obtainMessage(CubicActivity.STOP_PROGRESS_MESSAGE));
        }
    }

    @Override
    public void load(JSONObject storedState) throws JSONException {
        CubeConfig config =
                new CubeConfig(storedState.getJSONObject(CUBIC_CONFIG_KEY));

        cube.load(config, storedState.getJSONObject(CUBIC_STATE_KEY));
        camera.load(storedState.getJSONObject(CAMERA_STATE_KEY));
    }

    @Override
    public void save(JSONObject save) throws JSONException {
        save.put(CUBIC_CONFIG_KEY, cube.config.save())
            .put(CAMERA_STATE_KEY, camera.save())
            .put(CUBIC_STATE_KEY, cube.save());
    }
}
