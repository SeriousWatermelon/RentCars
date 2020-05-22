package cn.ac.ict.utils;

import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat hhSdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
    public static SimpleDateFormat onlyHourSdf = new SimpleDateFormat("HH");
    public static SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat beginSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    public static SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date nowDate() {
        return new Date();
    }

    public static Long dateTimestamp(Date date) {
        return date.getTime();
    }


    /**
     * Date 转 字符串
     *
     * @param dateTime
     * @return
     */
    public static String dateFormat(Date dateTime) {
        if (!ObjectUtils.isEmpty(dateTime)) {
            return sdf.format(dateTime);
        }
        return null;
    }

    public static String dateFormatHH(Date dateTime) {
        if (!ObjectUtils.isEmpty(dateTime)) {
            return hhSdf.format(dateTime);
        }
        return null;
    }

    public static String dateFormat(Date dateTime, SimpleDateFormat selfSdf) {
        if (!ObjectUtils.isEmpty(dateTime)) {
            return selfSdf.format(dateTime);
        }
        return null;
    }

    /**
     * 字符串 转 Date
     *
     * @param dateTime
     * @return
     */
    public static Date parse(String dateTime) {
        if (!StringUtils.isEmpty(dateTime)) {
            try {
                return sdf.parse(dateTime);
            } catch (ParseException e) {
                throw new GlobalException(AppCodeInfo.DATE_FORMAT_ERROR);
            }
        }
        return null;
    }

    public static Date parseTime(String dateTime) {
        if (!StringUtils.isEmpty(dateTime)) {
            try {
                return timeSdf.parse(dateTime);
            } catch (ParseException e) {
                throw new GlobalException(AppCodeInfo.DATE_FORMAT_ERROR);
            }
        }
        return null;
    }
}
