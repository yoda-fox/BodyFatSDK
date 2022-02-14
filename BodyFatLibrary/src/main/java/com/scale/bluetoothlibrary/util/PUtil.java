package com.scale.bluetoothlibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PUtil {
    private static final String DEFAULT_SHARE_NAME = "com.scale.bluetoothlibrary";
    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(DEFAULT_SHARE_NAME, Context.MODE_PRIVATE);
    }

    public static void put(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, "");
    }
}
