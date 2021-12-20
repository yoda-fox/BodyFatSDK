package com.scale.bluetoothlibrary.util;

import java.util.Locale;

public class StringUtil {
    /**
     * Byte array to hexadecimal string
     */
    public static String bytes2HexString(byte[] b) {
        StringBuilder r = new StringBuilder();
        for (byte value : b) {
            String hex = Integer.toHexString(value & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            if (r.length() > 1) r.append(" ");
            r.append(hex.toUpperCase());
        }

        return r.toString();
    }


    public static int getBit(byte b, int i) {
        return ((b >> i) & 0x01);
    }

    /**
     * byte to binary character format
     */
    public static String getTwoBit(byte b, int i, int j) {
        String binary = Integer.toBinaryString(b & 0xff);
        String s = "00000000".substring(0, 8 - binary.length()) + binary;
        return s.substring(i, j);
    }

    public static String getSystemLanguage() {
        Locale locale = Locale.getDefault();
        return locale.getLanguage() + "_" + locale.getCountry();
    }
}
