package org.ydle.activity;

import org.ydle.R;
import org.ydle.adapter.SensorListAdapter;
import org.ydle.model.Room;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RoomPagedActivity extends PagedActivity<Room> {

	protected static final String TAG = "Ydle.RoomPagedActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate : "+index);
	}
	
	@Override
	protected Class<? extends Activity> getActivityClass() {
		return RoomPagedActivity.class;
	}

	public void initView() {
		final ViewPager pagedView = (ViewPager) findViewById(R.id.pager);

		PagerAdapter adapter = new PagerAdapter() {

			@Override
			public int getCount() {
				return RoomPagedActivity.this.getCount();
			}

			public Object instantiateItem(View collection, int position) {
				LayoutInflater inflater = (LayoutInflater) collection
						.getContext().getSystemService(
								Context.LAYOUT_INFLATER_SERVICE);
				Log.d(TAG, "instantiateItem : " + position);
				View view = inflater.inflate(R.layout.fragment_room_detail,
						null);
				item = (Room) getItem(position);
				Log.d(TAG, "item : " + item);
				if (item != null)
					initView(view, item);
				((ViewPager) collection).addView(view, 0);
				return view;
			}

			@Override
			public void destroyItem(View arg0, int arg1, Object arg2) {
				((ViewPager) arg0).removeView((View) arg2);
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == ((View) arg1);
			}

		};
		pagedView.setAdapter(adapter);
		pagedView.setCurrentItem(index);

	}

	private void initView(View convertView, Room room) {
		item = room;
		if (item != null) {
			Log.d(TAG, "description : " + item.description);
			TextView room_detail = ((TextView) convertView
					.findViewById(R.id.room_detail));
			room_detail.setText(item.description);

			TextView capteur_title = ((TextView) convertView
					.findViewById(R.id.capteur_title));
			capteur_title.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
			
			TextView scenario_title = ((TextView) convertView
					.findViewById(R.id.scenario_title));
			scenario_title.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
			
			ImageView type = (ImageView) convertView.findViewById(R.id.type);

			type.setImageResource(room.typeIcon.getDrawable());
			
			if(item.sensor.size() > 0){
				convertView.findViewById(R.id.capteurs_empty).setVisibility(View.GONE);
			}

			ListView capteurs = ((ListView) convertView
					.findViewById(R.id.capteurs));

			Log.d(TAG, "capteurs : " + item.sensor.size());
			capteurs.setAdapter(new SensorListAdapter(this,item.sensor,item,prefs));
			

			// this.setTitle(item.name);
		}

	}

}
