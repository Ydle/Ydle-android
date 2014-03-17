package org.ydle.ui.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;

import org.ydle.R;
import org.ydle.config.Config;
import org.ydle.utils.StringUtils;

import java.io.IOException;

public class HomeSettingsActivity extends YdlePreferenceActivity implements Preference.OnPreferenceClickListener{

    private static final String LOG_TAG = HomeSettingsActivity.class.getSimpleName();

//    private Preference mPluginsPreference;
    private Preference mServerPreference;
    private Preference mNewsPreference;
    private Preference mAboutPreference;

    public static int CODE_RETOUR = 1;

    public static void displayHomeSettingsActivity(final Activity from) {
        final Intent i = new Intent(from, HomeSettingsActivity.class);
        from.startActivityForResult(i, CODE_RETOUR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_home);

        mServerPreference = (Preference) findPreference(getString(R.string.PREFERENCE_SERVER));
        mServerPreference.setOnPreferenceClickListener(this);
//        mPluginsPreference = (Preference) findPreference(getString(R.string.PREFERENCE_PLUGINS));
//        mPluginsPreference.setOnPreferenceClickListener(this);
        mNewsPreference = (Preference) findPreference(getString(R.string.PREFERENCE_NEWS));
        mNewsPreference.setOnPreferenceClickListener(this);
        mAboutPreference = (Preference) findPreference(getString(R.string.PREFERENCE_ABOUT));
        mAboutPreference.setOnPreferenceClickListener(this);
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(preference == mServerPreference) {
            ServerSettingsActivity.displayServerSettingsActivity(HomeSettingsActivity.this);
            return true;
//        } else if(preference == mPluginsPreference) {
//            PluginsSettingsActivity.displayPluginsSettingsActivity(HomeSettingsActivity.this);
//            return true;
        } else if(preference == mNewsPreference) {
            String news = "";
            try {
                news = StringUtils.streamToString(this.getAssets().open("news.txt"));
            } catch (IOException e) {
                if (Config.displayLog()) {
                    Log.d(LOG_TAG, "onPreferenceClick", e);
                }
            }
            new AlertDialog.Builder(HomeSettingsActivity.this)
                    .setTitle(getString(R.string.preference_news_title))
                    .setMessage(news)
                    .show();
            return true;
        }else if(preference == mAboutPreference) {
            AboutActivity.displayAboutSettingsActivity(HomeSettingsActivity.this);
            return true;
        }

        return false;
    }
}
