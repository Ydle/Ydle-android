package org.ydle.remote.tasks;

import org.ydle.model.Room;
import org.ydle.remote.YdleService;

import com.google.inject.Inject;

import android.app.Activity;

public class RoomDetailsAsynkTask extends AbstractTask<String, Room> {

	@Inject
	YdleService service;

	public Room items;

	public String roomName = "";

	public RoomDetailsAsynkTask(Activity context) {
		super(context);
	}

	@Override
	protected Room doInBackground(String... params) {
		items = service.getRoomDetails(params[0]);
		roomName = params[1];
		return items;
	}

	public String getDialogTitle() {
		String result = super.getDialogTitle();

		if (roomName != null && !roomName.isEmpty())
			result += " '" + roomName + "'";

		return result;
	}

}
