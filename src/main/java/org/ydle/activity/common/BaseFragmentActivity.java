package org.ydle.activity.common;

import org.ydle.R;
import org.ydle.activity.settings.SettingsActivity;
import org.ydle.model.configuration.Configuration;
import org.ydle.utils.PreferenceUtils;

import roboguice.activity.RoboFragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.google.inject.Inject;

public abstract class BaseFragmentActivity extends RoboFragmentActivity {

    @Inject
    protected SharedPreferences prefs;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivityForResult(new Intent(this, SettingsActivity.class), 1);
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public Configuration getConf() {
        return PreferenceUtils.getConf(prefs);
    }

}
