package org.ydle.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Jean-Baptiste on 31/01/14.
 */
public class Room implements Parcelable {

    private static final String LOG_TAG = Room.class.getSimpleName();

    private int mId;
    private String mName;
    private boolean mActive;
    private RoomType mType;
    private String mDescription;
    private int mSensorNumber;
    private ArrayList<Sensor> mSensors;

    public Room(int id, String name, boolean active, RoomType type, String description, int sensorNumber, ArrayList<Sensor> sensors) {
        this.mId = id;
        this.mName = name;
        this.mActive = active;
        this.mType = type;
        this.mDescription = description;
        this.mSensorNumber = sensorNumber;
        this.mSensors = sensors;
    }

    public Room(int id, String name, boolean active, RoomType type, String description, int sensorNumber) {
        this(id, name, active, type, description, sensorNumber, null);
    }

    public Room() {
        this.mSensors = null;
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

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean active) {
        this.mActive = active;
    }

    public RoomType getType() {
        return mType;
    }

    public void setType(RoomType type) {
        this.mType = type;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public int getSensorNumber() {
        return mSensorNumber;
    }

    public void setSensorNumber(int sensorNumber) {
        this.mSensorNumber = mSensorNumber;
    }

    public ArrayList<Sensor> getSensors() {
        return mSensors;
    }

    public void setSensors(ArrayList<Sensor> sensors) {
        this.mSensors = sensors;
    }

    @Override
    public String toString() {
        return "Room{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mActive=" + mActive +
                ", mType=" + mType +
                ", mDescription='" + mDescription + '\'' +
                ", mSensorNumber=" + mSensorNumber +
                ", mSensors=" + mSensors +
                '}';
    }

    protected Room(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mActive = in.readByte() != 0x00;
        mType = (RoomType) in.readValue(RoomType.class.getClassLoader());
        mDescription = in.readString();
        mSensorNumber = in.readInt();
        if (in.readByte() == 0x01) {
            mSensors = new ArrayList<Sensor>();
            in.readList(mSensors, Sensor.class.getClassLoader());
        } else {
            mSensors = null;
        }
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
        dest.writeValue(mType);
        dest.writeString(mDescription);
        dest.writeInt(mSensorNumber);
        if (mSensors == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mSensors);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Room> CREATOR = new Parcelable.Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };
}
