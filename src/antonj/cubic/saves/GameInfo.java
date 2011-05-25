package antonj.cubic.saves;

/**
 * User: Anton
 * Date: 14.03.2010
 * Time: 21:25:56
 */
//TODO: use/remove lastPlayedTime
public class GameInfo {
    public int cubeSize;
    public long spendTime;
    public long lastPlayedTime;
    public long startTime;

    public GameInfo(int cubeSize) {
        this.cubeSize = cubeSize;
    }

    public GameInfo(int cubeSize, long spendTime, long startTime) {
        this(cubeSize);
        this.spendTime = spendTime;
        this.startTime = startTime;
    }
}
