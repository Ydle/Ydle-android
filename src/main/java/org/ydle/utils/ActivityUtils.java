package org.ydle.utils;

import org.ydle.R;
import org.ydle.activity.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	
	
	public static final int NOTIFICATION_ID = 42;
	public static void createNotification(Context context, String message) {
		final NotificationManager mNotification = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);

		final Intent launchNotifiactionIntent = new Intent(context,
				MainActivity.class);
		final PendingIntent pendingIntent = PendingIntent.getActivity(context,
				0, launchNotifiactionIntent, PendingIntent.FLAG_CANCEL_CURRENT);

		Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ydle);

		Notification notification = new Notification.Builder(context)
				.setContentIntent(pendingIntent).setContentTitle("Ydle")
				.setLargeIcon(bm).setContentText(message)
				.setWhen(System.currentTimeMillis()).setAutoCancel(true)
				.setSmallIcon(R.drawable.goutte).getNotification();

		mNotification.notify(NOTIFICATION_ID, notification);
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
