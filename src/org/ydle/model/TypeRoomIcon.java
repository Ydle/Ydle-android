package org.ydle.model;

import org.ydle.R;

public enum TypeRoomIcon {
	KITCHEN(R.drawable.kitchen), BEDROOM(R.drawable.bed), CHILDRENBEDROOM(
			R.drawable.bed1), GARAGE(R.drawable.garage), OFFICE(
			R.drawable.office), BATHROOM(R.drawable.bathtub), SHOWERROOM(
			R.drawable.sink2), GARDEN(R.drawable.garden2), STAIRS(
			R.drawable.stairs), SALON(R.drawable.sofa2);

	private int drawable;

	private TypeRoomIcon(int drawable) {
		this.drawable = drawable;
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

}
