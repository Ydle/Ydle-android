package org.ydle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.ydle.R;
import org.ydle.utils.DeviceInfoUtils;

import java.util.ArrayList;

/**
 * Created by Jean-Baptiste on 28/01/14.
 */
public class DashboardAdapter extends BaseAdapter {

    private static final String LOG_TAG = DashboardAdapter.class.getSimpleName();

    private Context mContext;
    private int mWidth;
    private ArrayList<Item> mItemList;

    public DashboardAdapter(Context c) {
        mContext = c;
        mWidth = DeviceInfoUtils.getWidthScreenPx(mContext) / 3;

        mItemList = new ArrayList<Item>();
        mItemList.add(new Item(ItemType.ROOMS, R.string.dashboard_item_rooms_title, R.drawable.icon_rooms));
//        mItemList.add(new Item(ItemType.EVENTS, R.string.dashboard_item_events_title, R.drawable.icon_events));
//        mItemList.add(new Item(ItemType.STATUS, R.string.dashboard_item_status_title, R.drawable.icon_status));
//        mItemList.add(new Item(ItemType.YANA, R.string.dashboard_item_yana_title, R.drawable.icon_sarah_yana));
//        mItemList.add(new Item(ItemType.SARAH, R.string.dashboard_item_sarah_title, R.drawable.icon_sarah_yana));
//        mItemList.add(new Item(ItemType.SCENARIOS, R.string.dashboard_item_scenarios_title, R.drawable.icon_scenarios));
//        mItemList.add(new Item(ItemType.LOG, R.string.dashboard_item_logs_title, R.drawable.icon_events));
    }

    public int getCount() {
        return mItemList.size();
    }

    public Item getItem(int position) {
        return mItemList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View item;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item = inflater.inflate(R.layout.dashboard_item, null);
            item.setLayoutParams(new GridView.LayoutParams(mWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            item.setPadding(8, 8, 8, 8);
        } else {
            item = convertView;
        }

        ImageView imageItem = (ImageView) item.findViewById(R.id.image_dashboard_item);
        TextView titleItem = (TextView) item.findViewById(R.id.title_dashboard_item);

        imageItem.setImageResource(mItemList.get(position).getIcon());
        titleItem.setText(mItemList.get(position).getTitle());
        return item;
    }

    public enum ItemType {
        ROOMS,
        EVENTS,
        STATUS,
        YANA,
        SARAH,
        SCENARIOS,
        LOG;
    }

    public class Item {

        private ItemType mType;
        private int mTitle;
        private int mIcon;

        public Item (ItemType type, int title, int icon) {
            this.mType = type;
            this.mTitle = title;
            this.mIcon = icon;
        }

        public ItemType getType() {
            return mType;
        }

        public int getIcon() {
            return mIcon;
        }

        public int getTitle() {
            return mTitle;
        }
    }

}
