package org.ydle.push_notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.ydle.R;
import org.ydle.ui.DashboardActivity;

public class GCMIntentService extends IntentService {

    private static final String CLEAR_NOTIFICATION = "org.ydle.push_notification.CLEAR_NOTIFICATIONS";
    private static final String SHOW_NOTIFICATION = "org.ydle.push_notification.SHOW_NOTIFICATION";

    private static final int NOTIFICATION_ID = 1;

    private static final String TAG = "GCMIntentService";
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mNotificationBuilder;

    public GCMIntentService() {
        super(TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent)
    {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) // has effect of unparcelling Bundle
        {
	    /*
	     * Filter messages based on message type. Since it is likely that
	     * GCM will be extended in the future with new message types, just
	     * ignore any message types you're not interested in, or that you
	     * don't recognize.
	     */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                Log.d(TAG, "Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                Log.d(TAG, "Deleted messages on server: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // Post notification of received message.
                String title = ((intent.getExtras() == null) ? "" : intent.getExtras()
                        .getString("title"));
                String message = ((intent.getExtras() == null) ? "" : intent.getExtras()
                        .getString("message"));
                if (intent.getExtras() != null && CLEAR_NOTIFICATION.equals(intent.getExtras().getString("type"))) {
                    clearNotification();
                }  else {
                    sendNotification(title, message, extras);
                }
                Log.i(TAG, "Received: " + message);
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }

    /**
     * Put the message into a notification and post it.
     * This is just one simple example of what you might choose to do with
     * a GCM message.
     * @param msg
     * @param extras
     */
    private void sendNotification(String title, String msg, Bundle extras)
    {
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, DashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // If this is a notification type message include the data from the message
        // with the intent
        if (extras != null)
        {
            intent.putExtras(extras);
            intent.setAction(SHOW_NOTIFICATION);
        }
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mNotificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                        .setContentText(msg)
                        .setTicker(msg)
                        .setAutoCancel(true)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        mNotificationBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
    }

    /**
     * Remove the app's notification
     */
    private void clearNotification()
    {
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(NOTIFICATION_ID);
    }

}