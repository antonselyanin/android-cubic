package antonj.opengl.utils;

import android.os.SystemClock;

/**
 * User: Anton
 * Date: 14.02.2010
 * Time: 15:41:18
 */
public class FpsCounter {
    private float mpf;
    private float fps;
    private long lastTime;
    private long totalTime;

    private int counter;

    public void init() {
        lastTime = SystemClock.uptimeMillis();
    }

    public void onFrame() {
        long currentTime = SystemClock.uptimeMillis();
        totalTime += (currentTime - lastTime);
        lastTime = currentTime;
        counter++;

        if (counter == 10) {
            mpf = totalTime / counter;
            if (mpf != 0) {
                fps = 1000 / mpf;
            }

            totalTime = 0;
            counter = 0;
        }
    }

    public int getMpf() {
        return (int) mpf;
    }

    public int getFps() {
        return (int) fps;
    }
}
