package org.ydle.network.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ydle.data.model.RoomType;
import org.ydle.utils.JSONUtils;

import java.util.ArrayList;

/**
 * Created by Jean-Baptiste on 06/03/14.
 */
public class RoomTypesParser {

    private static final String LOG_TAG = RoomTypesParser.class.getSimpleName();

    //TODO Logs

    public static ArrayList<RoomType> parseAllRoomTypes(JSONObject root) {
        if (root.optJSONArray("result") != null) {
            try {
                return parseAllRoomTypes(root.getJSONArray("result"));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static ArrayList<RoomType> parseAllRoomTypes(JSONArray array) {
        ArrayList<RoomType> roomTypesList = null;

        if (array.length() > 0) {
            roomTypesList = new ArrayList<RoomType>();
            RoomType roomType;
            for (int i = 0; i < array.length(); i++) {
                try {
                    roomType = parseARoomType(array.getJSONObject(i));
                    if (roomType != null) {
                        roomTypesList.add(roomType);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return roomTypesList;
    }

    public static RoomType parseARoomType(JSONObject item) throws JSONException {
        RoomType roomType = null;
        if (item != null) {
            roomType = new RoomType();
            roomType.setId(JSONUtils.getOptionalInt(item, "id"));
            roomType.setName(JSONUtils.getOptionalString(item, "name"));
            roomType.setDescription(JSONUtils.getOptionalString(item, "description"));
            roomType.setActive(JSONUtils.getOptionalBoolean(item, "is_active"));
            roomType.setNumberRooms(JSONUtils.getOptionalInt(item, "nb_rooms"));
        }
        return roomType;
    }

}
