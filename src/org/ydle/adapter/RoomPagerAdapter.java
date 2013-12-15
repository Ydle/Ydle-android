package org.ydle.adapter;

import java.util.ArrayList;

import org.ydle.R;
import org.ydle.fragment.RoomDetailFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class RoomPagerAdapter extends PagerAdapter {

	private static final String TAG = "Ydle.RoomPagerAdapter";
	private int size;
	ArrayList<Fragment> fragments = new ArrayList<Fragment>();

	public RoomPagerAdapter() {
		fragments.add(new RoomDetailFragment());
	}

	@Override
	public int getCount() {
		return size;
	}

	public Object instantiateItem(View collection, int position) {
		LayoutInflater inflater = (LayoutInflater) collection.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d(TAG, "instantiateItem : " + position);
		View currentView = inflater
				.inflate(R.layout.fragment_room_detail, null);
		// getItem(position);
		((ViewPager) collection).addView(currentView, 0);
		return currentView;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View) arg1);
	}

}
