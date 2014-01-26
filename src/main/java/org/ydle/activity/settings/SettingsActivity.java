package org.ydle.activity.settings;

import org.ydle.ChangeLog;
import org.ydle.R;
import org.ydle.activity.AboutActivity;

import roboguice.activity.RoboPreferenceActivity;
import roboguice.inject.InjectPreference;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.google.inject.Inject;

public class SettingsActivity extends RoboPreferenceActivity {

	private static final boolean ALWAYS_SIMPLE_PREFS = false;

	@InjectPreference("aPropos")
	public Preference aPropo;
	@InjectPreference("servers")
	public Preference hosts;
	@InjectPreference("pref_extra_ydle")
	public Preference extra;
	@InjectPreference("aNews")
	public Preference news;

	@Inject
	protected SharedPreferences prefs;

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		setupSimplePreferencesScreen();

		
		// Intent detailIntent = new Intent(this, HostDetailActivity.class);
		Intent detailIntent = new Intent(this, HostListActivity.class);
		// detailIntent.putExtra(IntentConstantes.ITEM,
		// (Parcelable) ActivityUtils.getConf(prefs).serveur);
		startActivity(hosts, detailIntent);
		// startActivity(hosts, HostListActivity.class);

		

		startActivity(extra, ExtraActivity.class);

		
		startActivity(aPropo, AboutActivity.class);

		if (news != null) {
			news.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference arg0) {
					ChangeLog cl = new ChangeLog(SettingsActivity.this);
					cl.getFullLogDialog().show();
					return true;
				}
			});
		}
	}

	private void startActivity(final Preference pref,
			final Class<? extends Activity> activity) {
		if (pref != null) {
			pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference arg0) {
					startActivity(new Intent(SettingsActivity.this, activity));
					finish();
					return true;
				}
			});
		}
	}

	private void startActivity(final Preference pref, final Intent intent) {
		if (pref != null) {
			pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference arg0) {
					startActivity(intent);
					finish();
					return true;
				}
			});
		}
	}

	private void setupSimplePreferencesScreen() {
		//if (!isSimplePreferences(this)) {
		//	return;
		//}
		// Add 'general' preferences.
		addPreferencesFromResource(R.xml.pref_general);
	}

	/** {@inheritDoc} */
	@Override
	public boolean onIsMultiPane() {
		return isXLargeTablet(this) && !isSimplePreferences(this);
	}

	/**
	 * Helper method to determine if the device has an extra-large screen. For
	 * example, 10" tablets are extra-large.
	 */
	private static boolean isXLargeTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
	}

	private static boolean isSimplePreferences(Context context) {
		return ALWAYS_SIMPLE_PREFS
				|| Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
				|| !isXLargeTablet(context);
	}

	/**
	 * A preference value change listener that updates the preference's summary
	 * to reflect its new value.
	 */
	private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object value) {
			String stringValue = value.toString();

			if (preference instanceof ListPreference) {
				// For list preferences, look up the correct display value in
				// the preference's 'entries' list.
				ListPreference listPreference = (ListPreference) preference;
				int index = listPreference.findIndexOfValue(stringValue);

				// Set the summary to reflect the new value.
				preference
						.setSummary(index >= 0 ? listPreference.getEntries()[index]
								: null);

			} else {
				// For all other preferences, set the summary to the value's
				// simple string representation.
				preference.setSummary(stringValue);
			}
			return true;
		}
	};

	private static void bindPreferenceSummaryToValue(Preference preference) {
		// Set the listener to watch for value changes.
		preference
				.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

		// Trigger the listener immediately with the preference's
		// current value.
		sBindPreferenceSummaryToValueListener.onPreferenceChange(
				preference,
				PreferenceManager.getDefaultSharedPreferences(
						preference.getContext()).getString(preference.getKey(),
						""));
	}

	/**
	 * This fragment shows general preferences only. It is used when the
	 * activity is showing a two-pane settings UI.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class GeneralPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.pref_general);
		}
	}

}
