package org.ydle.remote.tasks;

import org.ydle.model.Room;
import org.ydle.remote.YdleService;

import android.app.Activity;

public class RoomDetailsAsynkTask extends AbstractTask<String, Room> {

	private YdleService service;

	public Room items;

	public String roomName = "";

	public RoomDetailsAsynkTask(Activity context, YdleService service) {
		super(context);
		this.service = service;
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
