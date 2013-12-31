package org.ydle.adapter;

import java.util.List;

import org.ydle.R;
import org.ydle.activity.settings.HostListActivity;
import org.ydle.model.configuration.ServeurInfo;

import roboguice.RoboGuice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

public class HostListAdapter extends ArrayAdapter<ServeurInfo> {

	@Inject
	LayoutInflater layoutInflater;

	public HostListAdapter(Context context, List<ServeurInfo> objects) {
		super(context, 0, objects);
		RoboGuice.getInjector(context).injectMembers(this);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ServeurInfo host = getItem(position);

		if (view == null) {
			view = layoutInflater.inflate(R.layout.host_item, null);
		}

		TextView host_name = (TextView) view.findViewById(R.id.host_name);
		host_name.setText(host.nom);

		TextView host_ip = (TextView) view.findViewById(R.id.host_ip);
		host_ip.setText(host.host);

		ImageView img = (ImageView) view.findViewById(R.id.etat);

		if (!host.actif) {
			img.setImageResource(R.drawable.voyant_rouge);
		} else {
			img.setImageResource(R.drawable.voyant_vert);
		}
		
		
//		view.setOnLongClickListener(new OnLongClickListener() {
//
//			@Override
//			public boolean onLongClick(View v) {
//				((HostListActivity) getContext()).onActive(getItem(position));
//				return true;
//			}
//		});
		

		return view;
	}
}
