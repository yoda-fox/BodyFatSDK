# About

[中文文档](https://github.com/yoda-fox/BodyFatSDK/blob/master/Document.md)  

## Conditions of Use
    1.The minimum version is android5.0 (API 21)
    2.Dependent environment androidx


## 1.Add dependency in file of budid.gradle(Module:app):

    implementation 'com.github.yoda-fox:BodyFatSDK:2.1.1'

## 2.Add in budid.gradle(Project:projectName):

    repositories {
        maven { url 'https://jitpack.io' }
    }

## 3.Add permissions in the file of “manifest”
    <uses-permission android:name="android.permission.INTERNET"/>
## 4.Code obfuscation
-keep public class com.scale.bluetoothlibrary.** {*;}
-keep class com.google.gson.** {*;}
-keep class com.squareup.okhttp3.** { *;}

## 5.Start access

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
            params.put(Constants.THIRD_NICKNAME, "测试");//nickname
            params.put(Constants.HEIGHT, 170);//Height
            params.put(Constants.AGE, 20);//Age
            params.put(Constants.SEX, 1);//Gender: 1-male, 0-female
            params.put(Constants.SCALE_TYPE, 1);//Scale type: 1. Four electrodes, 2. Eight electrodes
            params.put(Constants.SCAN_RECORD, scanRecord);//Scanning the broadcast packet returned by the device
            //Encapsulate request parameters
            BodySDKManager.getInstance().getBodyParameter(params,this);
	    
### Result callback:
	  @Override
    public void onDataSuccess(BodyConfig bodyFatConfig) {
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
        builder.append("> <Obesity degree:").append(BodyFatUtil.getObesityLevel(MainActivity.this, bodyFatConfig.obesityLevel));
        builder.append("> <Health level:").append(BodyFatUtil.getHealthLevel(MainActivity.this, bodyFatConfig.healthLevel));
        builder.append("> <Body score:").append(bodyFatConfig.bodyScore);
        builder.append("> <Body type:").append(BodyFatUtil.getBodyType(MainActivity.this, bodyFatConfig.bodyType));
        builder.append("> <Impedance type:").append(BodyFatUtil.getImpedanceStatus(MainActivity.this, bodyFatConfig.impedanceStatus));
	builder.append("> <Upper limb fat:").append(bodyFatConfig.upFat);
        builder.append("> <Lower limb fat:").append(bodyFatConfig.downFat);
        builder.append("> <Upper limb muscle:").append(bodyFatConfig.upMuscle);
        builder.append("> <Lower limb muscle:").append(bodyFatConfig.downMuscle);
        builder.append(">");
        tvResult.setText(builder.toString());
    }

    @Override
    public void onDataFail(String s) {
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
 SCALE_TYPE|Scale type (1. Four electrodes, 2. Eight electrodes)|int
 SCAN_RECORD|Broadcast packet|byte[]

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
 Body type|bodyFatConfig.bodyType|int|1: Lean type; 2: Lean muscle type; 3: Standard type;<br>4: Standard muscle type; 5: lack of exercise type;  <br>6: overweight type;7: Obese muscular type; <br>8: Puffyand obese type; 9: Obese type;10: Obese muscle type
 Impedance type|bodyFatConfig.impedanceStatus|int| **8 electrodes body up & down scale:** <br>1: Both hands and feet are in contact with the electrodes;<br>2: Only the feet are in contact with the electrodes of the scale; 3: Only the hand touches the electrode of the handle;<br> -1: No hand or foot touches the electrode<br> **Normal body fat scale:** <br>1: The foot is in contact with the electrode of the scale;<br>-1: The foot is not in contact with the electrode of the scale
 Upper limb fat rate|bodyFatConfig.upFat|double|
 Lower limb fat rate|bodyFatConfig.downFat|double|
 Upper limb muscle rate|bodyFatConfig.upMuscle|double|
 Lower limb muscle rate|bodyFatConfig.downMuscle|double|
