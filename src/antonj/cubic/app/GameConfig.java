package antonj.cubic.app;

import antonj.cubic.R;

/**
* User: Anton
* Date: 21.03.2010
* Time: 15:14:50
*/
//TODO: rename? There is already "GameInfo" class
public class GameConfig {
    public static GameConfig[] CONFIGS = {
            new GameConfig(R.drawable.pocket_cube, 2, "Pocket cube", "Even your kid can solve this"),
            new GameConfig(R.drawable.classic_cube, 3, "Classic cube", "For those who likes classic"),
            new GameConfig(R.drawable.revenge_cube, 4, "Revenge cube", "Revenge! The sweet feeling..."),
            //TODO: find icon for professor
            new GameConfig(R.drawable.icon, 5, "Professor's cube", "Are you a Ph.D.?"),
    };
    
    public final int iconId;
    public final int cubeSize;
    public final String title;
    public final String description;

    private GameConfig(int iconId, int cubeSize, String title, String description) {
        this.iconId = iconId;
        this.cubeSize = cubeSize;
        this.title = title;
        this.description = description;
    }
}
