package antonj.cubic.records;

import android.content.Context;
import android.util.SparseArray;
import antonj.cubic.app.GameConfig;
import antonj.cubic.saves.GameInfo;
import antonj.utils.json.JSON;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * User: Anton
 * Date: 22.03.2010
 * Time: 9:14:04
 */
public class BestPlayerManager {
    private static final String STORAGE_FILE = "bestplayers";

    private static final String CUBE_SIZE_KEY = "CUBE_SIZE";
    private static final String START_TIME_KEY = "START_TIME";
    private static final String PLAYER_NAME_KEY = "PLAYER_NAME";
    private static final String SPEND_TIME_KEY = "SPEND_TIME";
    private static final String ITEM_KEY_PREFIX = "ITEM_";

    private Context context;

    public BestPlayerManager(Context context) {
        this.context = context;
    }

    public SparseArray<BestPlayer> listRecords() {
        SparseArray<BestPlayer> result = new SparseArray<BestPlayer>();
        File file = new File(context.getFilesDir(), STORAGE_FILE);

        if (file.exists()) {
            load0(result);
        }

        return result;
    }

    private void load0(SparseArray<BestPlayer> result) {
        try {
            JSONObject json = JSON.load(context.openFileInput(STORAGE_FILE));
            for (GameConfig config : GameConfig.CONFIGS) {
                JSONObject item = json.optJSONObject(itemKey(config));
                if (item != null) {
                    result.put(config.cubeSize, load(item));
                }
            }
        } catch (Exception e) {
            //TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    private String itemKey(GameConfig config) {
        return ITEM_KEY_PREFIX + config.cubeSize;
    }

    public void store(GameInfo gameInfo, String name) {
        SparseArray<BestPlayer> list = listRecords();

        BestPlayer prevRecord = list.get(gameInfo.cubeSize);

        if (prevRecord != null && prevRecord.spendTime < gameInfo.spendTime) {
            return;
        }

        list.put(gameInfo.cubeSize, createRecord(gameInfo, name));
        save(list);
    }

    public void removeAll() {
        context.deleteFile(STORAGE_FILE);
    }

    private BestPlayer createRecord(GameInfo gameInfo, String name) {
        return new BestPlayer(gameInfo.cubeSize, gameInfo.startTime, gameInfo.spendTime, name);
    }

    public static JSONObject save(BestPlayer bestPlayer)
            throws JSONException {
        return new JSONObject().put(CUBE_SIZE_KEY, bestPlayer.cubeSize)
                .put(START_TIME_KEY, bestPlayer.startTime)
                .put(SPEND_TIME_KEY, bestPlayer.spendTime)
                .put(PLAYER_NAME_KEY, bestPlayer.playerName);
    }

    private BestPlayer load(JSONObject json) throws JSONException {
        return new BestPlayer(
                json.getInt(CUBE_SIZE_KEY),
                json.getLong(START_TIME_KEY),
                json.getLong(SPEND_TIME_KEY),
                json.getString(PLAYER_NAME_KEY));
    }

    private void save(SparseArray<BestPlayer> bestPlayers) {
        try {
            JSONObject json = new JSONObject();

            for (GameConfig config : GameConfig.CONFIGS) {
                BestPlayer data = bestPlayers.get(config.cubeSize);

                if (data != null) {
                    json.put(itemKey(config), save(data));
                }
            }

            JSON.save(
                    context.openFileOutput(STORAGE_FILE, Context.MODE_PRIVATE),
                    json);
        } catch (Exception e) {
            //TODO: handle exception
            throw new RuntimeException(e);
        }
    }
}
