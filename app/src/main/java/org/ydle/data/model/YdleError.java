package org.ydle.data.model;

/**
 * Created by Jean-Baptiste on 01/02/14.
 */
public class YdleError {

    private static final String LOG_TAG = Sensor.class.getSimpleName();

    private boolean mError = false;
    private int mNumber = 0;
    private String mMessage = "";

    public YdleError(boolean error, int number, String message) {
        this.mError = error;
        this.mNumber = number;
        this.mMessage = message;
    }

    public boolean isError() {
        return mError;
    }

    public void setError(boolean error) {
        this.mError = error;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        this.mNumber = number;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }
}
