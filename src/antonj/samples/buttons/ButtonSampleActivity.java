package antonj.samples.buttons;

import android.app.Activity;
import android.os.Bundle;

/**
 * User: Anton
 * Date: 18.02.2010
 * Time: 23:02:18
 */
public class ButtonSampleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ButtonsView(this));
    }
}
