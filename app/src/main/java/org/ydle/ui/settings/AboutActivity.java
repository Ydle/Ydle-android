package org.ydle.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.TextView;

import org.ydle.BuildConfig;
import org.ydle.R;
import org.ydle.ui.YdleActivity;
import org.ydle.utils.DisplayUtils;

public class AboutActivity extends YdleActivity {

    private static final String LOG_TAG = AboutActivity.class.getSimpleName();

    public static int CODE_RETOUR = 1;

    private TextView mVersionNumberTextView;
    private TextView mLinkTextView;

    public static void displayAboutSettingsActivity(final Activity from) {
        final Intent i = new Intent(from, AboutActivity.class);
        from.startActivityForResult(i, CODE_RETOUR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        mVersionNumberTextView = (TextView) findViewById(R.id.about_version_number_textview);
        mVersionNumberTextView.setText(BuildConfig.VERSION_NAME);

        mLinkTextView = (TextView) findViewById(R.id.about_ydle_link_textview);
        mLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayUtils.goToUrl(AboutActivity.this, "http://" + ((TextView) v).getText().toString());
            }
        });
    }
}
