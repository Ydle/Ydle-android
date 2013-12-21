package org.ydle.fragment.settings;

import org.ydle.IntentConstantes;
import org.ydle.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class ExtraFragment extends PreferenceFragment implements FramgmentValidator<Object>{


	public ExtraFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.pref_extra);

		if (getArguments() != null && getArguments().containsKey(IntentConstantes.ITEM)) {
			//TODO
		}
	}

	@Override
	public boolean isValide() {
		return true;
	}

	@Override
	public String getError() {
		return "";
	}

	@Override
	public Object getData() {
		return null;
	}

}
