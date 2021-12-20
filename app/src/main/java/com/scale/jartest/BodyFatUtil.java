package com.scale.jartest;

import android.content.Context;

public class BodyFatUtil {
    public static String getObesityLevel(Context context, int code) {
        switch (code) {
            case 0:
                return context.getString(R.string.words_obesity_0);
            case 1:
                return context.getString(R.string.words_obesity_1);
            case 2:
                return context.getString(R.string.words_obesity_2);
            case 3:
                return context.getString(R.string.words_obesity_3);
            case 4:
                return context.getString(R.string.words_obesity_4);
        }
        return "";
    }

    public static String getHealthLevel(Context context, int code) {
        switch (code) {
            case 1:
                return context.getString(R.string.words_thinner);
            case 2:
                return context.getString(R.string.words_standard);
            case 3:
                return context.getString(R.string.words_overweight);
            case 4:
                return context.getString(R.string.words_obesity);
        }
        return "";
    }

    public static String getBodyType(Context context, int code) {
        switch (code) {
            case 1:
                return context.getString(R.string.words_body_type1);
            case 2:
                return context.getString(R.string.words_body_type2);
            case 3:
                return context.getString(R.string.words_body_type3);
            case 4:
                return context.getString(R.string.words_body_type4);
            case 5:
                return context.getString(R.string.words_body_type5);
            case 6:
                return context.getString(R.string.words_body_type6);
            case 7:
                return context.getString(R.string.words_body_type7);
            case 8:
                return context.getString(R.string.words_body_type8);
            case 9:
                return context.getString(R.string.words_body_type9);
            case 10:
                return context.getString(R.string.words_body_type10);

        }
        return "";
    }

    public static String getImpedanceStatus(Context context, int code) {
        switch (code) {
            case 1:
                return context.getString(R.string.words_impedance_1);
            case 2:
                return context.getString(R.string.words_impedance_2);
            case 3:
                return context.getString(R.string.words_impedance_3);
        }
        return context.getString(R.string.words_impedance_4);
    }
}