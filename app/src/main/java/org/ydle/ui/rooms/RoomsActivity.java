package org.ydle.ui.rooms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import org.ydle.R;
import org.ydle.data.memoryprovider.MemoryProvider;
import org.ydle.data.model.Room;
import org.ydle.ui.YdleFragmentActivity;
import org.ydle.ui.adapter.RoomsAdapter;

import java.util.List;
import java.util.Vector;

public class RoomsActivity extends YdleFragmentActivity {

    private static final String LOG_TAG = RoomsActivity.class.getSimpleName();

    private List mFragments;
    private ViewPager mPager;
    private PagerSlidingTabStrip mTabs;
    public static final String NEWEST_ROOM_KEY = "NEWEST_ROOM_KEY";
    public static final String POSITION_ROOM_KEY = "POSITION_ROOM_KEY";
    private boolean mNewestRoom = false;
    private int mPositionRoom = 0;

    public static void displayRoomsActivity(final Activity from, boolean newestRoom) {
        final Intent i = new Intent(from, RoomsActivity.class);
        i.putExtra(NEWEST_ROOM_KEY, newestRoom);
        from.startActivity(i);
    }

    public static void displayRoomsActivity(final Activity from, int position) {
        final Intent i = new Intent(from, RoomsActivity.class);
        i.putExtra(POSITION_ROOM_KEY, position);
        from.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rooms_activity);

        mNewestRoom = getIntent().getBooleanExtra(NEWEST_ROOM_KEY, false);
        mPositionRoom = getIntent().getIntExtra(POSITION_ROOM_KEY, 0);

        // Initialize the ViewPager and set an adapter
        mPager = (ViewPager) findViewById(R.id.rooms_viewpager);
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.rooms_tabs);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.bindViews();

        if (mNewestRoom) {
            this.disaplayNewestRoom();
        } else {
            mPager.setCurrentItem(mPositionRoom);
        }
    }

    private void bindViews() {
        List<Room> rooms = MemoryProvider.getInstance().getRooms();
        mFragments = new Vector();

        if (rooms != null){
            for (Room room : rooms) {
                mFragments.add(RoomFragment.newInstance(room));
            }
        }
        mPager.setAdapter(new RoomsAdapter(getSupportFragmentManager(), mFragments));

        // Bind the tabs to the ViewPager
        mTabs.setViewPager(mPager);
    }

    public void disaplayNewestRoom() {
        mPager.setCurrentItem(mFragments.size() - 2);
    }

    public int getCurrentPosition() { return mPager.getCurrentItem(); }
}
