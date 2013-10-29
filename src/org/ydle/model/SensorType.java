package org.ydle.model;

public enum SensorType {
	TEMP(1, "Températures"), HYDRO(2, "Hygrometries"), LUM(3), AUTRE(4);

	private int valeur;
	private String label;

	private SensorType(int valeur) {
		this.valeur = valeur;
	}

	private SensorType(int valeur, String label) {
		this.valeur = valeur;
		this.label = label;
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
		return null;
	}
}
