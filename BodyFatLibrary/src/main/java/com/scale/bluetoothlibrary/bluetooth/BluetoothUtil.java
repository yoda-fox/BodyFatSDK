package com.scale.bluetoothlibrary.bluetooth;

import com.scale.bluetoothlibrary.util.StringUtil;

import java.text.DecimalFormat;

public class BluetoothUtil {
    public static final String COMMON = "10 FF";
    public static final String TM = "02 01 04 04 09";

    public static DeviceConfig parsingData(byte[] scanRecord) {
        if (isProtocol(scanRecord)) {
            String data = StringUtil.bytes2HexString(scanRecord);
            float decimal;
            int digits, impedance;
            double w, weight;
            if (data.startsWith(TM)) {
                decimal = getDecimal(scanRecord[18], 5, 7);//Number of decimal places reserved
                digits = getDigits(scanRecord[18], 5, 7);
                impedance = (scanRecord[22] & 0xff) * 256 + (scanRecord[23] & 0xff);
                w = (((scanRecord[20] & 0xff) * 256 + (scanRecord[21] & 0xff))) / decimal;
                weight = setDecimal(getUnitWeight(scanRecord[18], 3, 5, w), digits);
            } else {
                decimal = getDecimal(scanRecord[10], 5, 7);//Number of decimal places reserved
                digits = getDigits(scanRecord[10], 5, 7);
                impedance = (scanRecord[6] & 0xff) * 256 + (scanRecord[7] & 0xff);
                w = (((scanRecord[4] & 0xff) * 256 + (scanRecord[5] & 0xff))) / decimal;
                weight = setDecimal(getUnitWeight(scanRecord[10], 3, 5, w), digits);
            }
            DeviceConfig deviceConfig = new DeviceConfig();
            deviceConfig.setWeight(weight);
            deviceConfig.setImpedance(impedance);
            return deviceConfig;
        }
        return null;
    }

    public static boolean isProtocol(byte[] scanRecord) {
        if (StringUtil.bytes2HexString(scanRecord).startsWith(COMMON) && scanRecord.length >= 11 && StringUtil.getBit(scanRecord[10], 0) == 1) {
            return true;
        } else
            return StringUtil.bytes2HexString(scanRecord).startsWith(TM) && scanRecord.length >= 23 && StringUtil.getBit(scanRecord[18], 0) == 1;
    }

    public static float getDecimal(byte b, int start, int end) {
        switch (StringUtil.getTwoBit(b, start, end)) {
            case "00":
                return 10f;
            case "10":
                return 100f;
            default:
                return 1f;
        }
    }

    public static int getDigits(byte b, int start, int end) {
        switch (StringUtil.getTwoBit(b, start, end)) {
            case "00":
                return 1;
            case "10":
                return 2;
            default:
                return 0;
        }
    }

    public static double setDecimal(double d, int n) {
        String type;
        switch (n) {
            case 1:
                type = "0.0";
                break;
            case 2:
                type = "0.00";
                break;
            default:
                type = "0";
                break;
        }
        DecimalFormat decimalFormat = new DecimalFormat(type);
        return Double.parseDouble(decimalFormat.format(d));
    }

    public static double getUnitWeight(byte b, int start, int end, double weight) {
        switch (StringUtil.getTwoBit(b, start, end)) {
            case "01":
                return weight / 2f;
            case "10":
                return weight * 0.4535924;
            case "11":
                int st = (int) weight;
                double lb = weight - st;
                return st * 6.3503 + lb * 0.4536;
            default:
                return weight;
        }
    }
}