package com.example.hautran.myapplication.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;


public class FontHelper {

    private static Hashtable<String, Typeface> sFontCache = new Hashtable<String, Typeface>();

    public static Typeface get(final Context context,
                               final String name) {
        Typeface tf = sFontCache.get(name);
        if (tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(),
                                              name);
            } catch (final Exception e) {
                return null;
            }
            sFontCache.put(name,
                           tf);
        }
        return tf;
    }
}