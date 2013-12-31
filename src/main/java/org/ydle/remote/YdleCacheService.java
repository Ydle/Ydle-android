package org.ydle.remote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ydle.model.Room;
import org.ydle.model.Sensor;
import org.ydle.model.SensorData;
import org.ydle.model.TimeEchelle;
import org.ydle.model.configuration.Configuration;
import org.ydle.utils.PreferenceUtils;

import android.content.SharedPreferences;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class YdleCacheService implements YdleService {

	@Inject
	YdleServiceImpl ydleService;
	
	@Inject
	protected SharedPreferences prefs;

	List<Room> rooms = null;

	Map<String, Room> roomDetails = new HashMap<String, Room>();
	

	@Override
	public List<Room> getRooms() {

		if (rooms == null || !isActiveCache()) {
			rooms = ydleService.getRooms();
		}

		return rooms;
	}

	@Override
	public Room getRoomDetails(String id) {

		if (!roomDetails.containsKey(id) || !isActiveCache()) {
			roomDetails.put(id, ydleService.getRoomDetails(id));
		}
		return roomDetails.get(id);
	}

	@Override
	public List<SensorData> getData(Sensor sensor, TimeEchelle echelle) {
		return ydleService.getData(sensor, echelle);
	}

	public boolean isActiveCache() {
		return getConf().cacheActive;
	}

	
	public Configuration getConf() {
		return PreferenceUtils.getConf(prefs);
	}
}
