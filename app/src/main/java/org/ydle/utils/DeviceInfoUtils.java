package org.ydle.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Jean-Baptiste on 28/01/14.
 */
public class DeviceInfoUtils {

    private static final String LOG_TAG = DeviceInfoUtils.class.getSimpleName();

    /**
     * This method give device width in pixels.
     *
     * @param context Context to get width
     * @return A int value to represent the width in pixels
     */
    public static int getWidthScreenPx(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels < dm.heightPixels ? dm.widthPixels : dm.heightPixels;
    }


    /**
     * This method give device width in Dp.
     *
     * @param context Context to get width
     * @return A int value to represent the width in Dp
     */
    public static int getWidthScreenDp(Context context) {
        return (int) UiUtils.convertPixelsToDp(getWidthScreenPx(context), context);
    }

    /**
     * This method give device height in pixels.
     *
     * @param context Context to get height
     * @return A int value to represent the height in pixels
     */
    public static int getHeightScreenPx(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels < dm.heightPixels ? dm.heightPixels : dm.widthPixels;
    }


    /**
     * This method give device height in Dp.
     *
     * @param context Context to get height
     * @return A int value to represent the height in Dp
     */
    public static int getHeightScreenDp(Context context) {
        return (int) UiUtils.convertPixelsToDp(getHeightScreenPx(context), context);
    }
}
