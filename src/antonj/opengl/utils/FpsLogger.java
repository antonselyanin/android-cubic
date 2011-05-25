package antonj.opengl.utils;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

/**
 * User: Anton
 * Date: 14.11.2009
 * Time: 20:18:42
 */
public class FpsLogger {
    private static final String TAG = FpsLogger.class.getSimpleName();
    private static final int AVG_FRAME_COUNT = 10;

    private StringBuilder infoBuilder;
    private long renderTime;
    private int frameCount;

    private long startTime;

    public FpsLogger() {
        this.infoBuilder = new StringBuilder();
        frameCount = 0;
        renderTime = 0;
        startTime = 0;
    }

    public void startFrame() {
        this.startTime = SystemClock.uptimeMillis();
    }

    public void endFrame() {
        renderTime += (SystemClock.uptimeMillis() - startTime);
        if (++frameCount == AVG_FRAME_COUNT) {
            int avgTime = (int) (renderTime / AVG_FRAME_COUNT);
            frameCount = 0;
            renderTime = 0;

            infoBuilder.delete(0, infoBuilder.length());
            infoBuilder.append(avgTime).append("ms/frame");
            Log.d(TAG, infoBuilder.toString());
        }
    }
}
