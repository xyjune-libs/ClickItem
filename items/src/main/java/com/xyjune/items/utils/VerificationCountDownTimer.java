package com.xyjune.items.utils;

import android.os.CountDownTimer;

public class VerificationCountDownTimer extends CountDownTimer {

    private static final String TAG = "MsgCodeEditItem";

    private static long curMillis = 0L;
    private static long lastMillisInFuture = 0L;
    private boolean isRunning;

    public VerificationCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        lastMillisInFuture = millisInFuture;
    }

    public void onTick(long millisUntilFinished) {
    }

    public boolean isRunning() {
        return isRunning;
    }

    public static long getCurMillis() {
        return curMillis;
    }

    public static void setCurMillis(long curMillis) {
        VerificationCountDownTimer.curMillis = curMillis;
    }

    public static long getLastMillisInFuture() {
        return lastMillisInFuture;
    }

    public static long getEndTime() {
        return getCurMillis() + getLastMillisInFuture();
    }

    public static boolean isTimeEnd() {
        return getEndTime() < System.currentTimeMillis();
    }

    public void onFinish() {
        isRunning = false;
    }

    public void timerStart() {
        curMillis = System.currentTimeMillis();
        isRunning = true;
        this.start();
    }
}