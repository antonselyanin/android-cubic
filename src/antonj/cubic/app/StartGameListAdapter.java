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
import antonj.utils.Dates;

/**
 * User: Anton
 * Date: 11.03.2010
 * Time: 20:50:08
 */
public class StartGameListAdapter extends BaseAdapter {
    private static final String BEST_TIME_PREFIX = "Best time: ";

    private LayoutInflater inflater;
    private Item[] items = new Item[GameConfig.CONFIGS.length];
    private BestPlayerManager bestPlayers;

    public StartGameListAdapter(Context context, BestPlayerManager bestPlayers) {
        this.bestPlayers = bestPlayers;
        inflater = LayoutInflater.from(context);
        loadItems();
    }

    private void loadItems() {
        SparseArray<BestPlayer> records = bestPlayers.listRecords();

        for (int i = 0; i < items.length; i++) {
            Item item = new Item();

            GameConfig config = GameConfig.CONFIGS[i];
            item.config = config;

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
    public GameConfig getItem(int i) {
        return items[i].config;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.start_game_item_layout, null);

            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.title = (TextView) convertView.findViewById(R.id.text);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.bestTime = (TextView) convertView.findViewById(R.id.best_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = items[position];

        holder.icon.setImageResource(item.config.iconId);
        holder.title.setText(item.config.title);
        holder.description.setText(item.config.description);
        holder.bestTime.setText(item.bestTime);

        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView title;
        TextView description;
        TextView bestTime;
    }

    private static class Item {
        public GameConfig config;
        public String bestTime;
    }
}
