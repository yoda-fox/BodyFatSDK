# android 与体脂秤交互获取人体各项数据

## 使用条件
    1.最低版本 android5.0（API 21）
    2.依赖环境androidx


## 1.在budid.gradle(Module:app)文件里添加依赖：

      implementation 'com.github.yoda-fox:BodyFatSDK:1.0.0'

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
        builder.append(";身体年龄：").append(String.format("%d ", (int) bodyFatConfig.getBodyAge()));
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
 体重|bodyFatConfig.getWeight()|double|
 BMI|bodyFatConfig.getBMI()|double|
 脂肪率|bodyFatConfig.getFatRate()|double|
 脂肪量|bodyFatConfig.getFatKg()|double|
 皮下脂肪率|bodyFatConfig.getSubcutaneousFatRate()|double|
 皮下脂肪量|bodyFatConfig.getSubcutaneousFatKg()|double|
 肌肉率|bodyFatConfig.getMuscleRate()|double|
 肌肉量|bodyFatConfig.getMuscleKg()|double|
 水分 |bodyFatConfig.getWaterRate()|double|
 含水量|bodyFatConfig.getWaterKg()|double|
 内脏脂肪等级|bodyFatConfig.getVisceralFat()|int|
 内脏脂肪面积|bodyFatConfig.getVisceralFatKg()|double|
 骨量|bodyFatConfig.getBoneKg()|double|
 骨率|bodyFatConfig.getBoneRate()|double|
 基础代谢|bodyFatConfig.getBMR()|double|
 蛋白质比例|bodyFatConfig.getProteinPercentageRate()|double|
 蛋白质含量|bodyFatConfig.getProteinPercentageKg()|double|
 身体年龄|bodyFatConfig.getBodyAge()|int|
 去脂体重|bodyFatConfig.getNotFatWeight()|double|
 标准体重|bodyFatConfig.getStandardWeight()|double|
 控制体重|bodyFatConfig.getControlWeight()|double|返回结果：负数是需要减少的量
 脂肪控制量|bodyFatConfig.getControlFatKg()|double|返回结果：负数是需要减少的量
 肌肉控制量|bodyFatConfig.getControlMuscleKg()|double|返回结果：负数是需要减少的量
 肥胖等级|bodyFatConfig.getObesityLevel()|int|0：无肥胖; 1：肥胖1级; 2：肥胖2级; 3：肥胖3级; 4：肥胖4级
 健康等级|bodyFatConfig.getHealthLevel()|int|1：偏瘦; 2：标准; 3：超重; 4：肥胖
 身体得分|bodyFatConfig.getBodyScore()|int|
 身体类型|bodyFatConfig.getBodyType()|int|1：偏瘦型; 2：偏瘦肌肉型; 3：标准型; 4：标准肌肉型;<br>5：缺乏运动型; 6：偏胖型; 7：偏胖肌肉型;<br>8：浮肿肥胖型; 9：肥胖型; 10：肥胖肌肉型
 阻抗类型|bodyFatConfig.getImpedanceStatus()|int|1：手脚都接触电极; 2：只是脚接触秤电极;<br> 3：只是手接触手柄电极; 4：手脚都没接触电极
