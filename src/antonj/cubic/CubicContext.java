package antonj.cubic;

import android.content.Context;
import antonj.cubic.records.BestPlayerManager;
import antonj.cubic.saves.SavesManager;

/**
 * User: Anton
 * Date: 02.10.2010
 * Time: 21:55:44
 */
public class CubicContext {
    private static final CubicContext instance = new CubicContext();

    private BestPlayerManager bestPlayers;
    private SavesManager saves;

    private CubicContext() {
    }

    public void init(Context context) {
        bestPlayers = new BestPlayerManager(context);
        saves = new SavesManager(context);
    }

    public static CubicContext getInstance() {
        return instance;
    }

    public BestPlayerManager getBestPlayers() {
        return bestPlayers;
    }

    public SavesManager getSaves() {
        return saves;
    }
}
