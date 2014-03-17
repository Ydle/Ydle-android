package org.ydle.network.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ydle.data.model.Sensor;

import java.util.ArrayList;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class SensorsParser {

    private static final String LOG_TAG = SensorsParser.class.getSimpleName();

    //TODO Logs

    public static ArrayList<Sensor> parseAllSensors(JSONArray array) {
        ArrayList<Sensor> sensorsList = null;

        if (array.length() > 0) {
            sensorsList = new ArrayList<Sensor>();
            Sensor sensor = null;
            for (int i = 0; i < array.length(); i++) {
                try {
                    sensor = parseASensor(array.getJSONObject(i));
                    if (sensor != null) {
                        sensorsList.add(sensor);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return sensorsList;
    }

    public static Sensor parseASensor(JSONObject item) throws JSONException {
        Sensor sensor = null;
        if (item != null) {
//            sensor = new Sensor();
//            sensor.name = getFacultatifString(item, "name");
//            sensor.unit = getFacultatifString(item, "unit");
//            sensor.type = SensorType.fromLabel(getFacultatifString(item, "name")).getValeur();
//            sensor.active = getFacultatifBoolean(item, "active");
//            sensor.description = getFacultatifString(item, "description");
//            sensor.currentValeur = new SensorData(getFacultatifString(item,
//                    "current"), new Date());
//            sensor.datas = new ArrayList<SensorData>();
//            sensor.datas.add(sensor.currentValeur);
        }

        return sensor;
    }

}
