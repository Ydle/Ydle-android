package org.ydle.adapter;

import java.util.List;

import org.ydle.R;
import org.ydle.model.Room;

import roboguice.RoboGuice;

import com.google.inject.Inject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RoomListAdapter extends ArrayAdapter<Room> {

	@Inject
	LayoutInflater layoutInflater;
	
	public RoomListAdapter(Context context, List<Room> objects) {
		super(context, 0, objects);
		RoboGuice.getInjector(context).injectMembers(this);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final Room room = getItem(position);

		if (view == null) {
			view = layoutInflater.inflate(R.layout.item, null);
		}

		TextView name = (TextView) view.findViewById(R.id.name);
		name.setText(room.name);

		TextView desc = (TextView) view.findViewById(R.id.desc);
		desc.setText(room.getSensorSize() + " Capteur(s)");

		ImageView type = (ImageView) view.findViewById(R.id.type);

		if (room.typeIcon != null) {
			type.setImageResource(room.typeIcon.getDrawable());
		}

		if (!room.active) {
			view.setActivated(false);
			view.setAlpha(0.3f);
		}

		return view;
	}

}
