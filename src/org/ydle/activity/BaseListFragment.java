package org.ydle.activity;

import org.ydle.model.configuration.Configuration;
import org.ydle.utils.ActivityUtils;

import roboguice.fragment.RoboListFragment;
import android.content.SharedPreferences;

import com.google.inject.Inject;

public class BaseListFragment extends RoboListFragment {

	@Inject
	protected SharedPreferences prefs;

	
	public Configuration getConf() {
		return ActivityUtils.getConf(prefs);
	}

}