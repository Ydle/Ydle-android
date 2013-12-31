package org.ydle.model;

public enum TimeEchelle {
	DAY("Jour",2,"HH"), WEEK("semaine",10,"dd"), MONTH("Mois",5,"dd"), YEAR("Année",1,"MM");

	private String label;
	private int scale;
	private String dateFormat;

	private TimeEchelle(String label,int scale,String dateFormat) {
		this.label = label;
		this.scale = scale;
		this.dateFormat =dateFormat;
	}

	public String getLabel() {
		return label;
	}

	public String getDateFormat() {
		return dateFormat;
	}
	
	public int getScale() {
		return scale;
	}

}
