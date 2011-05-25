package antonj.cubic.components;

import android.view.MotionEvent;
import antonj.cubic.MainLoop;
import antonj.cubic.model.CubeConfig;
import org.json.JSONException;
import org.json.JSONObject;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 28.02.2010
 * Time: 16:46:30
 */
public abstract class Component {
    public void handleTouchEvent(MotionEvent event, MainLoop loop) {

    }

    public void handleTrackballEvent(MotionEvent event, MainLoop loop) {

    }

    public void update(long frameDelta, MainLoop loop) {

    }

    public void draw(GL10 gl) {

    }

    public void drawHud(GL10 gl) {

    }

    public void load(JSONObject json) throws JSONException {

    }

    public void save(JSONObject json) throws JSONException {

    }

    //TODO: rename!
    public void init(CubeConfig config) {

    }

    //TODO: rename!
    public void initWindow(GL10 gl, MainLoop loop) {
        
    }
}
