package org.ydle.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by Jean-Baptiste on 28/01/14.
 */
public class DisplayUtils {

    private static final String LOG_TAG = DisplayUtils.class.getSimpleName();

    private static ProgressDialog mProgressDialog;

    /**
     * This method display a Toast message
     *
     * @param context Context to display the message
     * @param message the message to display
     */
    public static void displayToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showProgressDialog(Context context, String message) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public static void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public static void goToUrl(Context context, String url) {
        Intent browse = new Intent("android.intent.action.VIEW", Uri.parse(url));
        context.startActivity(browse);
    }
}
