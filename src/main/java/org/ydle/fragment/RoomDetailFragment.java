package org.ydle.fragment;

import org.ydle.IntentConstantes;
import org.ydle.R;
import org.ydle.activity.common.BaseFragment;
import org.ydle.adapter.SensorListAdapter;
import org.ydle.model.Room;
import org.ydle.remote.YdleService;
import org.ydle.remote.tasks.RoomDetailsAsynkTask;

import roboguice.inject.InjectView;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.inject.Inject;

public class RoomDetailFragment extends BaseFragment {

	private static final String TAG = "Ydle.RoomDetailFragment";

	@Inject
	private YdleService service;

	private Room item;

	private String itemId;

	@InjectView(R.id.room_detail)
	TextView room_detail;
	@InjectView(R.id.type)
	ImageView type;
	@InjectView(R.id.scenario_title)
	TextView scenario_title;
	@InjectView(R.id.scenarios_empty)
	TextView scenarios_empty;
	@InjectView(R.id.scenarios)
	ListView scenarios;
	@InjectView(R.id.capteur_title)
	TextView capteur_title;
	@InjectView(R.id.capteurs_empty)
	TextView capteurs_empty;
	@InjectView(R.id.capteurs)
	ListView capteurs;

	@Inject
	protected SharedPreferences prefs;

	public void initItem(String items) {
		itemId = items;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null
				&& getArguments().containsKey(IntentConstantes.ITEM)) {
			itemId = getArguments().getString(IntentConstantes.ITEM);
			refreshData();
		}

	}

	public void refreshData() {
		RoomDetailsAsynkTask task = new RoomDetailsAsynkTask(this.getActivity()) {
			@Override
			protected void onPostExecute(Room result) {
				super.onPostExecute(result);
				item = result;
				initView();
			}
		};

		task.execute(itemId, item == null ? "" : item.name);
	}

	private void initView() {

		if (item != null) {

			if (item.description != null && !item.description.isEmpty()) {
				room_detail.setText(item.description);
			} else {
				room_detail.setText(item.name);
			}

			capteur_title.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

			if (getConf().avance) {
				scenario_title.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
				scenario_title.setVisibility(View.VISIBLE);
				scenarios_empty.setVisibility(View.VISIBLE);
			} else {
				scenario_title.setVisibility(View.GONE);
				scenarios_empty.setVisibility(View.GONE);
			}

			type.setImageResource(item.typeIcon.getDrawable());

			if (item.sensor.isEmpty()) {
				capteurs_empty.setVisibility(View.VISIBLE);
			}else{
				capteurs_empty.setVisibility(View.GONE);
			}

			Log.d(TAG, "capteurs : " + item.sensor.size());
			capteurs.setAdapter(new SensorListAdapter(this.getActivity(),
					item.sensor, item));

			// getActivity().setTitle(item.name);

			if (!item.active) {
				getView().setActivated(false);
				getView().setAlpha(0.3f);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.layout_room_detail,
				container, false);

		refreshData();

		return rootView;
	}
}
