package antonj.cubic;

import android.app.Application;

/**
 * User: Anton
 * Date: 03.10.2010
 * Time: 1:49:11
 */
public class CubicApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CubicContext.getInstance().init(getApplicationContext());
    }
}
