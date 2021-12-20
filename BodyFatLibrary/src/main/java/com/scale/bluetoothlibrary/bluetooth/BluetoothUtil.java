package com.scale.bluetoothlibrary.bluetooth;

import com.scale.bluetoothlibrary.util.DataUtil;
import com.scale.bluetoothlibrary.util.StringUtil;

public class BluetoothUtil {
    public static DeviceConfig parsingData(byte[] scanRecord) {
        if (StringUtil.bytes2HexString(scanRecord).startsWith("10 FF")) {
            if (scanRecord.length >= 11 && (StringUtil.getBit(scanRecord[10], 0) == 1)) {
                float decimal = DataUtil.getDecimal(StringUtil.getTwoBit(scanRecord[10], 5, 7));//保留的小数点位数
                double weight = DataUtil.getWeight(scanRecord, decimal);//体重
                int impedance = (scanRecord[6] & 0xff) * 256 + (scanRecord[7] & 0xff);
                DeviceConfig deviceConfig = new DeviceConfig();
                deviceConfig.setWeight(weight);
                deviceConfig.setImpedance(impedance);
                return deviceConfig;
            }
        }
        return null;
    }
}