package org.ydle.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class SensorData implements Parcelable {
	public String valeur;
	public Date date;
	
	
	
	public SensorData(String valeur, Date date) {
		super();
		this.valeur = valeur;
		this.date = date;
	}

	public SensorData(Parcel in) {
		this.valeur = in.readString();
		this.date = new Date(in.readLong());
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.valeur);
		dest.writeLong(this.date.getTime());
	}
	
	public static final Parcelable.Creator<SensorData> CREATOR = new Parcelable.Creator<SensorData>() {
		@Override
		public SensorData createFromParcel(Parcel source) {
			return new SensorData(source);
		}

		@Override
		public SensorData[] newArray(int size) {
			return new SensorData[size];
		}
	};
	
	public String toString() {
		return valeur;
	}
}
