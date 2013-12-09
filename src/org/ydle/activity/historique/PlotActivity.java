package org.ydle.activity.historique;

import java.util.List;

import org.ydle.R;
import org.ydle.activity.BaseActivity;
import org.ydle.activity.IntentConstantes;
import org.ydle.model.SensorData;
import org.ydle.model.TimeEchelle;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.androidplot.xy.XYPlot;

public abstract class PlotActivity<T> extends BaseActivity {

	private static final String TAG = "Ydle.PlotActivity";

	protected XYPlot plot;

	protected TimeEchelle echelle = TimeEchelle.MONTH;

	protected T item;

	protected List<SensorData> datas;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		item = getIntent().getParcelableExtra(IntentConstantes.ITEM);

		// fun little snippet that prevents users from taking screenshots
		// on ICS+ devices :-)
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
		// WindowManager.LayoutParams.FLAG_SECURE);

		setContentView(R.layout.xy_plot);
		// initialize our XYPlot reference:
		plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
		plot.setAlpha(0f);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plot, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_day:
			onChangeEchelle(TimeEchelle.DAY);
			break;
		case R.id.action_week:
			onChangeEchelle(TimeEchelle.WEEK);
			break;
		case R.id.action_month:
			onChangeEchelle(TimeEchelle.MONTH);
			break;
		case R.id.action_year:
			onChangeEchelle(TimeEchelle.YEAR);
			break;
		case R.id.menu_refresh:
			refresh();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void onChangeEchelle(TimeEchelle newEchelle) {

		if (!newEchelle.equals(echelle)) {
			echelle = newEchelle;
			Log.d(TAG, "select echelle : " + echelle.getLabel());
			refresh();
		}
	}

	private void refresh() {
		plot.clear();
		loadData();
	}

	public abstract void initPlot(XYPlot plott, List<SensorData> result);

	public abstract void loadData();

}
