package org.ydle.activity.common;

import org.ydle.model.configuration.Configuration;
import org.ydle.utils.PreferenceUtils;

import roboguice.fragment.RoboListFragment;
import android.content.SharedPreferences;

import com.google.inject.Inject;

public abstract class BaseListFragment extends RoboListFragment {

	@Inject
	protected SharedPreferences prefs;

	
	public Configuration getConf() {
		return PreferenceUtils.getConf(prefs);
	}

}