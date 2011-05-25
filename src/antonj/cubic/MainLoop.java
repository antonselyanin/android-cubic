package antonj.cubic;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import antonj.cubic.app.CubicActivity;
import antonj.cubic.components.*;
import antonj.cubic.input.InputProcessor;
import antonj.cubic.model.Cube;
import antonj.cubic.model.CubeConfig;
import antonj.cubic.records.BestPlayer;
import antonj.cubic.saves.GameInfo;
import antonj.opengl.Projector;
import antonj.opengl.TouchRay;
import antonj.opengl.text.Font;
import antonj.opengl.texture.Texture;
import antonj.opengl.widgets.button.TexButtonFactory;
import antonj.opengl.widgets.button.Viewport;
import org.json.JSONException;
import org.json.JSONObject;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: Anton
 * Date: 17.12.2009
 * Time: 22:56:24
 */
public class MainLoop {
    private static final String VERSION_KEY = "VERSION";
    private static final int VERSION = 1;

    public Cube cube = new Cube();
    public Projector projector = new Projector();
    public Camera camera = new Camera(projector);

    public TouchRay touch = new TouchRay();

    public InputProcessor input = new InputProcessor();

    public Component[] components;

    public int width;
    public int height;

    //TODO: extract to class?
    public Font font;
    public Font bigFont;

    public AssetManager assets;
    public Handler handler;
    public TexButtonFactory buttons;
    public Viewport viewport;
    public Texture texture;

    public static final int STATE_PLAYING = 0;
    public static final int STATE_SOLVED = 1;

    public int state;

    public GameInfo gameInfo;
    public BestPlayer record;

    private static final String GAME_INFO_KEY = "GAME_INFO";
    private static final String CUBE_SIZE_KEY = "CUBE_SIZE";
    private static final String SPEND_TIME_KEY = "SPEND_TIME";
    private static final String START_TIME_KEY = "START_TIME";

    public MainLoop(Activity activity, Handler handler, BestPlayer record) {
        this.assets = activity.getAssets();
        this.handler = handler;
        this.record = record;
        state = STATE_PLAYING;

        components = new Component[]{
                new FpsComponent(),
                new TimeComponent(this),
                new HistoryComponent(this),
                new CubeComponent(this),
                new CubeRotationComponent(),
                new FinishScreenComponent(this),
//            new RandomRotation(),
                new TestComponent(this),
        };
        input.setComponents(components);
    }

    public void init(int size) {
        this.gameInfo = new GameInfo(size);

        CubeConfig config = new CubeConfig(gameInfo.cubeSize);
        for (Component component : components) {
            component.init(config);
        }
    }

    public void load(JSONObject storedState) throws JSONException {
        if (storedState.getInt(VERSION_KEY) != VERSION) {
            throw new RuntimeException("Unknown save file");
        }

        gameInfo = loadGameInfo(storedState);

        for (Component component : components) {
            component.load(storedState);
        }
    }

    public JSONObject save() throws JSONException {
        JSONObject save = new JSONObject()
                .put(VERSION_KEY, VERSION);

        saveGameInfo(save);

        for (Component component : components) {
            component.save(save);
        }

        return save;
    }

    public void updateState(long frameDelta) {
        input.process(this);

        for (Component component : components) {
            component.update(frameDelta, this);
        }

        if (state == STATE_PLAYING && cube.solved) {
            state = STATE_SOLVED;
        }
    }

    public void initWindow(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;

        texture = Texture.create(gl, 512);
        buttons = new TexButtonFactory(assets, texture);

        viewport = new Viewport(width, height);

        Typeface typeface = Typeface.MONOSPACE;
        font = new Font(typeface, 18, true, texture);
        bigFont = new Font(typeface, 30, true, texture);

        for (Component component : components) {
            component.initWindow(gl, this);
        }
    }

    public void draw(GL10 gl) {
        for (Component component : components) {
            component.draw(gl);
        }
    }

    public void drawHud(GL10 gl) {
        for (Component component : components) {
            component.drawHud(gl);
        }
    }

    public void finishGame() {
        handler.sendMessage(
                handler.obtainMessage(CubicActivity.FINISH_MESSAGE));
    }

    private void saveGameInfo(JSONObject json)
            throws JSONException {
        JSONObject state = new JSONObject()
                .put(CUBE_SIZE_KEY, gameInfo.cubeSize)
                .put(SPEND_TIME_KEY, gameInfo.spendTime)
                .put(START_TIME_KEY, gameInfo.startTime);

        json.put(GAME_INFO_KEY, state);
    }

    public static GameInfo loadGameInfo(JSONObject json)
            throws JSONException {
        JSONObject state = json.getJSONObject(GAME_INFO_KEY);

        return new GameInfo(
                state.getInt(CUBE_SIZE_KEY),
                state.getInt(SPEND_TIME_KEY),
                state.getInt(START_TIME_KEY));
    }

    public boolean isSolved() {
        return state == STATE_SOLVED;
    }
}
