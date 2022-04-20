package com.example.covider.utils;

import java.util.Calendar;

/**
 * @Author: zhozicho
 * @Date: 2022/4/16 20:54
 * @Desc:
 */
public class TimeUtil {
    private static final Calendar calendar = Calendar.getInstance();
    public static final boolean today(long timeMillis){
        calendar.setTimeInMillis(timeMillis);
        int lYear = calendar.get(Calendar.YEAR);
        int lMonth = calendar.get(Calendar.MONTH);
        int lDay = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_YEAR);

        return year == lYear && month == lMonth && day == lDay;
    }

    public static final boolean sameWeek(long timeMillis){
        calendar.setTimeInMillis(timeMillis);
        int lYear = calendar.get(Calendar.YEAR);
        int lWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);

        return year == lYear && week == lWeek;
    }
    public static final boolean sameMonth(long timeMillis){
        calendar.setTimeInMillis(timeMillis);
        int lYear = calendar.get(Calendar.YEAR);
        int lMonth = calendar.get(Calendar.MONTH);
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        return year == lYear && month == lMonth;
    }
}
