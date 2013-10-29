package org.ydle.remote;

import java.util.ArrayList;
import java.util.List;

import org.ydle.dummy.DummyYdleContent;
import org.ydle.model.Sensor;
import org.ydle.model.Room;
import org.ydle.model.SensorData;
import org.ydle.model.SensorType;
import org.ydle.model.TimeEchelle;

import android.util.Log;

public class YdleServiceImpl implements YdleService {

	private static final String TAG = "Ydle.YdleServiceImpl";

	@Override
	public List<Room> getRooms() {
		for (Room room : DummyYdleContent.ITEMS) {
			Log.d(TAG, " -> " + room);
			for (Sensor node : room.sensor) {
				Log.d(TAG, " --> " + node);
			}
		}
		return DummyYdleContent.ITEMS;
	}

	@Override
	public List<SensorData> getData(Sensor sensor,TimeEchelle echelle) {
		List<SensorData> result = new ArrayList<SensorData>();
		Log.d(TAG, "getData -> " + sensor);
		if (sensor.type == SensorType.TEMP.getValeur()) {
			result.addAll(DummyYdleContent.getTempData(echelle));
		}

		Log.d(TAG, "getData -> " + result.size());
		return result;
	}

}
