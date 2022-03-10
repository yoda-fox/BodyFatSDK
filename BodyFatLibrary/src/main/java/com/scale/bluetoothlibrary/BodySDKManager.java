package com.scale.bluetoothlibrary;

import android.content.Context;

import com.scale.bluetoothlibrary.bluetooth.BluetoothUtil;
import com.scale.bluetoothlibrary.bluetooth.DeviceConfig;
import com.scale.bluetoothlibrary.bluetooth.ScanUtil;
import com.scale.bluetoothlibrary.listener.OnConfigListener;
import com.scale.bluetoothlibrary.listener.OnStatusListener;
import com.scale.bluetoothlibrary.util.Constants;
import com.scale.bluetoothlibrary.util.HttpUtil;
import com.scale.bluetoothlibrary.util.PUtil;

import java.util.HashMap;
import java.util.Map;

public class BodySDKManager {
    private static BodySDKManager bodySDKManager;

    public static BodySDKManager getInstance() {
        if (bodySDKManager == null) {
            bodySDKManager = new BodySDKManager();
        }
        return bodySDKManager;
    }

    /**
     * get access_token
     */
    public void init(Context context, String appid, String secret, OnStatusListener onStatusListener) {
        PUtil.init(context);
        HttpUtil.getInstance().setStatusListener(onStatusListener);
        HttpUtil.getInstance().getAccessToken(appid, secret);
    }

    /**
     * get access_token
     */
    public void init(Context context, String appid, String secret, ScanUtil.BluetoothSearchListener bluetoothSearchListener, OnStatusListener onStatusListener) {
        PUtil.init(context);
        ScanUtil.getInstance().init(context);
        ScanUtil.getInstance().registerBluetoothListener(bluetoothSearchListener);
        HttpUtil.getInstance().setStatusListener(onStatusListener);
        HttpUtil.getInstance().getAccessToken(appid, secret);
    }

    /**
     * get body data
     */
    public void getBodyParameter(Map<String, Object> params, OnConfigListener onConfigListener) {
        byte[] scanRecord = (byte[]) params.get(Constants.SCAN_RECORD);
        HttpUtil.getInstance().setConfigListener(onConfigListener);
        DeviceConfig deviceConfig = BluetoothUtil.parsingData(scanRecord);
        if (deviceConfig != null) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put(Constants.LOGIN_ACCOUNT, params.get(Constants.LOGIN_ACCOUNT));
            paramMap.put(Constants.THIRD_USERNO, params.get(Constants.THIRD_USERNO));
            paramMap.put(Constants.THIRD_NICKNAME, params.get(Constants.THIRD_NICKNAME));
            paramMap.put(Constants.HEIGHT, params.get(Constants.HEIGHT));
            paramMap.put(Constants.AGE, params.get(Constants.AGE));
            paramMap.put(Constants.SEX, params.get(Constants.SEX));
            paramMap.put(Constants.SCALE_TYPE, params.get(Constants.SCALE_TYPE));
            paramMap.put(Constants.IMPEDANCE, deviceConfig.getImpedance());
            paramMap.put(Constants.WEIGHT, deviceConfig.getWeight());
            HttpUtil.getInstance().getBodyParameter(paramMap);
        }
    }
}