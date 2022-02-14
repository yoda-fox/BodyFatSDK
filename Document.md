# 与体脂秤交互获取人体各项数据
# SDK使用说明文档
[English document](https://github.com/yoda-fox/BodyFatSDK#readme)   

## 使用条件
    1.最低版本 android5.0（API 21）
    2.依赖环境androidx

## 1.在budid.gradle(Module:app)文件里添加依赖：
       implementation 'com.github.yoda-fox:BodyFatSDK:2.1.1'

## 2.在budid.gradle(Project:projectName)里添加
    repositories {
        maven { url 'https://jitpack.io' }
    }

## 3.在manifest文件里添加权限
    <uses-permission android:name="android.permission.INTERNET"/>
    
## 4.代码需要混淆的在 proguard-rules.pro文件里添加
-keep public class com.scale.bluetoothlibrary.** {*;}
-keep class com.google.gson.** {*;}
-keep class com.squareup.okhttp3.** { *;}

## 5.开始接入

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
            params.put(Constants.SCALE_TYPE, 1);//秤类型：1.四电极,2.八电极
            params.put(Constants.SCAN_RECORD, scanRecord);//扫描设备返回的广播包
           //Encapsulate request parameters
        BodySDKManager.getInstance().getBodyParameter(params, MainActivity.this);
	    
### 结果回调：
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


# 输入参数说明

 参数     |参数说明   |数据类型
 -------- | :-----------:  | :-----------:
 LOGIN_ACCOUNT|用户账号|String
 THIRD_USERNO|用户ID|int
 THIRD_NICKNAME|用户昵称D|String
 HEIGHT|测量者的身高(单位：cm,范围：100~220);|double
 AGE|测量者的年龄(范围：10~99)|int
 SEX|测量者的性别(1:男，0：女)|int
 SCALE_TYPE|秤类型(1.四电极，2.八电极)|int
 SCAN_RECORD|广播数据包|byte[]

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
 控制体重|bodyFatConfig.controlWeight|double|返回结果：负值是需要减少的量
 脂肪控制量|bodyFatConfig.controlFatKg|double|返回结果：负值是需要减少的量
 肌肉控制量|bodyFatConfig.controlMuscleKg|double|返回结果：负值是需要减少的量
 肥胖等级|bodyFatConfig.obesityLevel|int|0：无肥胖; 1：肥胖1级; 2：肥胖2级; 3：肥胖3级;<br>4：肥胖4级
 健康等级|bodyFatConfig.healthLevel|int|1：偏瘦; 2：标准; 3：超重; 4：肥胖
 身体得分|bodyFatConfig.bodyScore|int|
 身体类型|bodyFatConfig.bodyType|int|1：偏瘦型; 2：偏瘦肌肉型; 3：标准型;<br>4：标准肌肉型; 5：缺乏运动型; 6：偏胖型;<br>7：偏胖肌肉型; 8：浮肿肥胖型; 9：肥胖型;<br>10：肥胖肌肉型
 阻抗类型|bodyFatConfig.impedanceStatus|int| **八极秤:** 1：手脚都接触电极; 2：只是脚接触秤电极;<br> 3：只是手接触手柄电极; -1：手脚都没接触电极<br> **普通脂肪秤:** 1：脚接触秤电极; -1：脚没有接触秤电极
 上肢脂肪率|bodyFatConfig.upFat|double|
 下肢脂肪率|bodyFatConfig.downFat|double|
 上肢肌肉率|bodyFatConfig.upMuscle|double|
 下肢肌肉率|bodyFatConfig.downMuscle|double|

 
 
## 参数范围说明
### 1.体重
#### 标准体重：standardWeight
#### 男标准范围：
    float downWeight = standardWeight  - standardWeight  * 0.1f;
#### 女标准范围：
    float upWeight = standardWeight  + standardWeight  * 0.1f; 
#### 偏胖范围：
    float fatWeight = standardWeight  + standardWeight  * 0.2f;
#### 人的体重范围：
 分类     |标准范围
 -------- | :-----------:
偏瘦|weight<downWeight
标准|downWeight≤weight<upWeight 
偏胖|upWeight ≤weight<fatWeight 
肥胖|weight≥fatWeight 
			      			      
### 2.BMI
 分类     |标准范围
 -------- | :-----------:
偏瘦|BMI<18.5
标准|18.5≤BMI<24
偏胖|24≤BMI<30
肥胖|30≤BMI

### 3.脂肪率
#### 男性：
如果年龄：小于 40
low = 10f;<br>
stander = 21f;<br>
lightFat = 26f;<br>
如果年龄： 大于等于40， 小于60 的<br>
low = 11f;<br>
stander = 22f;<br>
lightFat = 27f;<br>
如果年龄： 大于等于60 的<br>
low = 13f;<br>
stander = 24f;<br>
lightFat = 29f;<br>
#### 女性：
如果年龄：小于 40<br>
low = 20f;<br>
stander = 34f;<br>
lightFat = 39f;<br>
如果年龄： 大于等于40， 小于60 的<br>
low = 21f;<br>
stander = 35f;<br>
lightFat = 40f;<br>
如果年龄： 大于等于60 的<br>
low = 22f;<br>
stander = 36f;<br>
lightFat = 41f; <br>

 分类     |标准范围
 -------- | :-----------:
偏瘦|fat≤low
标准|low<fat≤stander
偏胖|stander<fat≤lightFat
肥胖|lightFat<fat

### 4.皮下脂肪率
 分类|男性标准范围|女性标准范围
 -------- | :-----------: | :-----------:
偏瘦|<8.6%|<18.5%
标准|8.6%-16.7%|18.5%-26.7%
偏胖|16.7%-20.7|26.7-30.8%
肥胖|>20.7%|>30.8%	
		
### 5. 肌肉率
#### 男性：
身高：小于 160cm<br>
low = 38.5f;<br>
mid = 46.5f;<br>
身高： 大于等于160， 小于等于170 的<br>
low = 44f;<br>
mid = 52.4f;<br>
如果年龄： 大于 170 的<br>
low = 49.4f;<br>
mid = 59.4f;
#### 女性：
身高：小于 150cm<br>
low = 29.1f;<br>
mid = 34.7f;<br>
身高： 大于等于150， 小于等于160 的<br>
low = 32.9f;<br>
mid = 37.5f;<br>
如果年龄： 大于 170 的<br>
low = 36.5f;<br>
mid = 42.5f;
 分类     |标准范围
 -------- | :-----------:
不足|muscle≤low
标准|low<muscle≤mid
优|mid<muscle

### 6.水分
#### 男性：
low = 55f;
mid = 65f;
#### 女性：
low = 45f;
mid = 60f;
 分类     |标准范围
 -------- | :-----------:
不足|water≤low
标准|low<water≤mid
优|mid<water
	    
### 7.内脏脂肪等级
 分类     |标准范围
 -------- | :-----------:
标准|visceral≤9
警惕|9<visceral≤14
危险|14<visceral
	       
### 8.骨量
#### 男性：
如果体重：小于 60kg<br>
low = 2.4f;<br>
mid = 2.6f;<br>
如果体重： 大于等于60， 小于等于75 的<br>
low = 2.8f;<br>
mid = 3.0f;<br>
如果体重： 大于 75 的<br>
low = 3.1f;<br>
mid = 3.3f;
#### 女性：
如果体重：小于 45kg<br>
low = 1.7f;<br>
mid = 1.9f;<br>
如果体重： 大于等于45， 小于等于60 的<br>
low = 2.1f;<br>
mid = 2.3f;<br>
如果体重： 大于 60 的<br>
low = 2.4f;<br>
mid = 2.6f;
 分类     |标准范围
 -------- | :-----------:
不足|bone≤low
标准|low<bone≤mid
优|mid<bone    

### 9.基础代谢	   
如果年龄：小于等于29 的<br>
男性：low = weight * 24.0f;<br>
女性：low = weight * 23.6f;<br>
如果年龄：大于等于30， 小于等于49 的<br>
男性：low = weight * 22.3f;<br>
女性：low = weight * 21.7f;<br>
如果年龄：大于 49 的<br>
男性：low = weight * 21.5f;<br>
女性：low = weight * 20.7f;<br>
 分类     |标准范围
 -------- | :-----------:
偏低|bmr≤low
优|low<bmr
### 10.蛋白质比例
 分类     |标准范围
 -------- | :-----------:
不足|protein<16
标准|16≤protein≤20
优|20<protein	  
### 11.身体得分
 分类     |标准范围
 -------- | :-----------:
存在隐患|0-60
亚健康|60-70
一般|70-80
良好|80-90   
非常好|≥90	   		
