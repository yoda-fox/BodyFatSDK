package com.scale.bluetoothlibrary.listener;

import com.scale.bluetoothlibrary.bean.BodyFatConfig;

public interface OnConfigListener {
    void onDataSuccess(BodyFatConfig bodyFatConfig);
    void onDataFail(String error);
}
