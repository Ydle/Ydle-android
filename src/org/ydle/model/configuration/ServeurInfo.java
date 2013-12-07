package org.ydle.model.configuration;

import java.io.Serializable;

import org.ydle.model.Room;
import org.ydle.model.TypeRoom;

import android.os.Parcel;
import android.os.Parcelable;

public class ServeurInfo implements Parcelable, Serializable {

	private static final String applicationName="Ydle/";
	
	public String nom;

	public String host;
	public int port;
	public String identifiant;

	public ServeurInfo(String nom, String host) {
		this.nom = nom;
		this.host = host;
	}

	@Override
	public String toString() {
		return nom;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public ServeurInfo(Parcel in) {
		if (in != null) {
			this.nom = in.readString();
			this.host = in.readString();
			this.port = in.readInt();
			this.identifiant = in.readString();
		}
	}

	public ServeurInfo() {
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(this.nom);
		dest.writeString(this.host);
		dest.writeInt(this.port);
		dest.writeString(this.identifiant);

	}

	public static final Parcelable.Creator<ServeurInfo> CREATOR = new Parcelable.Creator<ServeurInfo>() {
		@Override
		public ServeurInfo createFromParcel(Parcel source) {
			return new ServeurInfo(source);
		}

		@Override
		public ServeurInfo[] newArray(int size) {
			return new ServeurInfo[size];
		}
	};

	public String getUrl() {
		return host+":"+port+"/"+applicationName+"api.php";
	}
}
