package org.ydle.activity;

import org.ydle.IntentConstantes;
import org.ydle.R;
import org.ydle.activity.common.BaseFragmentActivity;
import org.ydle.fragment.RoomDetailFragment;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class RoomSlidePagerActivity extends BaseFragmentActivity {

	private static final String TAG = "Ydle.RoomSlidePagerActivity";

	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */
	@InjectView(R.id.pager)
	ViewPager mPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;

	@InjectExtra(IntentConstantes.ITEMS_SIZE)
	protected int itemSize;

	@InjectExtra(IntentConstantes.INDEX)
	protected int index;

	@InjectExtra(IntentConstantes.ITEMS)
	protected String[] items;
	
	RoomDetailFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.room_slide);

		// Instantiate a ViewPager and a PagerAdapter.
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(index);
		
		Log.d(TAG, "current index : " + index);
		Log.d(TAG, "current item : " + mPager.getCurrentItem());
		
	}


	/**
	 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects,
	 * in sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Log.d(TAG, "getItem : " + position);
			Log.d(TAG, "getItem index : " + index);
			Log.d(TAG, "getItem size: " + itemSize);
			fragment = new RoomDetailFragment();
			fragment.initItem(items[position]);
			return fragment;
		}

		@Override
		public int getCount() {
			return itemSize;
		}
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_refresh:
			fragment.refreshData();
			break;

		default:
			break;
		}

		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.removeItem(R.id.action_add);
		return result;
	}
}
