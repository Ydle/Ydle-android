package org.ydle.remote;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ydle.dummy.DummyYdleContent;
import org.ydle.model.Room;
import org.ydle.model.Sensor;
import org.ydle.model.SensorData;
import org.ydle.model.SensorType;
import org.ydle.model.TimeEchelle;
import org.ydle.model.configuration.Configuration;
import org.ydle.utils.ActivityUtils;
import org.ydle.utils.ServerUtils;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class YdleServiceImpl implements YdleService {

	private static final String TAG = "Ydle.YdleServiceImpl";

	@Inject
	protected JsonConverter converter;
	@Inject
	protected SharedPreferences prefs;

	private final String URL_ROOMS = "api/v1/rooms";

	@Override
	public List<Room> getRooms() {

		String result = ServerUtils.get("ApplicationIdentity",
				getConf().serveur.getUrl() + URL_ROOMS);
		
		ArrayList<Room> rooms = new ArrayList<Room>();
		if (result != null) {
			 Log.d(TAG, "rooms result : "+result);
			try {
				JSONObject json = new JSONObject(result);
				JSONArray roomsArray = json.getJSONArray("rooms");

				Room room = null;
				for (int a = 0; a < roomsArray.length(); a++) {
					JSONObject item = roomsArray.getJSONObject(a);
					room = converter.convertRoom(item);
					rooms.add(room);
				}
				Log.d("UPDATE", "load rooms : " + rooms.size());
				return rooms;
			} catch (JSONException e) {
				Log.e(TAG, "There was an error parsing the JSON", e);
			}
		}
		return DummyYdleContent.ITEMS;
	}

	@Override
	public List<SensorData> getData(Sensor sensor, TimeEchelle echelle) {
		List<SensorData> result = new ArrayList<SensorData>();
		Log.d(TAG, "getData -> " + sensor);
		if (sensor.type == SensorType.TEMP.getValeur()) {
			result.addAll(DummyYdleContent.getTempData(echelle));
		}

		Log.d(TAG, "getData -> " + result.size());
		return result;
	}

	public Configuration getConf() {
		return ActivityUtils.getConf(prefs);
	}

}
