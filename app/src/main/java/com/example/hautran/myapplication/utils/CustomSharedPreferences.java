package com.example.hautran.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class CustomSharedPreferences {

    private static SharedPreferences sharedPre;
    private static SharedPreferences.Editor editor;
    private static Context appContext;

    public static final String USER_TAG = "user_tag";
    public static final String ALL_RESTRICTION = "all_restriction";
    public static final String GOALS = "goals";
    public static final String MY_GOALS = "my_goals";
    public static final String ACCOUNT = "account";
    public static final String MEAL_DATE = "meal_date";

    /**
     * Constructor. Please create new object before using setter and getter
     *
     * @param context Input from Activity
     */
    public static void init(final Context context) {
        appContext = context;
        if (null != context) {
            sharedPre = context.getSharedPreferences("custom_sharepref", 0);
            editor = sharedPre.edit();
        } else {
            editor = null;
            sharedPre = null;
        }
    }

    private static void refresh() {
        if (null != appContext) {
            sharedPre = appContext.getSharedPreferences("custom_sharepref", 0);
            editor = sharedPre.edit();
        } else {
            editor = null;
            sharedPre = null;
        }
    }

    /**
     * Set data for String
     *
     * @param preName Preferences name
     * @param value   String input
     */
    public synchronized static void setPreferences(final String preName, final String value) {
        refresh();
        if (null != editor) {
            editor.putString(preName, value);
            editor.commit();
        }
    }

    /**
     * Get data for String
     *
     * @param preName      Preferences name
     * @param defaultValue
     * @return String or 0 if Name not existed
     */
    public static String getPreferences(final String preName, final String defaultValue) {
        refresh();
        if (null != sharedPre) {
            return sharedPre.getString(preName, defaultValue);
        } else {
            return null;
        }
    }

    /**
     * Set data for boolean
     *
     * @param preName Preferences name
     * @param value   boolean input
     */
    public synchronized static void setPreferences(final String preName, final boolean value) {
        if (null != editor) {
            editor.putBoolean(preName, value);
            editor.commit();
        }
    }

    /**
     * Get data for boolean
     *
     * @param preName      Preferences name
     * @param defaultValue
     * @return boolean or 0 if Name not existed
     */
    public static boolean getPreferences(final String preName, final boolean defaultValue) {
        if (null != sharedPre) {
            return sharedPre.getBoolean(preName, defaultValue);
        } else {
            return false;
        }
    }

    /**
     * Set data for Integer
     *
     * @param preName Preferences name
     * @param value   Integer input
     */
    public synchronized static void setPreferences(final String preName, final int value) {
        if (null != editor) {
            editor.putInt(preName, value);
            editor.commit();
        }
    }

    /**
     * Get data for Integer
     *
     * @param preName      Preferences name
     * @param defaultValue
     * @return Integer or 0 if Name not existed
     */
    public static int getPreferences(final String preName, final int defaultValue) {
        if (null != sharedPre) {
            return sharedPre.getInt(preName, defaultValue);
        } else {
            return 0;
        }
    }

    /**
     * Set data for Long
     *
     * @param preName Preferences name
     * @param value   Long input
     */
    public synchronized static void setPreferences(final String preName, final long value) {
        if (null != editor) {
            editor.putLong(preName, value);
            editor.commit();
        }
    }

    /**
     * Get data for Long
     *
     * @param preName      Preferences name
     * @param defaultValue
     * @return Long or 0 if Name not existed
     */
    public static long getPreferences(final String preName, final long defaultValue) {
        if (null != sharedPre) {
            return sharedPre.getLong(preName, defaultValue);
        } else {
            return 0;
        }
    }

    /**
     * Set data for Float
     *
     * @param preName Preferences name
     * @param value   Float input
     */
    public synchronized static void setPreferences(final String preName, final float value) {
        if (null != editor) {
            editor.putFloat(preName, value);
            editor.commit();
        }
    }

    /**
     * Get data for Float
     *
     * @param preName      Preferences name
     * @param defaultValue
     * @return Float or 0 if Name not existed
     */
    public static float getPreferences(final String preName, final float defaultValue) {
        if (null != sharedPre) {
            return sharedPre.getFloat(preName, defaultValue);
        } else {
            return 0;
        }

    }

    /**
     * Remove key from Preferences
     *
     * @param preName : key remove
     */
    public synchronized static void removePreferences(final String preName) {
        if (null != editor) {
            editor.remove(preName);
            editor.commit();
        }
    }

//    public static void setUserLogin(cUserResponseModel userLogin) {
//        Gson gson = new Gson();
//        String jsonCurProduct = gson.toJson(userLogin);
//
//        SharedPreferences sharedPref = appContext.getSharedPreferences(USER_TAG, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//
//        editor.putString(ACCOUNT, jsonCurProduct);
//        editor.commit();
//    }
//
//    public static  cUserResponseModel getUserLogin(){
//        Gson gson = new Gson();
//        cUserResponseModel acc = new cUserResponseModel();
//        SharedPreferences sharedPref = appContext.getSharedPreferences(USER_TAG, Context.MODE_PRIVATE);
//        String jsonPreferences = sharedPref.getString(ACCOUNT, "");
//
//        Type type = new TypeToken<cUserResponseModel>() {}.getType();
//        acc = gson.fromJson(jsonPreferences, type);
//
//        return acc;
//    }
//
//    public static void saveRestriction(ArrayList<String> arr){
//         sharedPre = PreferenceManager.getDefaultSharedPreferences(appContext);
//         editor = sharedPre.edit();
//        Gson gson = new Gson();
//
//        String json = gson.toJson(arr);
//
//        editor.putString(ALL_RESTRICTION, json);
//        editor.commit();
//    }
//
//    public static ArrayList<String> getRestriction(){
//         sharedPre = PreferenceManager.getDefaultSharedPreferences(appContext);
//        Gson gson = new Gson();
//        String json = sharedPre.getString(ALL_RESTRICTION, null);
//        Type type = new TypeToken<ArrayList<String>>() {}.getType();
//        ArrayList<String> arrayList = gson.fromJson(json, type);
//        return arrayList;
//    }
//
//    public static void saveArrGoals(ArrayList<cRestrictionGoalUnitDefaultModel> arr){
//        sharedPre = PreferenceManager.getDefaultSharedPreferences(appContext);
//        editor = sharedPre.edit();
//        Gson gson = new Gson();
//
//        String json = gson.toJson(arr);
//
//        editor.putString(GOALS, json);
//        editor.commit();
//    }
//
//    public static ArrayList<cRestrictionGoalUnitDefaultModel> getArrGoals(){
//        sharedPre = PreferenceManager.getDefaultSharedPreferences(appContext);
//        Gson gson = new Gson();
//        String json = sharedPre.getString(GOALS, null);
//        Type type = new TypeToken<ArrayList<cRestrictionGoalUnitDefaultModel>>() {}.getType();
//        ArrayList<cRestrictionGoalUnitDefaultModel> arrayList = gson.fromJson(json, type);
//        return arrayList;
//    }
//
//    public static void saveArrMyGoals(ArrayList<cNutrientDailyModel> arr){
//        sharedPre = PreferenceManager.getDefaultSharedPreferences(appContext);
//        editor = sharedPre.edit();
//        Gson gson = new Gson();
//
//        String json = gson.toJson(arr);
//
//        editor.putString(MY_GOALS, json);
//        editor.commit();
//    }
//
//    public static ArrayList<cNutrientDailyModel> getArrMyGoals(){
//        sharedPre = PreferenceManager.getDefaultSharedPreferences(appContext);
//        Gson gson = new Gson();
//        String json = sharedPre.getString(MY_GOALS, null);
//        Type type = new TypeToken<ArrayList<cNutrientDailyModel>>() {}.getType();
//        ArrayList<cNutrientDailyModel> arrayList = gson.fromJson(json, type);
//        return arrayList;
//    }


}
