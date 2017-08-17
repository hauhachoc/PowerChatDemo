package com.example.hautran.myapplication.utils;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;


import com.example.hautran.myapplication.BuildConfig;

import java.util.Date;


public class ShowLog {

    private static final String TAG_PREFIX = "ActivityPage == ";

    /**
     * cShowLog Flag (Using turn on or turn off debug)
     */
    private static final boolean IS_FLAG_DEBUG = BuildConfig.DEBUG;
    private static final boolean IS_FLAG_ERROR = BuildConfig.DEBUG;
    private static final boolean IS_FLAG_INFO = BuildConfig.DEBUG;
    private static final boolean IS_FLAG_VERBOSE = BuildConfig.DEBUG;
    private static final boolean IS_FLAG_WARN = BuildConfig.DEBUG;
    private static final boolean IS_FLAG_OS = BuildConfig.DEBUG;
    private static final boolean IS_FLAG_TOAST = BuildConfig.DEBUG;

    /**
     * Contact value is 3
     * Show Log Debug. Data note flow page.
     *
     * @param tag     Prefix
     * @param message Debug error
     */
    public static void showLogDebug(final String tag, final String message) {
        if (IS_FLAG_DEBUG) {
            Log.d(TAG_PREFIX + tag, message);
        }
    }

    /**
     * Contact value is 6
     * Show Log Error. Data very important, use to start to end block or end point to redirect page
     *
     * @param tag     Prefix
     * @param message Debug error
     */
    public static void showLogError(final String tag, final String message) {
        if (IS_FLAG_ERROR) {
            Log.e(TAG_PREFIX + tag, message);
        }
    }

    /**
     * Contact value is 4
     * Show Log Info. Data show result
     *
     * @param tag     Prefix
     * @param message Debug error
     */
    public static void showLogInfo(final String tag, final String message) {
        if (IS_FLAG_INFO) {
            Log.i(TAG_PREFIX + tag, message);
        }
    }

    /**
     * Contact value is 2
     * Show Log Verbose. Data show result
     *
     * @param tag     Prefix
     * @param message Debug error
     */
    public static void showLogVerbose(final String tag, final String message) {
        if (IS_FLAG_VERBOSE) {
            Log.i(TAG_PREFIX + tag, message);
        }
    }

    /**
     * Contact value is 2
     * Show Log Verbose. Data show result
     *
     * @param tag     Prefix
     * @param message Debug error
     */
    public static void v(final String tag, final String message) {
        if (IS_FLAG_VERBOSE) {
            Log.v(TAG_PREFIX + tag, message);
        }
    }

    /**
     * Contact value is 5
     * Show Log Warn. Data show result
     *
     * @param tag     Prefix
     * @param message Debug error
     */
    public static void showLogWarn(final String tag, final String message) {
        if (IS_FLAG_WARN) {
            Log.i(TAG_PREFIX + tag, message);
        }
    }

    /**
     * Show Log Current Time
     *
     * @param tag     Prefix
     * @param message Message add more
     */
    public static void showLogTime(final String tag, final String message) {
        //System.currentTimeMillis
        if (IS_FLAG_OS) {
            final Date date = new Date();
            final String current = date.getMinutes() + ":" + date.getSeconds();
            Log.e(TAG_PREFIX + tag, message + " :: Current time now: " + current + " (mm:ss)");
        }
    }

    /**
     * Show Toast
     *
     * @param context Context of activity
     * @param message Message show toast
     */
    public static void showToast(final Context context, final String message) {
        if (IS_FLAG_TOAST) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Show memory error
     *
     * @param tag
     * @param message Message show
     */
    public static void showLogMemory(final String tag, final String message) {
        if (IS_FLAG_OS) {
            final long allSize = Debug.getNativeHeapAllocatedSize() / 1048576L; // Origin is BYTES, print out is KB //1024
            final long freeSize = Debug.getNativeHeapFreeSize() / 1048576L;
            final long size = Debug.getNativeHeapSize() / 1048576L;
            final long maxMem = Runtime.getRuntime().maxMemory() / 1048576L; // Max memory
            //Usage
            Log.e(TAG_PREFIX + tag, message + ":: Max Memory(" + maxMem + " ) Allocation(" + allSize + " )" + " Free(" + freeSize + " )" + " Usage(" + size + " )");
        }
    }
}
