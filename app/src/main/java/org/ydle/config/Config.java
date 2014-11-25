package org.ydle.config;

/**
 * Created by Jean-Baptiste on 28/01/14.
 */
public class Config {

    private static final String LOG_TAG = Config.class.getSimpleName();

    public static final String SENDER_ID = "1087106100438";

    /**
     * This method determinates is we use crashlytics bug reporting
     *
     * @return true if we use it
     */
    public static boolean useCrashlytics() {

        return BuildInfo.buildMode == BuildInfo.BuildMode.Release
                || BuildInfo.buildMode == BuildInfo.BuildMode.Test;
    }

    /**
     * This method determinates if we display logs
     *
     * @return true if we use logs
     */
    public static boolean displayLog() {
        return BuildInfo.buildMode != BuildInfo.BuildMode.Release;
    }

}
