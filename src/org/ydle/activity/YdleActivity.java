package org.ydle.activity;

import org.ydle.R;
import org.ydle.model.configuration.Configuration;
import org.ydle.utils.PreferenceUtils;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.google.inject.Inject;


public class YdleActivity extends RoboActivity {

	@InjectView(R.id.textView1)
	TextView textView1;
	
	@Inject
	protected SharedPreferences prefs;

	private static final int STOPSPLASH = 0;
	private static final long SPLASHTIME = 1000;

	private Handler splashHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STOPSPLASH:
				onStartApplication();
				break;
			}
			finish();
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ydle);
		Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);
	}

	protected void onStartApplication() {
		Intent main = new Intent(YdleActivity.this, MainActivity.class);
		startActivity(main);
		finish();
	}
	
	public Configuration getConf() {
		return PreferenceUtils.getConf(prefs);
	}

}
