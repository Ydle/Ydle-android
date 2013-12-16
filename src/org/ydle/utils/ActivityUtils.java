package org.ydle.utils;

import java.io.IOException;
import java.util.List;

import org.ydle.dummy.DummyContent;
import org.ydle.model.configuration.Configuration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;

public class ActivityUtils {

	public static boolean isNetworkAvailable(Activity activity) {
		ConnectivityManager cm = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	public static boolean iswifiEnable(Activity activity) {
		WifiManager wifi = (WifiManager) activity
				.getSystemService(Context.WIFI_SERVICE);
		return wifi.isWifiEnabled();
	}

	

	public static AlertDialog showDownloadDialog(final Activity activity,
			CharSequence stringTitle, CharSequence stringMessage,
			CharSequence stringButtonYes, CharSequence stringButtonNo,
			final String packageApp) {
		AlertDialog.Builder downloadDialog = new AlertDialog.Builder(activity);
		downloadDialog.setTitle(stringTitle);
		downloadDialog.setMessage(stringMessage);
		downloadDialog.setPositiveButton(stringButtonYes,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int i) {
						Uri uri = Uri.parse("market://search?q=pname:"
								+ packageApp);
						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						activity.startActivity(intent);
					}
				});
		downloadDialog.setNegativeButton(stringButtonNo,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int i) {
					}
				});
		return downloadDialog.show();
	}

	public static AlertDialog showFisrtStartdDialog(final Activity activity,
			CharSequence stringTitle, CharSequence stringMessage,
			CharSequence stringButtonYes, CharSequence stringButtonNo,
			final Intent intent) {
		AlertDialog.Builder downloadDialog = new AlertDialog.Builder(activity);
		downloadDialog.setTitle(stringTitle);
		downloadDialog.setMessage(stringMessage);
		downloadDialog.setPositiveButton(stringButtonYes,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int i) {
						activity.startActivity(intent);
					}
				});
		downloadDialog.setNegativeButton(stringButtonNo,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int i) {
					}
				});
		return downloadDialog.show();
	}
}
