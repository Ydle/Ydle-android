package org.ydle.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TypeRoom implements Parcelable {

	public String id;
	public String name;
	public boolean active;
	public String description;

	@Override
	public int describeContents() {
		return 0;
	}

	public TypeRoom(Parcel in) {
		String[] data = new String[5];

		in.readStringArray(data);
		this.id = in.readString();
		this.name = in.readString();
		this.description = in.readString();
		this.active = Boolean.getBoolean(in.readString());
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(this.id);
		dest.writeString(this.name);
		dest.writeString(this.description);
		dest.writeString(String.valueOf(this.active));

	}
	
	public static final Parcelable.Creator<TypeRoom> CREATOR = new Parcelable.Creator<TypeRoom>() {
		@Override
		public TypeRoom createFromParcel(Parcel source) {
			return new TypeRoom(source);
		}

		@Override
		public TypeRoom[] newArray(int size) {
			return new TypeRoom[size];
		}
	};
}
