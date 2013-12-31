package org.ydle.adapter;

import java.util.List;

import org.ydle.IntentConstantes;
import org.ydle.R;
import org.ydle.activity.historique.GraphHistoryActivity;
import org.ydle.model.Room;
import org.ydle.model.Sensor;
import org.ydle.model.SensorType;
import org.ydle.utils.PreferenceUtils;

import com.google.inject.Inject;

import roboguice.RoboGuice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SensorListAdapter extends ArrayAdapter<Sensor> {

	private Room room;
	
	@Inject
	SharedPreferences prefs;
	
	@Inject
	LayoutInflater layoutInflater;

	public SensorListAdapter(Context context, List<Sensor> objects, Room room) {
		super(context, 0, objects);
		this.room = room;
		RoboGuice.getInjector(context).injectMembers(this);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final Sensor node = getItem(position);

		if (view == null) {
			view = layoutInflater.inflate(R.layout.sensor_item, null);
		}

		TextView name = (TextView) view.findViewById(R.id.name);
		name.setText(node.name);

		TextView valeur = (TextView) view.findViewById(R.id.valeur);
		valeur.setText(node.currentValeur.valeur + " " + node.unit);

		ImageView type = (ImageView) view.findViewById(R.id.type);
		SensorType sensorType = SensorType.fromCode(node.type);

		type.setImageResource(sensorType.getDrawable());

		if (sensorType.equals(SensorType.TEMP)) {

			if (PreferenceUtils.getConf(prefs).graph) {
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getContext(),
								GraphHistoryActivity.class);
						intent.putExtra(IntentConstantes.ITEM,
								(Parcelable) node);
						intent.putExtra(IntentConstantes.ITEM_ROOM,
								(Parcelable) room);
						getContext().startActivity(intent);

					}
				});
			}
		} else if (sensorType.equals(SensorType.LUM)) {

		} else if (sensorType.equals(SensorType.HYDRO)) {

			if (PreferenceUtils.getConf(prefs).graph) {
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getContext(),
								GraphHistoryActivity.class);
						intent.putExtra(IntentConstantes.ITEM,
								(Parcelable) node);
						intent.putExtra(IntentConstantes.ITEM_ROOM,
								(Parcelable) room);
						getContext().startActivity(intent);

					}
				});
			}
		}

		return view;
	}

}
