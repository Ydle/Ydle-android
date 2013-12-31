package org.ydle.remote.tasks;

import java.util.ArrayList;
import java.util.List;

import org.ydle.model.Room;
import org.ydle.remote.YdleService;

import com.google.inject.Inject;

import android.app.Activity;

public class RoomsAsynkTask extends AbstractTask<Void, List<Room>> {

	@Inject
	YdleService service;

	public ArrayList<Room> items = new ArrayList<Room>();

	public RoomsAsynkTask(Activity context) {
		super(context);
	}

	@Override
	protected List<Room> doInBackground(Void... params) {
		List<Room> result = service.getRooms();

		if (result != null) {
			items.addAll(result);
		}

		return items;
	}

}
