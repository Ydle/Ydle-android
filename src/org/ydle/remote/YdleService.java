package org.ydle.remote;

import java.util.List;

import org.ydle.model.Room;
import org.ydle.model.Sensor;
import org.ydle.model.SensorData;
import org.ydle.model.TimeEchelle;

public interface YdleService {

	List<Room> getRooms();
	
	List<SensorData> getData(Sensor sensor,TimeEchelle echelle);

}
