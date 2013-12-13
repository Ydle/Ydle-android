package org.ydle.model.configuration;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("serial")
public class ServeurInfo implements Parcelable, Serializable {

	private static final String applicationName = "app_dev.php/";

	public String nom;

	public String host;
	public int port;
	public String identifiant;
	public boolean actif;

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


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ServeurInfo other = (ServeurInfo) obj;

		if (nom == null) {
			if (other.nom != null) {
				return false;
			}
		} else if (!nom.equals(other.nom)) {
			return false;
		}

		return true;
	}
	
	

	public String getUrl() {
		return host + ":" + port + "/" + applicationName;
	}
}
