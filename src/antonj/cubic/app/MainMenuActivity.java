package antonj.cubic.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import antonj.cubic.R;
import antonj.samples.buttons.ButtonSampleActivity;

/**
 * User: Anton
 * Date: 06.01.2010
 * Time: 17:19:14
 */
public class MainMenuActivity extends Activity implements View.OnClickListener {
    private CheckBox soundsCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_screen);

        findViewById(R.id.start_button).setOnClickListener(this);
        findViewById(R.id.continue_button).setOnClickListener(this);
        findViewById(R.id.delete_data_button).setOnClickListener(this);
        findViewById(R.id.samples_button).setOnClickListener(this);
        soundsCheckBox = (CheckBox) findViewById(R.id.sounds_checkbox);
        soundsCheckBox.setOnClickListener(this);
        setSoundsText();
    }

    private void setSoundsText() {
        if (soundsCheckBox.isChecked()) {
            soundsCheckBox.setText("Sounds ON");
        } else {
            soundsCheckBox.setText("Sounds OFF");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button: {
                Intent intent = new Intent();
                intent.setClass(this, StartGameActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.continue_button: {
                Intent intent = new Intent();
                intent.setClass(this, ContinueGameActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.delete_data_button: {
                deleteFiles();
            }
            break;
            case R.id.samples_button: {
                Intent intent = new Intent();
                intent.setClass(this, ButtonSampleActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.sounds_checkbox: {
                setSoundsText();
            }
            break;
        }
    }

    private void deleteFiles() {
        for (String fileName : fileList()) {
            deleteFile(fileName);
        }
    }
}
