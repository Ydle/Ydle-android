package org.ydle.model;

import org.ydle.R;

public enum TypeRoomIcon {
	KITCHEN(R.drawable.kitchen, "Cuisine"), BEDROOM(R.drawable.bed, "Chambre"), CHILDRENBEDROOM(
			R.drawable.bed1, "Chambre enfant"), GARAGE(R.drawable.garage,
			"Garage"), OFFICE(R.drawable.office, "Bureau"), BATHROOM(
			R.drawable.bathtub, "Salle de bain"), SHOWERROOM(R.drawable.sink2,
			"Salle d'eau"), GARDEN(R.drawable.garden2, "Jardin"), STAIRS(
			R.drawable.stairs, "Escalier"), SALON(R.drawable.sofa2, "Salon"), SEJOUR(
			R.drawable.sofa2, "Sejour"), AUTRE(R.drawable.inconnue_rouge, "");

	private int drawable;
	private String label;

	private TypeRoomIcon(int drawable, String label) {
		this.drawable = drawable;
		this.label = label;
	}

	public int getDrawable() {
		return drawable;
	}

	public static TypeRoomIcon fromDrawable(int readInt) {
		for (TypeRoomIcon value : values()) {
			if (value.drawable == readInt) {
				return value;
			}
		}
		return null;
	}

	public static TypeRoomIcon fromLabel(String label) {

		for (TypeRoomIcon value : values()) {
			if (value.label.equals(label)) {
				return value;
			}
		}
		return TypeRoomIcon.AUTRE;
	}

}
