package org.ydle.fragment.settings;

import org.ydle.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class ExtraFragment extends PreferenceFragment{


	public static final String ARG_ITEM_ID = "item_id";


	public ExtraFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.pref_extra);

		if (getArguments() != null && getArguments().containsKey(ARG_ITEM_ID)) {
			//TODO
		}
	}

}
