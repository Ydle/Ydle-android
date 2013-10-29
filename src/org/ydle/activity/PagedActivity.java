package org.ydle.activity;

import java.io.Serializable;
import java.util.ArrayList;

import org.ydle.R;

import roboguice.inject.InjectExtra;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

public abstract class PagedActivity<T extends Serializable> extends
		BaseActivity {

	protected static final String TAG = "Ydle.PagedActivity";
	@InjectExtra(IntentConstantes.ITEM)
	protected T item;
	@InjectExtra(IntentConstantes.INDEX)
	protected int index;
	@InjectExtra(value = IntentConstantes.ITEMS, optional = true)
	protected ArrayList<T> items;

	protected abstract Class<? extends Activity> getActivityClass();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.room_slide);

		final ViewPager pagedView = (ViewPager) findViewById(R.id.pager);
		pagedView.setOnPageChangeListener(mOnPagedViewChangedListener);

		initView();
	}
	
	public int getCount(){
		return items.size();
	}

	public T getItem(int index) {
		if(index <0) index=0;
		if(index > items.size())index = items.size()-1;
		return items.get(index);

	}

	public abstract void initView();

	private ViewPager.OnPageChangeListener mOnPagedViewChangedListener = new ViewPager.OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			Log.d(TAG, "onPageScrollStateChanged : "+arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
		}

	};
}
