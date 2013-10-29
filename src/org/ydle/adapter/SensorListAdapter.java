package org.ydle.adapter;

import java.util.List;

import org.ydle.R;
import org.ydle.activity.IntentConstantes;
import org.ydle.activity.historique.GraphHistoryActivity;
import org.ydle.model.Room;
import org.ydle.model.Sensor;
import org.ydle.model.SensorType;

import android.content.Context;
import android.content.Intent;
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

	public SensorListAdapter(Context context, List<Sensor> objects, Room room) {
		super(context, 0, objects);
		this.activitiy = context;
		this.room = room;
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
		

		if (node.type == SensorType.TEMP.getValeur()) {
			type.setImageResource(R.drawable.thermometer);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(activitiy, GraphHistoryActivity.class);
					intent.putExtra(IntentConstantes.ITEM,(Parcelable)node);
					intent.putExtra(IntentConstantes.ITEM_ROOM,(Parcelable)room);
					activitiy.startActivity(intent);

				}
			});
		} else if (node.type == SensorType.LUM.getValeur()) {
			type.setImageResource(R.drawable.light);
		} else if (node.type == SensorType.HYDRO.getValeur()) {
			type.setImageResource(R.drawable.goutte);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(activitiy, GraphHistoryActivity.class);
					intent.putExtra(IntentConstantes.ITEM,(Parcelable)node);
					intent.putExtra(IntentConstantes.ITEM_ROOM,(Parcelable)room);
					activitiy.startActivity(intent);

				}
			});
		} else {
			type.setImageResource(R.drawable.inconnue_rouge);
		}

		

		return view;
	}

	
}
