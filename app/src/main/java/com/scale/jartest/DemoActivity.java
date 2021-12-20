package com.scale.jartest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanResult;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.scale.bluetoothlibrary.BodySDKManager;
import com.scale.bluetoothlibrary.bean.BodyFatConfig;
import com.scale.bluetoothlibrary.listener.OnConfigListener;
import com.scale.bluetoothlibrary.listener.OnStatusListener;
import com.scale.bluetoothlibrary.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Provide demos and library-dependent projects used by third-party vendors
 */
public class DemoActivity extends AppCompatActivity implements BluetoothUtil.BluetoothSearchListener, OnStatusListener, OnConfigListener, View.OnClickListener {
    private TextView tvResult;
    private final int REQUEST_CODE_ACCESS_LOCATION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        findViewById(R.id.button).setOnClickListener(this);
        tvResult = findViewById(R.id.tv_result);
        BluetoothUtil.getInstance().init(this);
        BluetoothUtil.getInstance().registerBluetoothListener(this);
        requestLocationPermission();
        //initSDK
        BodySDKManager.getInstance().init(this, "d82a7485030fe83b0d955f4792f0ce04",
                "XenwE4VpZxRX0xMfSiqCkdKgzzPq0JGUSFCEzXv0pvMzfBzg5gS91vKyL3fAXj4Q", this);
    }

    /**
     * Initialization SDK  result callback
     */
    @Override
    public void onStatus(int code, String message) {
        Log.e("DemoActivityTAG", "message=" + message);
    }

    @Override
    public void onClick(View v) {
        if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {//Determine whether Bluetooth is turned on
            BluetoothUtil.getInstance().searchDevice();//scan device
        } else {
            Toast.makeText(this, "Please turn on bluetooth", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * scan result callback
     */
    @Override
    public void onSearchCallback(ScanResult result) {
        Map<String, Object> params = new HashMap<>();
        params.put(Constants.LOGIN_ACCOUNT, "email@163.com");
        params.put(Constants.THIRD_USERNO, 1000);
        params.put(Constants.THIRD_NICKNAME, "test");
        params.put(Constants.HEIGHT, 170);
        params.put(Constants.AGE, 20);
        params.put(Constants.SEX, 1);
        params.put(Constants.SCALE_TYPE, 1);//1.Four electrodes,2.Eight electrodes
        params.put(Constants.SCAN_RECORD, result.getScanRecord().getBytes());
        //Encapsulate request parameters
        BodySDKManager.getInstance().getBodyParameter(params, DemoActivity.this);
    }

    /**
     * example:Callback for the success of obtaining various data of the human body
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void onDataSuccess(BodyFatConfig bodyFatConfig) {
        StringBuilder builder = new StringBuilder();
        builder.append("<weight:").append(String.format("%.2f", bodyFatConfig.weight));
        builder.append("> <BMI:").append(String.format("%.1f", bodyFatConfig.BMI));
        builder.append("> <fat rate:").append(String.format("%.1f%% ", bodyFatConfig.fatRate));
        builder.append("> <fat mass:").append(String.format("%.2fkg ", bodyFatConfig.fatKg));
        builder.append("> <Subcutaneous fat rate:").append(String.format("%.1f%% ", bodyFatConfig.subcutaneousFatRate));
        builder.append("> <Subcutaneous fat:").append(String.format("%.2fkg ", bodyFatConfig.subcutaneousFatKg));
        builder.append("> <Muscle rate:").append(String.format("%.1f%% ", bodyFatConfig.muscleRate));
        builder.append("> <Muscle mass:").append(String.format("%.2fkg ", bodyFatConfig.muscleKg));
        builder.append("> <Water:").append(String.format("%.1f%% ", bodyFatConfig.waterRate));
        builder.append("> <Moisture:").append(String.format("%.2fkg ", bodyFatConfig.waterKg));
        builder.append("> <Visceral fat grade:").append(String.format("%d ", bodyFatConfig.visceralFat));
        builder.append("> <Visceral fat area:").append(String.format("%.1fcm² ", bodyFatConfig.visceralFatKg));
        builder.append("> <Bone mass:").append(String.format("%.2fkg ", bodyFatConfig.boneKg));
        builder.append("> <Bone rate:").append(String.format("%.1f%% ", bodyFatConfig.boneRate));
        builder.append("> <BMR:").append(String.format("%.1f ", bodyFatConfig.BMR));
        builder.append("> <Protein rate:").append(String.format("%.1f%% ", bodyFatConfig.proteinPercentageRate));
        builder.append("> <Protein mass:").append(String.format("%.2fkg ", bodyFatConfig.proteinPercentageKg));
        builder.append("> <Physical age:").append(String.format("%d ", bodyFatConfig.bodyAge));
        builder.append("> <Fat free body weight:").append(String.format("%.2fkg ", bodyFatConfig.notFatWeight));
        builder.append("> <Standard weight:").append(String.format("%.2fkg ", bodyFatConfig.standardWeight));
        builder.append("> <Weight control:").append(String.format("%.2fkg ", bodyFatConfig.controlWeight));
        builder.append("> <Fat control:").append(String.format("%.2fkg ", bodyFatConfig.controlFatKg));
        builder.append("> <Muscle control:").append(String.format("%.2fkg ", bodyFatConfig.controlMuscleKg));
        builder.append("> <Obesity degree:").append(BodyFatUtil.getObesityLevel(DemoActivity.this, bodyFatConfig.obesityLevel));
        builder.append("> <Health level:").append(BodyFatUtil.getHealthLevel(DemoActivity.this, bodyFatConfig.healthLevel));
        builder.append("> <Body score:").append(bodyFatConfig.bodyScore);
        builder.append("> <Body type:").append(BodyFatUtil.getBodyType(DemoActivity.this, bodyFatConfig.bodyType));
        builder.append("> <Impedance type:").append(BodyFatUtil.getImpedanceStatus(DemoActivity.this, bodyFatConfig.impedanceStatus));
        builder.append(">");
        tvResult.setText(builder.toString());
    }

    @Override
    public void onDataFail(int code, String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    /**
     * request  LocationPermission
     */
    private void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int[] permissions = {ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION),
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)};
            if (permissions[0] != PackageManager.PERMISSION_GRANTED || permissions[1] != PackageManager.PERMISSION_GRANTED) {//用户未授权位置信息权限，请求用户授权
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ACCESS_LOCATION);
            }
        }
    }

    /**
     * request  LocationPermission callback
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ACCESS_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {//The user is allowed to obtain location permissions
                //TODO implement
            } else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {//The user refuses to obtain location permissions and is no longer prompted
                //TODO implement
            } else {//User refused to obtain location permission
                //TODO implement
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BluetoothUtil.getInstance().searchDevice();
    }
}