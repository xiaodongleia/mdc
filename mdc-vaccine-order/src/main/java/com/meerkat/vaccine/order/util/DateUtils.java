package com.meerkat.vaccine.order.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间控制器
 *
 * @author zhujx
 */
public class DateUtils {

    public static final Long ONE_DAY = 24 * 60 * 60 * 1000L;

    public static final Long ONE_HOUR = 60 * 60 * 1000L;

    public static final Long ONE_MINUTE = 60 * 1000L;

    public static final Long ONE_SECOND = 1000L;

    public static final ThreadLocal<DateFormat> DF_YMD_INT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd"));

    public static final ThreadLocal<DateFormat> DF_YMD = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    public static final ThreadLocal<DateFormat> DF_YMD_HMS = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static final ThreadLocal<DateFormat> DF_YMD_HMS_INT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMddHHmmss"));

    public static final ThreadLocal<DateFormat> DF_YMD_HMS_TZ = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));

    public static final ThreadLocal<DateFormat> DF_YMD_HMS_S = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"));

    public static Date resetZero(Date date) {
        try {
            return DF_YMD.get().parse(DF_YMD.get().format(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parse(String dateStr, String pattern) {
        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(pattern)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String format(Date date, String pattern) {
        if (date == null || StringUtils.isBlank(pattern)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date addDay(Date runDate, int i) {
        return new Date(runDate.getTime() + ONE_DAY * i);
    }

    public static Date addHour(Date runDate, int i) {
        return new Date(runDate.getTime() + ONE_HOUR * i);
    }

    public static boolean isBetween(Date time, Date startTime, Date endTime) {
        if (time == null || startTime == null || endTime == null) {
            return false;
        }
        return time.compareTo(startTime) > 0 && time.compareTo(endTime) < 0;
    }

    public static String dateYmdHmsString(Date date) {
        if (date == null) {
            return null;
        }
        return DF_YMD_HMS.get().format(date);
    }

    public static String dateYmdFormatString(Date date) {
        if (date == null) {
            return null;
        }
        return DF_YMD.get().format(date);
    }

    public static Long dateYmdLong(Date date) {
        if (date == null) {
            return null;
        }
        return Long.valueOf(DF_YMD_INT.get().format(date));
    }

    public static Long dateYmdHmsLong(Date date) {
        if (date == null) {
            return null;
        }
        return Long.valueOf(DF_YMD_HMS_INT.get().format(date));
    }

    public static String dateDiff(Date startTime, Date endTime) {
        int ten = 10;
        long diff = endTime.getTime() - startTime.getTime();
        long hour = diff / ONE_HOUR;
        long min = diff % ONE_DAY % ONE_HOUR / ONE_MINUTE;
        long sec = diff % ONE_DAY % ONE_HOUR % ONE_MINUTE / ONE_SECOND;
        StringBuilder stringBuilder = new StringBuilder();
        String time;
        if (hour < ten) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hour);
        stringBuilder.append(":");
        if (min < ten) {
            stringBuilder.append(0);
        }
        stringBuilder.append(min);
        stringBuilder.append(":");
        if (sec < ten) {
            stringBuilder.append(0);
        }
        stringBuilder.append(sec);
        return stringBuilder.toString();
    }


}
