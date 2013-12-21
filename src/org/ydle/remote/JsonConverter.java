package org.ydle.remote;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ydle.model.Room;
import org.ydle.model.Sensor;
import org.ydle.model.SensorData;
import org.ydle.model.SensorType;
import org.ydle.model.TypeRoomIcon;

public class JsonConverter {

	public Room convertRoom(JSONObject item) throws JSONException {
		Room room = null;
		if (item != null) {
			room = new Room();
			room.description = getFacultatifString(item, "description");
			room.name = getFacultatifString(item, "name");
			room.id = getFacultatifString(item, "id");
			room.active = getFacultatifBoolean(item, "active");
			room.typeIcon = TypeRoomIcon.fromLabel(getFacultatifString(item, "type"));
			if (item.optJSONArray("capteurs") != null) {
				room.sensor = new ArrayList<Sensor>();
				JSONArray capteurs = item.getJSONArray("capteurs");
				for (int a = 0; a < capteurs.length(); a++) {
					JSONObject capteur = capteurs.getJSONObject(a);
					Sensor sensor = convertSensor(capteur);
					room.sensor.add(sensor);
				}

			} else {
				room.setSensorSize(getFacultatifInt(item, "capteurs"));
			}
		}

		return room;
	}

	private Sensor convertSensor(JSONObject item) {
		Sensor sensor = null;
		if (item != null) {
			sensor = new Sensor();
			sensor.name = getFacultatifString(item, "name");
			sensor.unit = getFacultatifString(item, "unit");
			sensor.type = SensorType.fromLabel(getFacultatifString(item, "name")).getValeur();
			sensor.active = getFacultatifBoolean(item, "active");
			sensor.description = getFacultatifString(item, "description");
			sensor.currentValeur = new SensorData(getFacultatifString(item,
					"current"), new Date());
			sensor.datas = new ArrayList<SensorData>();
			sensor.datas.add(sensor.currentValeur);
		}
		return sensor;
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
