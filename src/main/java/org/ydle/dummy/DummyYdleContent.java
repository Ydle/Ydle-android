package org.ydle.dummy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ydle.model.Sensor;
import org.ydle.model.SensorData;
import org.ydle.model.SensorType;
import org.ydle.model.Room;
import org.ydle.model.TimeEchelle;
import org.ydle.model.TypeRoomIcon;

import android.util.Log;

public class DummyYdleContent {

	private static final String TAG = "Ydle.DummyYdleContent";
	/**
	 * An array of sample (dummy) items.
	 */
	public static List<Room> ITEMS = new ArrayList<Room>();

	static {

		SensorData data = new SensorData("21", new Date());

		// Add 3 sample items.
		Room room = new Room("1", "Séjour", "Pièce de vie", TypeRoomIcon.SALON);
		room.sensor.add(new Sensor("Temperature", "°C", data, SensorType.TEMP));
		data = new SensorData("49", new Date());
		room.sensor.add(new Sensor("Hygrometrie", "%", data, SensorType.HYDRO));
		addItem(room);
		room = new Room("2", "Chambre 1", "Suite parentale",
				TypeRoomIcon.BEDROOM);
		data = new SensorData("19", new Date());
		room.sensor.add(new Sensor("Temperature", "°C", data, SensorType.TEMP));
		data = new SensorData("45", new Date());
		room.sensor.add(new Sensor("Hygrometrie", "%", data, SensorType.HYDRO));
		addItem(room);
		room = new Room("3", "Chambre 2", "Chambre d'amis",
				TypeRoomIcon.CHILDRENBEDROOM);
		data = new SensorData("18", new Date());
		room.sensor.add(new Sensor("Temperature", "°C", data, SensorType.TEMP));
		data = new SensorData("51", new Date());
		room.sensor.add(new Sensor("Hygrometrie", "%", data, SensorType.HYDRO));
		data = new SensorData("On", new Date());
		room.sensor.add(new Sensor("Lumière", "", data, SensorType.LUM));
		addItem(room);

		room = new Room("4", "Salle d'eau", "Salle d'eau",
				TypeRoomIcon.SHOWERROOM);
		addItem(room);

		room = new Room("5", "Garage", "Garage", TypeRoomIcon.GARAGE);
		room.active = false;
		addItem(room);

		room = new Room("6", "Bureau", "Bureau", TypeRoomIcon.OFFICE);
		room.active = false;
		addItem(room);

		room = new Room("7", "Escalier", "Escalier", TypeRoomIcon.STAIRS);
		addItem(room);
	}

	private static void addItem(Room item) {
		ITEMS.add(item);
	}

	public static List<SensorData> getTempData(TimeEchelle echelle) {
		List<SensorData> datas = new ArrayList<SensorData>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		try {
			Date now = new Date();

			int y = Integer.valueOf(new SimpleDateFormat("yyyy").format(now));

			if (echelle.equals(TimeEchelle.MONTH)) {

				int m = Integer.valueOf(new SimpleDateFormat("MM").format(now));
				sdf = new SimpleDateFormat("dd-MM-yyyy");
				for (int d = 1; d <= 31; d++) {
					String value = String.valueOf(30 - 15 * Math.random());
					datas.add(new SensorData(value, sdf.parse(to2Digit(d) + "-"
							+ to2Digit(m) + "-" + y)));
				}
			} else if (echelle.equals(TimeEchelle.YEAR)) {
				sdf = new SimpleDateFormat("dd-MM-yyyy");
				for (int m = 1; m <= 12; m++) {
					String value = String.valueOf(30 - 15 * Math.random());
					datas.add(new SensorData(value, sdf.parse("01-"
							+ to2Digit(m) + "-" + y)));
				}
			} else if (echelle.equals(TimeEchelle.YEAR)) {
				int m = Integer.valueOf(new SimpleDateFormat("MM").format(now));
				int d = Integer.valueOf(new SimpleDateFormat("dd").format(now));
				for (int h = 0; h <= 23; h += 2) {
					for (int mi = 0; mi <= 58; mi += 4) {

						String value = String.valueOf(30 - 15 * Math.random());
						datas.add(new SensorData(value, sdf.parse(to2Digit(d)
								+ "-" + to2Digit(m) + "-" + y + " "
								+ to2Digit(h) + ":" + to2Digit(mi))));
					}
				}
			}

		} catch (ParseException e) {
			Log.e(TAG, "erreur : " + e.getMessage());
		} catch (Exception e) {
			Log.e(TAG, "erreur : " + e.getMessage());
		}

		Log.d(TAG, "size : " + datas.size()+" "+echelle);
		return datas;
	}

	private static String to2Digit(int m) {

		if (m < 10) {
			return "0" + String.valueOf(m);
		}

		return String.valueOf(m);
	}

}
