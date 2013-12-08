package org.ydle.remote;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.ydle.model.Room;
import org.ydle.model.Sensor;
import org.ydle.model.TypeRoomIcon;

public class JsonConverter {

	public Room convertRoom(JSONObject item) {
		Room room = null;
		if (item != null) {
			room = new Room();
			room.description = getFacultatifString(item, "description");
			room.name = getFacultatifString(item, "name");
			room.id = getFacultatifString(item, "id");
			room.active = getFacultatifBoolean(item, "active");
			room.typeIcon = TypeRoomIcon.BATHROOM;
			if (item.optJSONArray("capteurs") != null) {
				room.sensor = new ArrayList<Sensor>();
			} else {
				room.setSensorSize(getFacultatifInt(item, "capteurs"));
			}
		}

		return room;
	}

	private boolean getFacultatifBoolean(JSONObject item, String name) {
		try {
			return item.getBoolean(name);
		} catch (JSONException e) {
			return false;
		}
	}

	private String getFacultatifString(JSONObject item, String name) {
		try {
			String value = item.getString(name);
			if (value.equals("null")) {
				return "";
			}
			return value;
		} catch (JSONException e) {
			return "";
		}
	}

	private int getFacultatifInt(JSONObject item, String name) {
		try {
			return item.getInt(name);
		} catch (JSONException e) {
			return 0;
		}
	}

}
