package antonj.cubic.records;

/**
 * User: Anton
 * Date: 22.03.2010
 * Time: 9:13:46
 */
public class BestPlayer {
    public final int cubeSize;
    public final long startTime;
    public final long spendTime;
    public final String playerName;

    public BestPlayer(int cubeSize, long startTime, long spendTime, String playerName) {
        this.cubeSize = cubeSize;
        this.startTime = startTime;
        this.spendTime = spendTime;
        this.playerName = playerName;
    }
}
