package org.ydle.remote.tasks;

import org.ydle.model.Room;
import org.ydle.remote.YdleService;

import android.app.Activity;

public class RoomDetailsAsynkTask extends AbstractTask<String,Room> {

	private YdleService service;

	public Room items;

	public RoomDetailsAsynkTask(Activity context,YdleService service) {
		super(context);
		this.service = service;
	}

	@Override
	protected Room doInBackground(String... params) {
		items = service.getRoomDetails(params[0]);
		return items;
	}

}

