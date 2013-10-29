package org.ydle.remote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.ydle.model.SensorData;
import org.ydle.model.TimeEchelle;

import android.app.Activity;
import android.util.Log;

public class FiltreSensorDataAsynkTask extends AbstractTask<List<SensorData>> {

	private static final String TAG = "Ydle.FiltreSensorDataAsynkTask";
	private List<SensorData> datas;
	private TimeEchelle echelle;

	public FiltreSensorDataAsynkTask(Activity context, List<SensorData> datas,
			TimeEchelle echelle) {
		super(context);
		this.datas = datas;
		this.echelle = echelle;
	}

	@Override
	protected List<SensorData> doInBackground(Void... params) {

		return filtreData(datas, echelle);
	}

	private List<SensorData> filtreData(List<SensorData> datas,
			TimeEchelle echelle) {
		List<SensorData> result = new ArrayList<SensorData>();
		if (datas != null) {
			Date now = new Date();
			SimpleDateFormat sdf = null;

			if (TimeEchelle.MONTH.equals(echelle)) {
				sdf = new SimpleDateFormat("MMyyyy");
			} else if (echelle.equals(TimeEchelle.DAY)) {
				sdf = new SimpleDateFormat("ddMMyyyy");
			} else if (echelle.equals(TimeEchelle.YEAR)) {
				sdf = new SimpleDateFormat("yyyy");
			} else if (echelle.equals(TimeEchelle.WEEK)) {
				sdf = new SimpleDateFormat("ww-yyyy");
			} else {
				sdf = new SimpleDateFormat("MMyyyy");
			}

			for (SensorData data : datas) {
				// TODO filtre data
				if (data != null
						&& sdf.format(now).equals(sdf.format(data.date))) {
					result.add(data);
				}

			}

			result = moyData(result, echelle);

			for (SensorData data : result) {
				if (data != null) {
					Log.d(TAG,
							"data : "
									+ new SimpleDateFormat("ddMMyyyy")
											.format(data.date) + " "
									+ data.valeur);
				}
			}
		}
		return result;
	}

	private List<SensorData> moyData(List<SensorData> datas, TimeEchelle echelle) {

		Collections.sort(datas, new Comparator<SensorData>() {

			@Override
			public int compare(SensorData lhs, SensorData rhs) {
				return lhs.date.compareTo(rhs.date);
			}
		});

		List<SensorData> result = new ArrayList<SensorData>();
		if (echelle.equals(TimeEchelle.MONTH)) {
			int jour = 1;
			int sum = 0;
			int nb = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("dd");
			SensorData previous = null;
			for (SensorData data : datas) {
				if (to2Digit(jour).equals(sdf.format(data.date))) {
					sum += Float.valueOf(data.valeur).intValue();
					nb++;

				} else {
					jour++;
					if (nb != 0) {
						previous.valeur = String.valueOf(sum / nb);
					}
					if (previous != null) {
						result.add(previous);
					}
					sum = 0;
					nb = 0;
				}

				previous = data;
			}

			if (nb != 0) {
				previous.valeur = String.valueOf(sum / nb);
			}
			if (previous != null) {
				result.add(previous);
			}

			Log.d(TAG, "data size : " + result.size());

		} else if (echelle.equals(TimeEchelle.WEEK)) {
			int jour = 1;
			int sum = 0;
			int nb = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("dd");
			SensorData previous = null;
			for (SensorData data : datas) {
				if (to2Digit(jour).equals(sdf.format(data.date))) {
					sum += Float.valueOf(data.valeur).intValue();
					nb++;

				} else {
					jour++;
					if (nb != 0) {
						previous.valeur = String.valueOf(sum / nb);
					}
					if (previous != null) {
						result.add(previous);
					}
					sum = 0;
					nb = 0;
				}

				previous = data;
			}

			if (nb != 0) {
				previous.valeur = String.valueOf(sum / nb);
			}
			if (previous != null) {
				result.add(previous);
			}

			Log.d(TAG, "data size : " + result.size());
		} else if (echelle.equals(TimeEchelle.DAY)) {
			result.addAll(datas);
		} else if (echelle.equals(TimeEchelle.YEAR)) {
			int mois = 1;
			int sum = 0;
			int nb = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("MM");
			SensorData previous = null;
			for (SensorData data : datas) {
				if (to2Digit(mois).equals(sdf.format(data.date))) {
					sum += Float.valueOf(data.valeur).intValue();
					nb++;

				} else {
					mois++;
					if (nb != 0) {
						previous.valeur = String.valueOf(sum / nb);
					}
					if (previous != null) {
						result.add(previous);
					}
					sum = 0;
					nb = 0;
				}

				previous = data;
			}

			if (nb != 0) {
				previous.valeur = String.valueOf(sum / nb);
			}
			if (previous != null) {
				result.add(previous);
			}

			Log.d(TAG, "data size : " + result.size());

		}

		return result;
	}

	private static String to2Digit(int m) {

		if (m < 10) {
			return "0" + String.valueOf(m);
		}

		return String.valueOf(m);
	}
}
