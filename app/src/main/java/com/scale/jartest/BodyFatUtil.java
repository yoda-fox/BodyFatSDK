package com.scale.jartest;

public class BodyFatUtil {
    /**
     * 肥胖等级
     * <p>
     * 0.无肥胖
     * 1.肥胖1级
     * 2.肥胖2级
     * 3.肥胖3级
     * 4.肥胖4级
     */
    public static String getObesityLevel(int code) {
        switch (code) {
            case 0:
                return "无肥胖";
            case 1:
                return "肥胖1级";
            case 2:
                return "肥胖2级";
            case 3:
                return "肥胖3级";
            case 4:
                return "肥胖4级";
        }
        return "";
    }

    /**
     * 健康等级
     * <p>
     * 1.偏瘦
     * 2.标准
     * 3.超重
     * 4.肥胖
     */
    public static String getHealthLevel(int code) {
        switch (code) {
            case 1:
                return "偏瘦";
            case 2:
                return "标准";
            case 3:
                return "超重";
            case 4:
                return "肥胖";
        }
        return "";
    }

    /**
     * 1.偏瘦型
     * 2.偏瘦肌肉型
     * 3.标准型
     * 4.标准肌肉型
     * 5.缺乏运动型
     * 6.偏胖型
     * 7.偏胖肌肉型
     * 8.浮肿肥胖型
     * 9.肥胖型
     * 10.肥胖肌肉型
     */
    public static String getBodyType(int code) {
        switch (code) {
            case 1:
                return "偏瘦型";
            case 2:
                return "偏瘦肌肉型";
            case 3:
                return "标准型";
            case 4:
                return "标准肌肉型";
            case 5:
                return "缺乏运动型";
            case 6:
                return "偏胖型";
            case 7:
                return "偏胖肌肉型";
            case 8:
                return "浮肿肥胖型";
            case 9:
                return "肥胖型";
            case 10:
                return "肥胖肌肉型";

        }
        return "";
    }

    /**
     * 获取阻抗状态
     * 1.手脚都接触电极 5000
     * 2.只是脚接触秤电极 45000
     * 3.只是手接触手柄电极 5500
     * -1.手脚都没接触电极
     */
    public static String getImpedanceStatus(int code) {
        switch (code) {
            case 1:
                return "手脚都接触电极";
            case 2:
                return "只是脚接触秤电极";
            case 3:
                return "只是手接触手柄电极";
        }
        return "手脚都没接触电极";
    }
}