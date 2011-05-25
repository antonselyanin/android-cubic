package antonj.cubic.app;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import antonj.cubic.R;
import antonj.cubic.records.BestPlayer;
import antonj.cubic.records.BestPlayerManager;
import antonj.cubic.saves.GameInfo;
import antonj.cubic.saves.SavesManager;
import antonj.utils.Dates;

/**
 * User: Anton
 * Date: 11.03.2010
 * Time: 20:50:08
 */
public class ContinueGameListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Item[] items = new Item[GameConfig.CONFIGS.length];

    //TODO: move strings to resources
    private static final String CURRENT_TIME_PREFIX = "Current time: ";
    private static final String START_TIME_PREFIX = "Start time: ";
    private static final String BEST_TIME_PREFIX = "Best time: ";

    private BestPlayerManager bestPlayers;
    private SavesManager saves;

    public ContinueGameListAdapter(Context context,
            BestPlayerManager bestPlayers,
            SavesManager saves) {
        inflater = LayoutInflater.from(context);
        this.bestPlayers = bestPlayers;
        this.saves = saves;
        loadItems();
    }

    public void invalidateItems() {
        loadItems();
        notifyDataSetInvalidated();
    }

    private void loadItems() {
        SparseArray<GameInfo> savedGames = saves.listSavedGames();
        SparseArray<BestPlayer> records = bestPlayers.listRecords();

        for (int i = 0; i < items.length; i++) {
            Item item = new Item();

            GameConfig config = GameConfig.CONFIGS[i];
            item.config = config;
            GameInfo gameInfo = savedGames.get(config.cubeSize);

            if (gameInfo != null) {
                item.enabled = true;
                item.spendTime = CURRENT_TIME_PREFIX + Dates.timeToString(gameInfo.spendTime);
                item.startTime = START_TIME_PREFIX + Dates.timeToString(gameInfo.startTime);
            } else {
                item.enabled = false;
                item.spendTime = CURRENT_TIME_PREFIX + "<no records>";
                item.startTime = START_TIME_PREFIX + "<no records>";
            }

            BestPlayer record = records.get(config.cubeSize);

            if (record != null) {
                item.bestTime = BEST_TIME_PREFIX + Dates.timeToString(record.spendTime);
            } else {
                item.bestTime = BEST_TIME_PREFIX + "<no records>";
            }

            items[i] = item;
        }
    }

    @Override
    public int getCount() {
        return GameConfig.CONFIGS.length;
    }

    @Override
    public GameConfig getItem(int position) {
        return items[position].config;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean isEnabled(int position) {
        return items[position].enabled; 
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.continue_game_item_layout, null);

            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.spendTime = (TextView) convertView.findViewById(R.id.current_time);
            holder.bestTime = (TextView) convertView.findViewById(R.id.best_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = items[position];

        holder.icon.setImageResource(item.config.iconId);
        holder.text.setText(item.config.title);
        holder.spendTime.setText(item.spendTime);
        holder.bestTime.setText(item.bestTime);

        if (!item.enabled) {
            holder.icon.setAlpha(96);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView text;
        TextView spendTime;
        TextView bestTime;
    }

    static class Item {
        public GameConfig config;
        public boolean enabled;
        public String spendTime;
        public String startTime;
        public String bestTime;
    }
}