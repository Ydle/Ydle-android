package org.ydle.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Jean-Baptiste on 31/01/14.
 */
public class Sensor implements Parcelable {

    private static final String LOG_TAG = Sensor.class.getSimpleName();

    private int mId;
    private String mName;
    private boolean mActive;
    private String mUnit;
    private Object mCurrentValue;

    public Sensor(int id, String name, boolean active, String unit) {
        this(id, name, active, unit, null);
    }

    public Sensor(int id, String name, boolean active, String unit, Object currentValue) {
        this.mId = id;
        this.mName = name;
        this.mActive = active;
        this.mUnit = unit;
        this.mCurrentValue = currentValue;
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

    public String getUnit() {
        return mUnit;
    }

    public void setUnit(String unit) {
        this.mUnit = unit;
    }

    public Object getCurrentValue() {
        return mCurrentValue;
    }

    public void setCurrentValue(Object currentValue) {
        this.mCurrentValue = currentValue;
    }

    protected Sensor(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mActive = in.readByte() != 0x00;
        mUnit = in.readString();
        mCurrentValue = (Object) in.readValue(Object.class.getClassLoader());
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
        dest.writeString(mUnit);
        dest.writeValue(mCurrentValue);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Sensor> CREATOR = new Parcelable.Creator<Sensor>() {
        @Override
        public Sensor createFromParcel(Parcel in) {
            return new Sensor(in);
        }

        @Override
        public Sensor[] newArray(int size) {
            return new Sensor[size];
        }
    };
}
