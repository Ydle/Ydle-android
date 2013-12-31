package org.ydle.activity.common;

import org.ydle.model.configuration.Configuration;
import org.ydle.utils.PreferenceUtils;

import roboguice.fragment.RoboFragment;
import android.content.SharedPreferences;

import com.google.inject.Inject;

public class BaseFragment extends RoboFragment {

	@Inject
	protected SharedPreferences prefs;

	public Configuration getConf() {
		return PreferenceUtils.getConf(prefs);
	}

}
