package com.example.hautran.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;


public class Utility {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * function hide key board
     *
     * @param mActivity
     */
    public static void hideInputKeyboard(Activity mActivity) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (mActivity.getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
    }


    /**
     * function check validation for phone number
     *
     * @param phone
     * @return
     */
    public static boolean isValidMobile(String phone) {
        if (phone.length() < 10 || phone.length()>16)
            return false;
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    /**
     * Set view or sub views with custom font.
     *
     * @param rootView     The root view.
     * @param fontFileName The full path of custom font in asset folder.
     * @since v0.1
     */
    public static void setFontForViewRecursive(final View rootView,
                                               final String fontFileName) {
        if (rootView instanceof ViewGroup) {
            // Let's set sub views fonts.
            final ViewGroup viewGroup = (ViewGroup) rootView;
            final int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                Utility.setFontForViewRecursive(viewGroup.getChildAt(i),
                        fontFileName);
            }
        } else if (rootView instanceof TextView) {
            Utility.setFontForTextView((TextView) rootView,
                    fontFileName);
        }
    }

    /**
     * Set custom font for text view.
     *
     * @param textView     The text view which uses custom fonts.
     * @param fontFileName The full path of custom font in asset folder.
     * @since v0.1
     */
    public static void setFontForTextView(final TextView textView,
                                          final String fontFileName) {
        // Check input data.
        if ((textView == null) || TextUtils.isEmpty(fontFileName)) {
            return;
        }

        // Check working context.
        if (textView.getContext() == null) {
            return;
        }

        // Set custom font.
        textView.setTypeface(FontHelper.get(textView.getContext(),
                fontFileName),
                Typeface.NORMAL);
    }

    /**
     * function check password has contain bad word
     *
     * @param password
     * @return
     */
    private static boolean isContainInvalidWord(String password) {
        String[] words = {"`", "'", "\"", ":", ";", "~"};
        for (int i = 0; i < words.length; i++) {
            if (password.contains(words[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * @author LocPB returning image file
     */
    public static File getOutputMediaFile(Context mContext) {

        // External sdcard location
        File mediaStorageDir = new File(mContext.getExternalFilesDir(null).getAbsolutePath());

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    /**
     * LocPB get real path of file from uri
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getPathFromUri(Context context, Uri uri) {
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null,
                    null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            return picturePath;
        } catch (Exception e) {
            return uri.getPath();
        }
    }

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

//    /**
//     * round float value to 2 decimal
//     * @param value
//     * @return
//     */
//    public static float roundFloat2Decimal(float value){
//        return(float) ((float) Math.round(value * 100.0) / 100.0);
//    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }


    public static String perfectDecimal(String str, int MAX_BEFORE_POINT, int MAX_DECIMAL){
        if(str.charAt(0) == '.') str = "0"+str;
        int max = str.length();

        String rFinal = "";
        boolean after = false;
        int i = 0, up = 0, decimal = 0; char t;
        while(i < max){
            t = str.charAt(i);
            if(t != '.' && after == false){
                up++;
                if(up > MAX_BEFORE_POINT) return rFinal;
            }else if(t == '.'){
                after = true;
            }else{
                decimal++;
                if(decimal > MAX_DECIMAL)
                    return rFinal;
            }
            rFinal = rFinal + t;
            i++;
        }return rFinal;
    }


}
