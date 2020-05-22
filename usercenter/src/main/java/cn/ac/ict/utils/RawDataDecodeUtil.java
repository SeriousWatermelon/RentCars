package cn.ac.ict.utils;

import cn.ac.ict.entity.TtRawDataEntity;

/**
 * 蓝牙手环 rawData 解码
 */
public class RawDataDecodeUtil {

    public static TtRawDataEntity decodeRawData(String mac, String rawData) {
        TtRawDataEntity entity = new TtRawDataEntity();
        entity.setMac(mac);
        entity.setFactoryId(rawData.substring(10, 18));
        entity.setTime(DateUtil.parseTime(hex2Dec(rawData.substring(18, 20)) + ":" + hex2Dec(rawData.substring(20, 22)) + ":00"));
        entity.setStepCount(hex2Dec(rawData.substring(24, 26) + rawData.substring(22, 24)));
        entity.setMileage(hex2Dec(rawData.substring(28, 30) + rawData.substring(26, 28)));
        entity.setCalorie(hex2Dec(rawData.substring(32, 34) + rawData.substring(30, 32)));
        entity.setPower(hex2Dec(rawData.substring(34, 36)));
        entity.setSos(hex2Dec(rawData.substring(36, 38)));
        entity.setTemperature(hex2Dec(rawData.substring(38, 40)) + "." + hex2Dec(rawData.substring(40, 42)));
        entity.setSleepTime(hex2Dec(rawData.substring(42, 44)));
        return entity;
    }

    /**
     * 16进制数 转 十进制数
     *
     * @param hexNum
     * @return
     */
    private static String hex2Dec(String hexNum) {
        Integer dec = Integer.parseInt(hexNum, 16);
        return dec.toString();
    }


    public static void main(String[] args) {
        String rowData = "02010612FF107803E80E2B00000000000031001D1800080959543120373742";
        String mac = "";
        TtRawDataEntity entity = decodeRawData(mac, rowData);
        System.out.println(entity);
    }

}
