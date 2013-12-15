package org.ydle.remote;

import java.util.ArrayList;
import java.util.List;

import org.ydle.model.Room;

import android.app.Activity;

public class RoomsAsynkTask extends AbstractTask<Void,List<Room>> {

	private YdleService service;

	public ArrayList<Room> items = new ArrayList<Room>();

	public RoomsAsynkTask(Activity context,YdleService service) {
		super(context);
		this.service = service;
	}

	@Override
	protected List<Room> doInBackground(Void... params) {
		items.addAll(service.getRooms());
		
		return items;
	}

}
