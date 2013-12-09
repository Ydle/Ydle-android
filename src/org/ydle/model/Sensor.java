package org.ydle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("serial")
public class Sensor implements Parcelable, Serializable {

	public String name;
	public String unit;
	public int type;

	public SensorData currentValeur;

	public List<SensorData> datas = new ArrayList<SensorData>();
	public boolean active;
	public String description;

	public Sensor(String name, String unit, SensorData valeur, SensorType type) {
		this.name = name;
		this.unit = unit;
		this.currentValeur = valeur;
		this.type = type.getValeur();
	}

	public Sensor(String name, String unit, SensorData data, SensorType type,
			List<SensorData> datas) {
		this(name, unit, data, type);
		this.datas = datas;
	}

	@SuppressWarnings("unchecked")
	public Sensor(Parcel in) {
		this.name = in.readString();
		this.unit = in.readString();
		this.description = in.readString();
		this.active = Boolean.getBoolean(in.readString());
		this.currentValeur = in.readParcelable(SensorData.class
				.getClassLoader());
		this.type = in.readInt();
		datas = in.readArrayList(SensorData.class.getClassLoader());
	}

	public Sensor() {
	}

	@Override
	public String toString() {
		return name + " : " + currentValeur + " " + unit;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.name);
		dest.writeString(this.unit);
		dest.writeString(this.description);
		dest.writeString(String.valueOf(this.active));
		dest.writeParcelable(this.currentValeur, 0);
		dest.writeInt(this.type);
		dest.writeArray(this.datas.toArray());
	}

	public static final Parcelable.Creator<Sensor> CREATOR = new Parcelable.Creator<Sensor>() {
		@Override
		public Sensor createFromParcel(Parcel source) {
			return new Sensor(source);
		}

		@Override
		public Sensor[] newArray(int size) {
			return new Sensor[size];
		}
	};
}
