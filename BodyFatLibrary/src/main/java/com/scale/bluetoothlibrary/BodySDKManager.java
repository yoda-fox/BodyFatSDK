package com.scale.bluetoothlibrary;

import android.content.Context;

import com.scale.bluetoothlibrary.bluetooth.BluetoothUtil;
import com.scale.bluetoothlibrary.bluetooth.DeviceConfig;
import com.scale.bluetoothlibrary.listener.OnConfigListener;
import com.scale.bluetoothlibrary.listener.OnStatusListener;
import com.scale.bluetoothlibrary.util.Constants;
import com.scale.bluetoothlibrary.util.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BodySDKManager implements BluetoothUtil.BluetoothSearchListener {
    private static BodySDKManager bodySDKManager;
    private Map<String, Object> params;

    public static BodySDKManager getInstance() {
        if (bodySDKManager == null) {
            bodySDKManager = new BodySDKManager();
        }
        return bodySDKManager;
    }
    /**
     * 获取access_token
     */
    public void init(Context context, String appid, String secret, OnStatusListener onStatusListener) {
        PUtil.init(context);
        BluetoothUtil.getInstance().init(context);
        HttpUtil.getInstance().setStatusListener(onStatusListener);
        HttpUtil.getInstance().getAccessToken(appid, secret);
    }

    public void getBodyParameter(Map<String, Object> params, OnConfigListener onConfigListener) {
        this.params = params;
        HttpUtil.getInstance().setConfigListener(onConfigListener);
        BluetoothUtil.getInstance().registerBluetoothListener(this);
        if (BluetoothUtil.getInstance().mBluetoothAdapter.isEnabled()) {
            BluetoothUtil.getInstance().searchDevice(params.get(Constants.MAC));
        }
    }

    @Override
    public void onSearchCallback(@NotNull DeviceConfig deviceBean) {
        params.put("weight", deviceBean.getWeight());
        params.put("impedance", deviceBean.getImpedance());
        HttpUtil.getInstance().getBodyParameter(params,deviceBean.getAddress());
    }

    public void unInit() {
        BluetoothUtil.getInstance().stopSearchDevice();
    }
}