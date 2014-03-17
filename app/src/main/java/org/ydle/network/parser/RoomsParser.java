package org.ydle.network.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ydle.data.model.Room;
import org.ydle.data.model.RoomType;
import org.ydle.data.model.Sensor;
import org.ydle.utils.JSONUtils;

import java.util.ArrayList;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class RoomsParser {

    private static final String LOG_TAG = RoomsParser.class.getSimpleName();

    //TODO Logs

    public static ArrayList<Room> parseAllRooms(JSONObject root) {
        if (root.optJSONArray("result") != null) {
            try {
                return parseAllRooms(root.getJSONArray("result"));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static ArrayList<Room> parseAllRooms(JSONArray array) {
        ArrayList<Room> roomsList = null;

        if (array.length() > 0) {
            roomsList = new ArrayList<Room>();
            Room room;
            for (int i = 0; i < array.length(); i++) {
                try {
                    room = parseARoom(array.getJSONObject(i));
                    if (room != null) {
                        roomsList.add(room);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return roomsList;
    }

    public static Room parseARoom(JSONObject item) throws JSONException {
        Room room = null;
        if (item != null) {
            room = new Room();
            room.setId(JSONUtils.getOptionalInt(item, "id"));
            room.setName(JSONUtils.getOptionalString(item, "name"));
            room.setDescription(JSONUtils.getOptionalString(item, "description"));
            room.setActive(JSONUtils.getOptionalBoolean(item, "is_active"));
            room.setType(new RoomType(-1, JSONUtils.getOptionalString(item, "type"), "", true));
            if (item.optJSONArray("sensors") != null) {
                ArrayList<Sensor> sensors = SensorsParser.parseAllSensors(item.getJSONArray("sensors"));
                room.setSensors(sensors);
                room.setSensorNumber(sensors.size());
            } else {
                room.setSensorNumber(JSONUtils.getOptionalInt(item, "sensors"));
            }
        }
        return room;
    }

}
