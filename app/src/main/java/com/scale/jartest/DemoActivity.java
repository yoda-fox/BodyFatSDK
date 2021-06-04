package com.scale.jartest;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
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
import com.scale.bluetoothlibrary.bluetooth.BluetoothUtil;
import com.scale.bluetoothlibrary.listener.OnConfigListener;
import com.scale.bluetoothlibrary.listener.OnStatusListener;
import com.scale.bluetoothlibrary.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 提供给第三方厂商使用的demo和依赖库的项目
 */
public class DemoActivity extends AppCompatActivity implements OnStatusListener, OnConfigListener, View.OnClickListener {
    private TextView tvResult;
    private int status = -1;
    private final int REQUEST_CODE_ACCESS_LOCATION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        findViewById(R.id.button).setOnClickListener(this);
        tvResult = findViewById(R.id.tv_result);
        locationPermission();
        //初始化SDK
        BodySDKManager.getInstance().init(this, "d82a7485030fe83b0d955f4792f0ce04",
                "XenwE4VpZxRX0xMfSiqCkdKgzzPq0JGUSFCEzXv0pvMzfBzg5gS91vKyL3fAXj4Q", this);
    }

    @Override
    public void onStatus(int code, String message) {
        status = code;
    }

    @Override
    public void onClick(View v) {
        if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {//判断蓝牙是否打开
            getBodyParameter();
        } else {
            Toast.makeText(DemoActivity.this, "Please turn on bluetooth", Toast.LENGTH_SHORT).show();
        }
    }

    private void getBodyParameter() {
        if (status == 0) {
            Map<String, Object> params = new HashMap<>();
            params.put(Constants.LOGIN_ACCOUNT, "email@163.com");
            params.put(Constants.THIRD_USERNO, 1000);
            params.put(Constants.THIRD_NICKNAME, "test");
            params.put(Constants.HEIGHT, 170);
            params.put(Constants.AGE, 20);
            params.put(Constants.SEX, 1);
            params.put(Constants.MAC, "");
            if (locationPermission()) {//判断位置信息权限，用户未授权位置信息权限，蓝牙无法搜索到设备
                BodySDKManager.getInstance().getBodyParameter(params, DemoActivity.this);
            }
        }
    }

    /**
     * 获取人体各项数据结果成功的回调
     */
    @Override
    public void onDataSuccess(BodyFatConfig bodyFatConfig) {
        StringBuilder builder = new StringBuilder();
        builder.append("体重：").append(String.format("%.2f", bodyFatConfig.getWeight()));
        builder.append(";BMI：").append(String.format("%.1f", bodyFatConfig.getBMI()));
        builder.append(";脂肪率：").append(String.format("%.1f%% ", bodyFatConfig.getFatRate()));
        builder.append(";脂肪量：").append(String.format("%.2fkg ", bodyFatConfig.getFatKg()));
        builder.append(";皮下脂肪率：").append(String.format("%.1f%% ", bodyFatConfig.getSubcutaneousFatRate()));
        builder.append(";皮下脂肪量：").append(String.format("%.2fkg ", bodyFatConfig.getSubcutaneousFatKg()));
        builder.append(";肌肉率：").append(String.format("%.1f%% ", bodyFatConfig.getMuscleRate()));
        builder.append(";肌肉量：").append(String.format("%.2fkg ", bodyFatConfig.getMuscleKg()));
        builder.append(";水分：").append(String.format("%.1f%% ", bodyFatConfig.getWaterRate()));
        builder.append(";含水量：").append(String.format("%.2fkg ", bodyFatConfig.getWaterKg()));
        builder.append(";内脏脂肪等级：").append(String.format("%d ", bodyFatConfig.getVisceralFat()));
        builder.append(";内脏脂肪面积：").append(String.format("%.1fcm² ", bodyFatConfig.getVisceralFatKg()));
        builder.append(";骨量：").append(String.format("%.2fkg ", bodyFatConfig.getBoneKg()));
        builder.append(";骨率：").append(String.format("%.1f%% ", bodyFatConfig.getBoneRate()));
        builder.append(";基础代谢：").append(String.format("%.1f ", bodyFatConfig.getBMR()));
        builder.append(";蛋白质比例：").append(String.format("%.1f%% ", bodyFatConfig.getProteinPercentageRate()));
        builder.append(";蛋白质含量：").append(String.format("%.2fkg ", bodyFatConfig.getProteinPercentageKg()));
        builder.append(";身体年龄：").append(String.format("%d ", bodyFatConfig.getBodyAge()));
        builder.append(";去脂体重：").append(String.format("%.2fkg ", bodyFatConfig.getNotFatWeight()));
        builder.append(";标准体重：").append(String.format("%.2fkg ", bodyFatConfig.getStandardWeight()));
        builder.append(";控制体重：").append(String.format("%.2fkg ", bodyFatConfig.getControlWeight()));
        builder.append(";脂肪控制量：").append(String.format("%.2fkg ", bodyFatConfig.getControlFatKg()));
        builder.append(";肌肉控制量：").append(String.format("%.2fkg ", bodyFatConfig.getControlMuscleKg()));
        builder.append(";肥胖等级：").append(bodyFatConfig.getObesityLevel());
        builder.append(";健康等级：").append(bodyFatConfig.getHealthLevel());
        builder.append(";身体得分：").append(bodyFatConfig.getBodyScore());
        builder.append(";身体类型：").append(bodyFatConfig.getBodyType());
        builder.append(";阻抗类型：").append(bodyFatConfig.getImpedanceStatus());
        builder.append(";设备mac地址：").append(bodyFatConfig.getMac());
        tvResult.setText(builder.toString());
    }

    @Override
    public void onDataFail(String error) {
        Log.e("DemoActivityTAG", "error=" + error);
    }

    /**
     * 判断位置信息权限
     */
    private boolean locationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0以上
            int[] permissions = {ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION),
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)};
            if (permissions[0] != PackageManager.PERMISSION_GRANTED || permissions[1] != PackageManager.PERMISSION_GRANTED) {//用户未授权位置信息权限，请求用户授权
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ACCESS_LOCATION);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * 获取权限结果回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ACCESS_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户允许获取位置权限

            } else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {//用户拒绝获取位置权限并不再提示

            } else {//用户拒绝获取位置权限

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BodySDKManager.getInstance().unInit();
    }
}