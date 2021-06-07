# About

[中文文档](https://github.com/yoda-fox/BodyFatSDK/blob/master/Document.md)  

## 使用条件
    1.The minimum version is android5.0 (API 21)
    2.Dependent environment androidx


## 1.Add dependency in file of budid.gradle(Module:app):

      implementation 'com.github.yoda-fox:BodyFatSDK:1.0.3'

## 2.Add in budid.gradle(Project:projectName):

    repositories {
        maven { url 'https://jitpack.io' }
    }

## 3.Add permissions in the file of “manifest”

    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION"/>
    <uses-permission android:name="android.permission.INTERNET"/>


## Android 6.0 and above systems must have location permission, and need to manually obtain location permission

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
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {//User allowed to obtain location permissions

            } else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {//User Refuse to obtain location permission and don’t remind again

            } else {//User Refuse to obtain location permission

            }
        }
    }

## 4.Start access

### 1) Initialization
 `BodySDKManager.getInstance().init(this, "appid", "secret", OnStatusListener);//Fill in the parameters appid, secret that you applied for.`


### Result callback:
    @Override
    public void onStatus(int i, String s) {//Return to initialization status 0 success, -1 failure
    
    }
    
### 2) To call the interface to obtain various data of the human body, Bluetooth needs to be turned on
            Map<String, Object> params = new HashMap<>();
            params.put(Constants.LOGIN_ACCOUNT, "email2@163.com");//user account
            params.put(Constants.THIRD_USERNO, 1000);//user ID
            params.put(Constants.THIRD_NICKNAME, "test");//nickname
            params.put(Constants.HEIGHT, 170);//Height
            params.put(Constants.AGE, 20);//Age
            params.put(Constants.SEX, 1);//Gender: 1-male, 0-female
            params.put(Constants.MAC, "AA:BB:CC:DD:EE:FF");//Specify the mac address of the body fat scale (optional)
	    //Before the 'getBodyParameter()' function is called, Bluetooth must be turned on and permission to obtain location information
            BodySDKManager.getInstance().getBodyParameter(params, MainActivity.this);
	    
### Result callback:
	  @Override
    public void onDataSuccess(BodyConfig bodyFatConfig) {
        StringBuilder builder = new StringBuilder();
        builder.append("body weight:").append(String.format("%.2f", bodyFatConfig.weight));
        builder.append(";BMI：").append(String.format("%.1f", BMI));
        builder.append(";Fat rate:").append(String.format("%.1f%% ",fatRate));
        builder.append(";Fat weight:").append(String.format("%.2fkg ", bodyFatConfig.fatKg));
        builder.append(";Subcutaneous fat rate:").append(String.format("%.1f%% ", bodyFatConfig.subcutaneousFatRate));
        builder.append(";Subcutaneous fat mass:").append(String.format("%.2fkg ", bodyFatConfig.subcutaneousFatKg));
        builder.append(";Muscle rate:").append(String.format("%.1f%% ", bodyFatConfig.muscleRate));
        builder.append(";Muscle mass:").append(String.format("%.2fkg ", bodyFatConfig.muscleKg));
        builder.append(";Moisture:").append(String.format("%.1f%% ", bodyFatConfig.waterRate));
        builder.append(";Water content:").append(String.format("%.2fkg ", bodyFatConfig.waterKg));
        builder.append(";Visceral fat grade:").append(String.format("%d ", bodyFatConfig.visceralFat));
        builder.append(";Visceral fat area:").append(String.format("%.1fcm² ", bodyFatConfig.visceralFatKg));
        builder.append(";Bone Mass:").append(String.format("%.2fkg ", bodyFatConfig.boneKg));
        builder.append(";Bone rate:").append(String.format("%.1f%% ", bodyFatConfig.boneRate));
        builder.append(";Basic metabolism:").append(String.format("%.1f ", bodyFatConfig.BMR));
        builder.append(";Protein ratio:").append(String.format("%.1f%% ", bodyFatConfig.proteinPercentageRate));
        builder.append(";Protein content:").append(String.format("%.2fkg ", bodyFatConfig.proteinPercentageKg));
        builder.append(";Body age:").append(String.format("%d ", (int) bodyFatConfig.bodyAge));
        builder.append(";Lean body weight:").append(String.format("%.2fkg ", bodyFatConfig.notFatWeight));
        builder.append(";Standard weight:").append(String.format("%.2fkg ", bodyFatConfig.standardWeight));
        builder.append(";control Weight:").append(String.format("%.2fkg ", bodyFatConfig.controlWeight));
        builder.append(";Fat control amount:").append(String.format("%.2fkg ", bodyFatConfig.controlFatKg));
        builder.append(";Muscle control amount:").append(String.format("%.2fkg ", bodyFatConfig.controlMuscleKg));
        builder.append(";Obesity level:").append(bodyFatConfig.obesityLevel);
        builder.append(";Health level:").append(bodyFatConfig.healthLevel);
        builder.append(";Body score:").append(bodyFatConfig.bodyScore);
        builder.append(";Body type:").append(bodyFatConfig.bodyType);
	builder.append(";Impedance type:").append(bodyFatConfig.impedanceStatus);
        builder.append(";Device mac address:").append(bodyFatConfig.mac);
        tvResult.setText(builder.toString());
    }

    @Override
    public void onDataFail(String s) {
    }

### Log out

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BodySDKManager.getInstance().unInit();
    }

# Input parameter description

 parameter     |Parameter Description   |type of data
 -------- | :-----------:  | :-----------:
 LOGIN_ACCOUNT|user account|String
 THIRD_USERNO|user ID|int
 THIRD_NICKNAME|User nickname|String
 HEIGHT|The height of the user (unit: cm, range: 100~220)|double
 AGE|The age of the user (range: 10-99)|int
 SEX|Gender of the user (1: male, 0: female)|int
 MAC|MAC address of the measuring device (optional);<br>Format: XX:XX:XX:XX:XX:XX|String

## Result Callback description
### BodyFatConfig(Human body data)

 Data name     |How to Obtain   |Data type|Description
 -------- | :-----------:  | :-----------: | :-----------:
 weight|bodyFatConfig.weight|double|
 BMI|bodyFatConfig.BMI|double|
 Fat rate|bodyFatConfig.fatRate|double|
 Fat mass|bodyFatConfig.fatKg|double|
 Subcutaneous fat rate|bodyFatConfig.subcutaneousFatRate|double|
 Subcutaneous fat mass|bodyFatConfig.subcutaneousFatKg|double|
 Muscle rate|bodyFatConfig.muscleRate|double|
 Muscle mass|bodyFatConfig.muscleKg|double|
 Moisture |bodyFatConfig.waterRate|double|
 Water content|bodyFatConfig.waterKg|double|
 Visceral fat grade|bodyFatConfig.visceralFat|int|
 Visceral fat area|bodyFatConfig.visceralFatKg|double|
 Bone Mass|bodyFatConfig.boneKg|double|
 Bone rate|bodyFatConfig.boneRate|double|
 Basic metabolism|bodyFatConfig.BMR|double|
 Protein ratio|bodyFatConfig.proteinPercentageRate|double|
 Protein content|bodyFatConfig.proteinPercentageKg|double|
 Body age|bodyFatConfig.bodyAge|int|
 Lean body weight|bodyFatConfig.notFatWeight|double|
 Standard weight|bodyFatConfig.standardWeight|double|
 Control weight|bodyFatConfig.controlWeight|double|Return result: a negative number is the amount that needs to be reduced
 Fat control amount|bodyFatConfig.controlFatKg|double|Return result: a negative number is the amount that needs to be reduced
 Muscle control amount|bodyFatConfig.controlMuscleKg|double|Return result: a negative number is the amount that needs to be reduced
 Obesity level|bodyFatConfig.obesityLevel|int|0: No obesity; 1: Obesity level 1; 2: Obesity level 2; <br>3: Obesity level 3; 4: Obesity level 4
 Health level|bodyFatConfig.healthLevel|int|1: Lean; 2: Standard; 3:Overweight; 4: Obese
 Body score|bodyFatConfig.bodyScore|int|
 Body type|bodyFatConfig.bodyType|int|1: Lean type; 2: Lean muscle type; 3: Standard type;<br>4: Standard muscle type; 5: lack of exercise type;  <br>6: overweight type;7: Obese muscular type; 8: Puffyand obese type; <br>9: Obese type;10: Obese muscle type
 Impedance type|bodyFatConfig.impedanceStatus|int| **八极秤:** 1: Both hands and feet are in contact with the electrodes;<br>2: Only the feetare in contact with the electrodes of the scale; 3: Only the hand touches the electrode of the handle;<br> -1: No hand or foot touches the electrode<br> **普通脂肪秤:** 1：脚接触秤电极; -1：脚没有接触秤电极
 Device mac address|bodyFatConfig.mac|String|The Mac address of the body fat scale 
