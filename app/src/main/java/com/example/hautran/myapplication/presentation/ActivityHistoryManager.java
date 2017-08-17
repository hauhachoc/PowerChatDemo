package com.example.hautran.myapplication.presentation;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hautran on 16/08/17.
 */

public class ActivityHistoryManager {
    private final static String TAG = ActivityHistoryManager.class.getSimpleName();
    private final static int ACTIVITY_QUEUE_SIZE = 20;
    private final static long ACTIVITY_TIMEOUT = TimeUnit.SECONDS.toMillis(60 * 60 * 6);

    // public static CustomBlockingDeque<Activity> ActivityHistory = new
    // CustomBlockingDeque <Activity>();
    public static List<Activity> ActivityHistory = new ArrayList<Activity>();
    private static long lastModificationTime = 0;

    /**
     * get size of list activity was created
     *
     * @return
     */
    public static int getSize() {
        return ActivityHistory.size();
    }

    /**
     * function add new activity to history
     *
     * @param newActivity
     */
    public static synchronized void addNewActivity(Activity newActivity) {

        synchronized (ActivityHistory) {
            if (ACTIVITY_QUEUE_SIZE < ActivityHistory.size()) {
                Activity oldestActivity = ActivityHistory.remove(0);
                oldestActivity.finish();
            }
        }
        ActivityHistory.add(newActivity);
        lastModificationTime = System.currentTimeMillis();
    }

    /**
     * function remove one activity out of history
     *
     * @param removeActivity
     */
    public static synchronized void removeFromActivityHistory(Activity removeActivity) {
        synchronized (ActivityHistory) {
            if (ActivityHistory.contains(removeActivity)) {
                ActivityHistory.remove(removeActivity);
            }
        }
    }

    /**
     * function remove activity with specific name out of history
     *
     * @param removeActivity
     */
    public static synchronized void removeFromActivityHistory(String removeActivity) {
        synchronized (ActivityHistory) {
            for (int i = 0; i < ActivityHistory.size(); i++) {
                if (ActivityHistory.get(i).getClass().getSimpleName().equals(removeActivity))
                    ActivityHistory.remove(i).finish();
            }
        }
    }

    /**
     * function clear all activity from history
     */
    public static void shutdownAllActivity() {
        for (Activity activity : ActivityHistory) {
            activity.finish();
        }
        ActivityHistory.clear();
        lastModificationTime = System.currentTimeMillis();
    }

    /**
     * function clear all activity before current activity in history.
     */
    public static void shutdownAllActivityBefore() {
        synchronized (ActivityHistory) {
            while (ActivityHistory.size() > 1) {
                Activity acrm = ActivityHistory.remove(0);
                acrm.finish();
            }
        }
        lastModificationTime = System.currentTimeMillis();
    }

    /**
     * function get last activity from history
     *
     * @return
     */
    public static Activity peekLast() {
        synchronized (ActivityHistory) {
            int size = ActivityHistory.size();
            if (size > 0) {
                return ActivityHistory.get(size - 1);
            }
        }
        return null;
    }

    /**
     * function check long time to operation with history
     *
     * @return
     */
    public static boolean confirmNoLongerOperation() {

        long duration = System.currentTimeMillis() - lastModificationTime;
        if (duration > ACTIVITY_TIMEOUT) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * function check true if history is empty
     *
     * @return
     */
    public static boolean isEmptyActivityHistory() {
        return null == ActivityHistory || ActivityHistory.isEmpty();
    }

    /**
     * function check true if history has only one activity
     *
     * @return
     */
    public static boolean isLastActivityHistory() {
        return null != ActivityHistory && ActivityHistory.size() <= 1;
    }

    /**
     * function check an activity exist in history
     *
     * @param activity
     * @return
     */
    public static boolean checkActivityExist(Class<?> activity) {
        int size = ActivityHistory.size();
        for (int i = size - 1; i >= 0; i--) {
            Activity ac = ActivityHistory.get(i);
            if (activity.isAssignableFrom(ac.getClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * function back from current to a specific activity
     *
     * @param activity
     */
    public static void backToActivity(Class<?> activity) {
        for (int i = ActivityHistory.size() - 1; i >= 1; i--) {
            Activity ac = ActivityHistory.get(i);
            if (!activity.isAssignableFrom(ac.getClass()))
                ac.finish();
            else
                return;
        }
    }
}
