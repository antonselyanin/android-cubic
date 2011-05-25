package antonj.cubic.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import antonj.cubic.CubicContext;
import antonj.cubic.R;
import antonj.cubic.records.BestPlayerManager;
import antonj.cubic.saves.SavesManager;

/**
 * User: Anton
 * Date: 21.03.2010
 * Time: 13:14:38
 */
public class ContinueGameActivity extends Activity
        implements AdapterView.OnItemClickListener{
    private static final int MENU_REMOVE_RECORDS = 0;

    private BestPlayerManager bestPlayers;

    private ContinueGameListAdapter gameListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bestPlayers = CubicContext.getInstance().getBestPlayers();
        SavesManager saves = CubicContext.getInstance().getSaves();

        setContentView(R.layout.header_and_list_layout);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Continue");

        ListView gamesList = (ListView) findViewById(R.id.games_list);

        gameListAdapter = new ContinueGameListAdapter(this, bestPlayers, saves);
        gameListAdapter.notifyDataSetInvalidated();
        gamesList.setAdapter(gameListAdapter);
        gamesList.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_REMOVE_RECORDS, 0, "Remove time records");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_REMOVE_RECORDS) {
            bestPlayers.removeAll();
            gameListAdapter.invalidateItems();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        GameConfig config = gameListAdapter.getItem(position);

        if (!gameListAdapter.isEnabled(position)) {
            return;
        }

        Intent intent = new Intent(this, CubicActivity.class);
        intent.putExtra(CubicActivity.CONTINUE, true);
        intent.putExtra(CubicActivity.SIZE, config.cubeSize);
        startActivity(intent);
        finish();
    }
}