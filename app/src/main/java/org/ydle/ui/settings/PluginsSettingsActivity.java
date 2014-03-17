package org.ydle.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import org.ydle.R;

public class PluginsSettingsActivity extends YdlePreferenceActivity {

    private static final String LOG_TAG = PluginsSettingsActivity.class.getSimpleName();

    public static int CODE_RETOUR = 1;

    public static void displayPluginsSettingsActivity(final Activity from) {
        final Intent i = new Intent(from, PluginsSettingsActivity.class);
        from.startActivityForResult(i, CODE_RETOUR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_plugins);
    }
}
