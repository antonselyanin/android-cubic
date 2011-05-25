package antonj.cubic.saves;

import android.content.Context;
import android.util.SparseArray;
import antonj.cubic.MainLoop;
import antonj.cubic.app.GameConfig;
import antonj.utils.json.JSON;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Anton
 * Date: 14.03.2010
 * Time: 21:25:06
 */
public class SavesManager {
    private static final String SAVE_FILE = "save";

    private Context context;

    public SavesManager(Context context) {
        this.context = context;
    }

    public SparseArray<GameInfo> listSavedGames() {
        SparseArray<GameInfo> saves = new SparseArray<GameInfo>(GameConfig.CONFIGS.length);

        Set<String> files = new HashSet<String>();
        files.addAll(Arrays.asList(context.fileList()));

        for (GameConfig gameConfig : GameConfig.CONFIGS) {
            if (files.contains(file(gameConfig.cubeSize))) {
                GameInfo gameInfo = loadGameInfo(gameConfig);
                saves.append(gameInfo.cubeSize, gameInfo);
            }
        }

        return saves;
    }

    private GameInfo loadGameInfo(GameConfig gameConfig)  {
        try {
            return MainLoop.loadGameInfo(load(gameConfig.cubeSize));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void load(MainLoop loop, int size) {
        try {
            loop.load(load(size));
        } catch (Exception e) {
            //TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    private JSONObject load(int size)
            throws IOException, JSONException {
        return JSON.load(context.openFileInput(file(size)));
    }

    private String file(int size) {
        return SAVE_FILE + size;
    }

    public void save(MainLoop loop) {
        try {
            JSON.save(context.openFileOutput(
                    file(loop.gameInfo.cubeSize), Context.MODE_PRIVATE),
                    loop.save());
        } catch (Exception e) {
            //TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    public void remove(int cubeSize) {
        if (!context.deleteFile(file(cubeSize))) {
            System.out.println("FAILED to remove save file!");
        } else {
            System.out.println("SUCCESSFULLY removed save file");
        }
    }
}
