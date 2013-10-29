package org.ydle.model;

public enum TimeEchelle {
	DAY("Jour"), WEEK("semaine"), MONTH("Mois"), YEAR("Ann�e");

	private String label;

	private TimeEchelle(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
