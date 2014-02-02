package org.ydle.activity;

import roboguice.inject.InjectView;
import org.ydle.R;
import org.ydle.activity.common.BaseActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class RoomEditActivity extends BaseActivity {


	private String mName;
	private String mDesciption;

	// UI references.
	@InjectView(R.id.room_name)
	EditText mNameView;
	@InjectView(R.id.room_desc)
	EditText mDescriptionView;
	@InjectView(R.id.room_form)
	View mRoomFormView;
	@InjectView(R.id.room_status)
	View mRoomStatusView;
	@InjectView(R.id.room_status_message)
	TextView mRoomStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_room_edit);

		findViewById(R.id.room_add_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						saveRoom();
					}
				});
	}

	protected void saveRoom() {
		// Reset errors.
		mNameView.setError(null);
		mDescriptionView.setError(null);

		// Store values at the time of the login attempt.
		mName = mNameView.getText().toString();
		mDesciption = mDescriptionView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// TODO validation

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mRoomStatusMessageView.setText(R.string.room_progress);
			showProgress(true);

			//TODO asynch task save room
		}
	}


	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mRoomStatusView.setVisibility(View.VISIBLE);
			mRoomStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mRoomStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mRoomStatusView.setVisibility(View.VISIBLE);
			mRoomStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mRoomStatusView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mRoomStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mRoomFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

}
