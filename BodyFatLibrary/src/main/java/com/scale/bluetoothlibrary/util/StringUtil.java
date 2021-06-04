package com.scale.bluetoothlibrary.util;

public class StringUtil {
    /*
     * 字节数组转16进制字符串
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

    public static String getTwoBit(byte b, int i, int j) {
        String binary = Integer.toBinaryString(b & 0xff);//转换为2进制字符串
        switch (binary.length()) {
            case 1:
                binary = "0000000" + binary;
                break;
            case 2:
                binary = "000000" + binary;
                break;
            case 3:
                binary = "00000" + binary;
                break;
            case 4:
                binary = "0000" + binary;
                break;
            case 5:
                binary = "000" + binary;
                break;
            case 6:
                binary = "00" + binary;
                break;
            case 7:
                binary = "0" + binary;
                break;
        }
        return binary.substring(i, j);
    }

    public static boolean  matcheMac(String s){
        String regex="^[A-Fa-f0-9]+$";
        return s.matches(regex);
    }
}
