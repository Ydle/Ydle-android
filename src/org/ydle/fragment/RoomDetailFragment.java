package org.ydle.fragment;

import org.ydle.IntentConstantes;
import org.ydle.R;
import org.ydle.adapter.SensorListAdapter;
import org.ydle.model.Room;
import org.ydle.remote.YdleService;
import org.ydle.remote.tasks.RoomDetailsAsynkTask;

import roboguice.fragment.RoboFragment;
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

public class RoomDetailFragment extends RoboFragment {

	private static final String TAG = "Ydle.RoomDetailFragment";

	@Inject
	private YdleService service;

	private Room item;

	private String itemId;

	TextView room_detail;

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
		RoomDetailsAsynkTask task = new RoomDetailsAsynkTask(
				this.getActivity(), service) {
			@Override
			protected void onPostExecute(Room result) {
				super.onPostExecute(result);
				item = result;
				initView();
			}
		};

		task.execute(itemId);
	}

	private void initView() {
		View rootView = getView();

		if (item != null) {

			TextView room_detail = ((TextView) rootView
					.findViewById(R.id.room_detail));
			room_detail.setText(item.description);

			TextView capteur_title = ((TextView) rootView
					.findViewById(R.id.capteur_title));
			capteur_title.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

			TextView scenario_title = ((TextView) rootView
					.findViewById(R.id.scenario_title));
			scenario_title.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

			ImageView type = (ImageView) rootView.findViewById(R.id.type);

			type.setImageResource(item.typeIcon.getDrawable());

			ListView capteurs = ((ListView) rootView
					.findViewById(R.id.capteurs));

			Log.d(TAG, "capteurs : " + item.sensor.size());
			capteurs.setAdapter(new SensorListAdapter(this.getActivity(),
					item.sensor, item, prefs));

			//getActivity().setTitle(item.name);
			
			if(!item.active){
				rootView.setActivated(false);
				rootView.setAlpha(0.3f);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_room_detail,
				container, false);

		refreshData();

		return rootView;
	}
}
