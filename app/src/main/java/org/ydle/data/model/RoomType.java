package org.ydle.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Jean-Baptiste on 3/02/14.
 */
public class RoomType implements Parcelable {

    private static final String LOG_TAG = RoomType.class.getSimpleName();

    private int mId;
    private String mName;
    private String mDescription;
    private boolean mActive;
    private int mNumberRooms;

    public RoomType() {}

    public RoomType(int id, String name, String description, boolean active, int numberRooms) {
        this.mId = id;
        this.mName = name;
        this.mDescription = description;
        this.mActive = active;
        this.mNumberRooms = numberRooms;
    }

    public RoomType(int id, String name, String description, boolean active) {
        this(id, name, description, active, 0);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean active) {
        this.mActive = active;
    }

    public int getNumberRooms() { return mNumberRooms; }

    public void setNumberRooms(int numberRooms) {
        this.mNumberRooms = numberRooms;
    }

    public static String[] getActiveRoomTypesName (ArrayList<RoomType> roomTypes) {
        if(roomTypes == null) {
            return new String[0];
        }

        String[] array = new String[roomTypes.size()];
        int i = 0;
        for(RoomType roomType : roomTypes) {
            if (roomType.isActive()) {
                array[i] = roomType.getName();
                i++;
            }
        }
        return array;
    }

    @Override
    public String toString() {
        return "Room{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mActive=" + mActive +
                ", mNumberRooms=" + mNumberRooms +
                '}';
    }

    protected RoomType(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mActive = in.readByte() != 0x00;
        mNumberRooms = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeByte((byte) (mActive ? 0x01 : 0x00));
        dest.writeValue(mNumberRooms);
    }

    @SuppressWarnings("unused")
    public static final Creator<RoomType> CREATOR = new Creator<RoomType>() {
        @Override
        public RoomType createFromParcel(Parcel in) {
            return new RoomType(in);
        }

        @Override
        public RoomType[] newArray(int size) {
            return new RoomType[size];
        }
    };
}
