package org.ydle.fragment;

import org.ydle.R;
import org.ydle.activity.IntentConstantes;
import org.ydle.adapter.SensorListAdapter;
import org.ydle.model.Room;

import roboguice.fragment.RoboFragment;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RoomDetailFragment extends RoboFragment {


	private static final String TAG = "Ydle.RoomDetailFragment";

	private Room item;

	TextView room_detail;

	public RoomDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(IntentConstantes.ITEM)) {
			item = getArguments().getParcelable(IntentConstantes.ITEM);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_room_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (item != null) {

			TextView room_detail = ((TextView) rootView
					.findViewById(R.id.room_detail));
			room_detail.setText(item.description);

			TextView capteur_title = ((TextView) rootView
					.findViewById(R.id.capteur_title));
			capteur_title.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

			ImageView type = (ImageView) rootView.findViewById(R.id.type);

			type.setImageResource(item.typeIcon.getDrawable());

			ListView capteurs = ((ListView) rootView
					.findViewById(R.id.capteurs));

			Log.d(TAG, "capteurs : " + item.sensor.size());
			capteurs.setAdapter(new SensorListAdapter(this.getActivity(),
					item.sensor, item));

			getActivity().setTitle(item.name);
		}

		return rootView;
	}
}
