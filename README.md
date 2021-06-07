# SDK使用说明文档

## 使用条件
    1.最低版本 android5.0（API 21）
    2.依赖环境androidx


## 1.在budid.gradle(Module:app)文件里添加依赖：

      implementation 'com.github.yoda-fox:BodyFatSDK:1.0.2'

## 2.在budid.gradle(Project:projectName)里添加

    repositories {
        maven { url 'https://jitpack.io' }
    }

## 3.在manifest文件里添加权限

    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION"/>
    <uses-permission android:name="android.permission.INTERNET"/>


## 安卓6.0及以上系统必须要定位权限，且需要手动获取位置权限

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0以上
            int[] permissions = {ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION),
                                  ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)};
            if (permissions[0] != PackageManager.PERMISSION_GRANTED || permissions[1] != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
		       Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ACCESS_LOCATION);
            }
        }
    }

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

## 4.开始接入

### 1)初始化
 `BodySDKManager.getInstance().init(this, "appid", "secret", OnStatusListener);//参数appid、secret填入自己申请的。`


### 结果回调：
 @Override
    public void onStatus(int i, String s) {//返回初始化状态0成功，-1失败
    
    }
    
### 2)调用获取人体各项数据的接口，需要开启蓝牙。
            Map<String, Object> params = new HashMap<>();
            params.put(Constants.LOGIN_ACCOUNT, "email2@163.com");//用户账号
            params.put(Constants.THIRD_USERNO, 1000);//用户ID
            params.put(Constants.THIRD_NICKNAME, "测试");//昵称
            params.put(Constants.HEIGHT, 170);//身高
            params.put(Constants.AGE, 20);//年龄
            params.put(Constants.SEX, 1);//性别：1-男，0-女
            params.put(Constants.MAC, "AA:BB:CC:DD:EE:FF");//指定体脂称的mac地址(选填)
	    //getBodyParameter()函数调用前必须开启蓝牙和获取位置信息权限
            BodySDKManager.getInstance().getBodyParameter(params, MainActivity.this);
	    
### 结果回调：
	  @Override
    public void onDataSuccess(BodyConfig bodyFatConfig) {
        StringBuilder builder = new StringBuilder();
        builder.append("体重：").append(String.format("%.2f", bodyFatConfig.weight));
        builder.append(";BMI：").append(String.format("%.1f", BMI));
        builder.append(";脂肪率：").append(String.format("%.1f%% ",fatRate));
        builder.append(";脂肪量：").append(String.format("%.2fkg ", bodyFatConfig.fatKg));
        builder.append(";皮下脂肪率：").append(String.format("%.1f%% ", bodyFatConfig.subcutaneousFatRate));
        builder.append(";皮下脂肪量：").append(String.format("%.2fkg ", bodyFatConfig.subcutaneousFatKg));
        builder.append(";肌肉率：").append(String.format("%.1f%% ", bodyFatConfig.muscleRate));
        builder.append(";肌肉量：").append(String.format("%.2fkg ", bodyFatConfig.muscleKg));
        builder.append(";水分：").append(String.format("%.1f%% ", bodyFatConfig.waterRate));
        builder.append(";含水量：").append(String.format("%.2fkg ", bodyFatConfig.waterKg));
        builder.append(";内脏脂肪等级：").append(String.format("%d ", bodyFatConfig.visceralFat));
        builder.append(";内脏脂肪面积：").append(String.format("%.1fcm² ", bodyFatConfig.visceralFatKg));
        builder.append(";骨量：").append(String.format("%.2fkg ", bodyFatConfig.boneKg));
        builder.append(";骨率：").append(String.format("%.1f%% ", bodyFatConfig.boneRate));
        builder.append(";基础代谢：").append(String.format("%.1f ", bodyFatConfig.BMR));
        builder.append(";蛋白质比例：").append(String.format("%.1f%% ", bodyFatConfig.proteinPercentageRate));
        builder.append(";蛋白质含量：").append(String.format("%.2fkg ", bodyFatConfig.proteinPercentageKg));
        builder.append(";身体年龄：").append(String.format("%d ", (int) bodyFatConfig.bodyAge));
        builder.append(";去脂体重：").append(String.format("%.2fkg ", bodyFatConfig.notFatWeight));
        builder.append(";标准体重：").append(String.format("%.2fkg ", bodyFatConfig.standardWeight));
        builder.append(";控制体重：").append(String.format("%.2fkg ", bodyFatConfig.controlWeight));
        builder.append(";脂肪控制量：").append(String.format("%.2fkg ", bodyFatConfig.controlFatKg));
        builder.append(";肌肉控制量：").append(String.format("%.2fkg ", bodyFatConfig.controlMuscleKg));
        builder.append(";肥胖等级：").append(bodyFatConfig.obesityLevel);
        builder.append(";健康等级：").append(bodyFatConfig.healthLevel);
        builder.append(";身体得分：").append(bodyFatConfig.bodyScore);
        builder.append(";身体类型：").append(bodyFatConfig.bodyType);
	builder.append(";阻抗类型：").append(bodyFatConfig.impedanceStatus);
        builder.append(";设备mac地址：").append(bodyFatConfig.mac);
        tvResult.setText(builder.toString());
    }

    @Override
    public void onDataFail(String s) {
    }

### 注销

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BodySDKManager.getInstance().unInit();
    }

# 输入参数说明

 参数     |参数说明   |数据类型
 -------- | :-----------:  | :-----------:
 LOGIN_ACCOUNT|用户账号|String
 THIRD_USERNO|用户ID|int
 THIRD_NICKNAME|用户昵称D|String
 HEIGHT|测量者的身高(单位：cm,范围：100~220);|double
 AGE|测量者的年龄(范围：10-99)|int
 SEX|测量者的性别(1:男，0：女)|int
 MAC|测量设备的MAC地址(选填);<br>格式: XX:XX:XX:XX:XX:XX|String

## 回调结果说明
### BodyFatConfig(人体各项数据)

 数据名称     |获取方法   |数据类型|描述
 -------- | :-----------:  | :-----------: | :-----------:
 体重|bodyFatConfig.weight|double|
 BMI|bodyFatConfig.BMI|double|
 脂肪率|bodyFatConfig.fatRate|double|
 脂肪量|bodyFatConfig.fatKg|double|
 皮下脂肪率|bodyFatConfig.subcutaneousFatRate|double|
 皮下脂肪量|bodyFatConfig.subcutaneousFatKg|double|
 肌肉率|bodyFatConfig.muscleRate|double|
 肌肉量|bodyFatConfig.muscleKg|double|
 水分 |bodyFatConfig.waterRate|double|
 含水量|bodyFatConfig.waterKg|double|
 内脏脂肪等级|bodyFatConfig.visceralFat|int|
 内脏脂肪面积|bodyFatConfig.visceralFatKg|double|
 骨量|bodyFatConfig.boneKg|double|
 骨率|bodyFatConfig.boneRate|double|
 基础代谢|bodyFatConfig.BMR|double|
 蛋白质比例|bodyFatConfig.proteinPercentageRate|double|
 蛋白质含量|bodyFatConfig.proteinPercentageKg|double|
 身体年龄|bodyFatConfig.bodyAge|int|
 去脂体重|bodyFatConfig.notFatWeight|double|
 标准体重|bodyFatConfig.standardWeight|double|
 控制体重|bodyFatConfig.controlWeight|double|返回结果：负数是需要减少的量
 脂肪控制量|bodyFatConfig.controlFatKg|double|返回结果：负数是需要减少的量
 肌肉控制量|bodyFatConfig.controlMuscleKg|double|返回结果：负数是需要减少的量
 肥胖等级|bodyFatConfig.obesityLevel|int|0：无肥胖; 1：肥胖1级; 2：肥胖2级; 3：肥胖3级;<br>4：肥胖4级
 健康等级|bodyFatConfig.healthLevel|int|1：偏瘦; 2：标准; 3：超重; 4：肥胖
 身体得分|bodyFatConfig.bodyScore|int|
 身体类型|bodyFatConfig.bodyType|int|1：偏瘦型; 2：偏瘦肌肉型; 3：标准型;<br>4：标准肌肉型; 5：缺乏运动型; 6：偏胖型;<br>7：偏胖肌肉型; 8：浮肿肥胖型; 9：肥胖型;<br>10：肥胖肌肉型
 阻抗类型|bodyFatConfig.impedanceStatus|int|1：手脚都接触电极; 2：只是脚接触秤电极;<br> 3：只是手接触手柄电极; 4：手脚都没接触电极
 设备MAC地址|bodyFatConfig.mac|String|返回测量数据的体脂秤设备Mac地址
