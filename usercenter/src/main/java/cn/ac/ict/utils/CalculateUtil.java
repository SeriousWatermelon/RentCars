package cn.ac.ict.utils;

/**
 * 计算 工具
 *
 * @author cuiweiman
 * @date 2020/4/23 14:38
 */
public class CalculateUtil {

    private static Integer A = 64;
    private static Integer N = 2;

    // distance = 10^((abs(RSSI) - A) / (10 * n)).A=64,n=2
    public static String calculateDistance(String rssiStr) {
        Double resultD = 1D;
        Integer rssi = Math.abs(Integer.valueOf(rssiStr));
        resultD = Math.pow(10, (rssi - A) / (Math.pow(10, N)));
        String distance = String.format("%.2f", resultD);
        return distance;
    }

}
