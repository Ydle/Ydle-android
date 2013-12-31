package org.ydle.model;

import org.ydle.R;

public enum SensorType {
	TEMP(1, "Température", R.drawable.thermometer), HYDRO(2, "Hygrometries",
			R.drawable.goutte), LUM(3,"Luminosité", R.drawable.light), AUTRE(4,
			R.drawable.inconnue_rouge);

	private int valeur;
	private String label;
	private int drawable;

	private SensorType(int valeur, int drawable) {
		this.valeur = valeur;
		this.drawable = drawable;
	}

	private SensorType(int valeur, String label, int drawable) {
		this.valeur = valeur;
		this.label = label;
		this.drawable = drawable;
	}

	public int getValeur() {
		return valeur;
	}

	public String getLabel() {
		return label;
	}

	public static SensorType fromCode(int type) {

		for (SensorType value : values()) {
			if (value.valeur == type) {
				return value;
			}
		}
		return SensorType.AUTRE;
	}
	
	public static SensorType fromLabel(String label) {

		for (SensorType value : values()) {
			if (value.label.equals(label) ) {
				return value;
			}
		}
		return SensorType.AUTRE;
	}

	public int getDrawable() {
		return drawable;
	}

}
