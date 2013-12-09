package org.ydle.adapter;

import java.util.List;

import org.ydle.R;
import org.ydle.activity.IntentConstantes;
import org.ydle.activity.historique.GraphHistoryActivity;
import org.ydle.model.Room;
import org.ydle.model.Sensor;
import org.ydle.model.SensorType;
import org.ydle.utils.ActivityUtils;

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

	Context activitiy;
	private Room room;
	private SharedPreferences prefs;

	public SensorListAdapter(Context context, List<Sensor> objects, Room room,
			SharedPreferences prefs) {
		super(context, 0, objects);
		this.activitiy = context;
		this.room = room;
		this.prefs = prefs;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final Sensor node = getItem(position);

		if (view == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.sensor_item, null);
		}

		TextView name = (TextView) view.findViewById(R.id.name);
		name.setText(node.name);

		TextView valeur = (TextView) view.findViewById(R.id.valeur);
		valeur.setText(node.currentValeur.valeur + " " + node.unit);

		ImageView type = (ImageView) view.findViewById(R.id.type);
		SensorType sensorType = SensorType.fromCode(node.type);

		type.setImageResource(sensorType.getDrawable());

		if (sensorType.equals(SensorType.TEMP)) {

			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(activitiy,
							GraphHistoryActivity.class);
					intent.putExtra(IntentConstantes.ITEM, (Parcelable) node);
					intent.putExtra(IntentConstantes.ITEM_ROOM,
							(Parcelable) room);
					activitiy.startActivity(intent);

				}
			});
		} else if (sensorType.equals(SensorType.LUM)) {

		} else if (sensorType.equals(SensorType.HYDRO)) {

			if (ActivityUtils.getConf(prefs).graph) {
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(activitiy,
								GraphHistoryActivity.class);
						intent.putExtra(IntentConstantes.ITEM,
								(Parcelable) node);
						intent.putExtra(IntentConstantes.ITEM_ROOM,
								(Parcelable) room);
						activitiy.startActivity(intent);

					}
				});
			}
		}

		return view;
	}

}
