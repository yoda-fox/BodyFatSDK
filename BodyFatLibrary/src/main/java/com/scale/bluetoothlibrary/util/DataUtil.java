package com.scale.bluetoothlibrary.util;

public class DataUtil {
    public static float getDecimal(String s) {
        switch (s) {
            case "00":
                return 10f;
            case "10":
                return 100f;
            default:
                return 1f;
        }
    }

    public static double getWeight(byte[] scanRecord, float decimal) {
        switch (StringUtil.getTwoBit(scanRecord[10], 3, 5)) {
            case "01"://斤
                //  unit = "斤";
                return (((scanRecord[4] & 0xff) * 256 + (scanRecord[5] & 0xff)) / decimal) / 2f;
            case "10"://lb
                //   unit = "lb";
                return (((scanRecord[4] & 0xff) * 256 + (scanRecord[5] & 0xff)) / decimal) * 0.4536;
            case "11"://st:lb
                // unit = "st:lb";
                int st = (int) (((scanRecord[4] & 0xff) * 256 + (scanRecord[5] & 0xff)) / decimal);
                float lb = ((scanRecord[4] & 0xff) * 256 + (scanRecord[5] & 0xff)) / decimal - st;
                return st * 6.3503 + lb * 0.4536;
            default://kg
                // unit = "kg";
                return ((scanRecord[4] & 0xff) * 256 + (scanRecord[5] & 0xff)) / decimal;
        }
    }
}
