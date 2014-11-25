package org.ydle.data.memoryprovider;

import com.android.volley.VolleyError;

import org.ydle.data.model.Log;
import org.ydle.data.model.Room;
import org.ydle.data.model.RoomType;
import org.ydle.data.model.YdleError;

import java.util.ArrayList;

/**
 * Created by Jean-Baptiste on 01/02/14.
 */
public class MemoryProvider {

    private static final String LOG_TAG = MemoryProvider.class.getSimpleName();

    private static MemoryProvider ourInstance;

    public static MemoryProvider getInstance() {
        if (ourInstance == null) {
            ourInstance = new MemoryProvider();
        }

        return ourInstance;
    }

    public static void resetInstance() {
        if (ourInstance != null) {
            ourInstance.resetData();
        }
        ourInstance = null;
    }

    public void resetData() {
        resetRooms();
        resetRoomTypes();
    }

    /**
     * GetAllRooms data
     */
    private ArrayList<Room> mRooms;
    private YdleError mRoomsError;
    private VolleyError mRoomsVolleyError;

    public ArrayList<Room> getRooms() {
        return mRooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.mRooms = rooms;
    }

    public YdleError getRoomsError() {
        return mRoomsError;
    }

    public void setRoomsError(YdleError roomsError) {
        this.mRoomsError = roomsError;
    }

    public VolleyError getRoomsVolleyError() {
        return mRoomsVolleyError;
    }

    public void setRoomsVolleyError(VolleyError roomsVolleyError) {
        this.mRoomsVolleyError = roomsVolleyError;
    }

    public void resetRooms() {
        mRooms = null;
        mRoomsError = null;
        mRoomsVolleyError = null;
    }

    public void updateRoom(Room room) {
        for (Room current : mRooms) {
            if (current.getId() == room.getId()) {
                mRooms.remove(current);
                break;
            }
        }
        mRooms.add(room);
    }

    /**
     * GetAllRoomTypes data
     */
    private ArrayList<RoomType> mRoomTypes;
    private YdleError mRoomTypesError;
    private VolleyError mRoomTypesVolleyError;

    public ArrayList<RoomType> getRoomTypes() {
        return mRoomTypes;
    }

    public RoomType getRoomType(String name) {
        if (mRoomTypes == null){
            return null;
        }
        for(RoomType roomType : mRoomTypes) {
            if (roomType.getName().equals(name)){
                return roomType;
            }
        }
        return null;
    }

    public void setRoomTypes(ArrayList<RoomType> roomTypes) {
        this.mRoomTypes = roomTypes;
    }

    public YdleError getRoomTypesError() {
        return mRoomTypesError;
    }

    public void setRoomTypesError(YdleError roomTypesError) {
        this.mRoomTypesError = roomTypesError;
    }

    public VolleyError getRoomTypesVolleyError() {
        return mRoomTypesVolleyError;
    }

    public void setRoomTypesVolleyError(VolleyError roomTypesVolleyError) {
        this.mRoomTypesVolleyError = roomTypesVolleyError;
    }

    public void resetRoomTypes() {
        mRoomTypes = null;
        mRoomTypesError = null;
        mRoomTypesVolleyError = null;
    }

    /**
     * GetAllLogs data
     */
    private ArrayList<Log> mLogs = new ArrayList<Log>();
    private YdleError mLogsError;
    private VolleyError mLogsVolleyError;

    public ArrayList<Log> getLogs() {
        if (mLogs != null && mLogs.size() > 0) {
            return mLogs;
        }

        return getTestLogs();
    }

    public void setLogs(ArrayList<Log> logs) {
        this.mLogs = logs;
    }

    public YdleError getLogsError() {
        return mLogsError;
    }

    public void setLogsError(YdleError logsError) {
        this.mLogsError = logsError;
    }

    public VolleyError getLogsVolleyError() {
        return mLogsVolleyError;
    }

    public void setLogsVolleyError(VolleyError logsVolleyError) {
        this.mLogsVolleyError = logsVolleyError;
    }



    public ArrayList<Log> getTestLogs() {
        ArrayList<Log> logs = new ArrayList<Log>();

        logs.add(new Log(Log.LogLevel.LEVEL_1, "Log de test"));
        logs.add(new Log(Log.LogLevel.LEVEL_1, "Log de test"));
        logs.add(new Log(Log.LogLevel.LEVEL_1, "Log de test"));
        logs.add(new Log(Log.LogLevel.LEVEL_1, "Log de test"));
        logs.add(new Log(Log.LogLevel.LEVEL_1, "Log de test"));

        return logs;
    }
}
