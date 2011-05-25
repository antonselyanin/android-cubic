package antonj.cubic.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import antonj.cubic.CubicContext;
import antonj.cubic.CubicView;
import antonj.cubic.MainLoop;
import antonj.cubic.input.InputObject;
import antonj.cubic.records.BestPlayer;
import antonj.cubic.records.BestPlayerManager;
import antonj.cubic.saves.GameInfo;
import antonj.cubic.saves.SavesManager;

public class CubicActivity extends Activity {
    private static final String TAG = CubicActivity.class.getSimpleName();

    public static final String CONTINUE = "CONTINUE";
    public static final String SIZE = "SIZE";

    public static final int FINISH_MESSAGE = 0;
    public static final int SHOW_PROGRESS_MESSAGE = 1;
    public static final int STOP_PROGRESS_MESSAGE = 2;

    private CubicView view;
    private PowerManager.WakeLock wl;
    private MainLoop loop;

    private SavesManager saves;
    private BestPlayerManager bestPlayers;
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        saves = CubicContext.getInstance().getSaves();
        bestPlayers = CubicContext.getInstance().getBestPlayers();

        Intent intent = getIntent();
        boolean continueGame = intent.getExtras().getBoolean(CONTINUE);
        int size = intent.getExtras().getInt(SIZE, 3);

        BestPlayer record = bestPlayers.listRecords().get(size);

        loop = new MainLoop(this, new MsgHandler(), record);

        if (continueGame) {
            Log.d(TAG, "loading from file");
            saves.load(loop, size);
        } else {
            loop.init(size);
        }

        view = new CubicView(this, loop);
        setContentView(view);

        //TODO: debug
//        Debug.startMethodTracing("cubic");
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        loop.input.feed(InputObject.TRACK_BALL, event);

        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.onPause();
        if (wl.isHeld()) {
            wl.release();
        }

        if (!loop.isSolved()) {
            saves.save(loop);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, TAG);
        wl.acquire();
    }

    @Override
    protected void onStop() {
        if (wl.isHeld()) {
            wl.release();
        }
        //TODO: debug
//        Debug.stopMethodTracing();
        super.onStop();
    }

    private void finishGame() {
        GameInfo gameInfo = loop.gameInfo;

        //TODO: read name somehow?
        bestPlayers.store(gameInfo, "Unknown hero");
        saves.remove(gameInfo.cubeSize);

        finish();
    }

    private class MsgHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FINISH_MESSAGE : {
                    finishGame();
                }
                break;
                case SHOW_PROGRESS_MESSAGE : {
                    progress = ProgressDialog.show(CubicActivity.this,
                            "", "Generating puzzle. Please wait...", false, false);
                }
                break;
                case STOP_PROGRESS_MESSAGE : {
                    progress.dismiss();
                }
                break;

                default:
                    super.handleMessage(msg);
            }
        }
    }
}
