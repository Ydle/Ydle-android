package org.ydle.data.model;

/**
 * Created by Jean-Baptiste on 06/02/14.
 */
public class Log {

    private static final String LOG_TAG = Log.class.getSimpleName();

    private LogLevel mLevel;
    private String mMessage;

    public Log(LogLevel level, String message) {
        mLevel = level;
        mMessage = message;
    }

    public LogLevel getLevel() {
        return mLevel;
    }

    public void setLevel(LogLevel level) {
        this.mLevel = level;
    }

    public void setLevel(int level) {
        this.mLevel = LogLevel.getFromValue(level);
    }

    public void setLevel(String level) {
        this.mLevel = LogLevel.getFromLabel(level);
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public enum LogLevel {

        LEVEL_1(1, "Level 1"),
        LEVEL_2(2, "Level 2"),
        LEVEL_3(3, "Level 3"),
        LEVEL_4(4, "Level 4"),
        LEVEL_5(5, "Level 5");

        private static final String LOG_TAG = LogLevel.class.getSimpleName();

        private int mValue;
        private String mLabel;

        private LogLevel(int value, String label) {
            mValue = value;
            mLabel = label;
        }

        public int getValue() {
            return mValue;
        }

        public String getLabel() {
            return mLabel;
        }

        public static LogLevel getFromLabel(String label) {

            for (LogLevel value : values()) {
                if (value.mLabel.equals(label)) {
                    return value;
                }
            }
            return LogLevel.LEVEL_1;
        }

        public static LogLevel getFromValue(int value) {

            for (LogLevel log : values()) {
                if (log.mValue == value) {
                    return log;
                }
            }
            return LogLevel.LEVEL_1;
        }
    }
}
