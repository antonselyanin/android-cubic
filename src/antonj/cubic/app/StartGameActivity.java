package antonj.cubic.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import antonj.cubic.CubicContext;
import antonj.cubic.R;

/**
 * User: Anton
 * Date: 21.03.2010
 * Time: 13:14:38
 */
public class StartGameActivity extends Activity
        implements AdapterView.OnItemClickListener{
    private StartGameListAdapter gameListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.header_and_list_layout);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Start");

        ListView list = (ListView) findViewById(R.id.games_list);

        gameListAdapter = new StartGameListAdapter(this,
                CubicContext.getInstance().getBestPlayers());
        list.setAdapter(gameListAdapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        GameConfig config = gameListAdapter.getItem(position);

        Intent intent = new Intent();
        intent.setClass(this, CubicActivity.class);
        intent.putExtra(CubicActivity.CONTINUE, false);
        intent.putExtra(CubicActivity.SIZE, config.cubeSize);
        startActivity(intent);
        finish();
    }
}
