package it.mbkj.lib.utils;

import java.text.SimpleDateFormat;

/**
 * Created by lv on 18-6-4.
 */

public class TimeFormatUtil {
    public static String FORMAT_A = "yyyy.MM.dd HH:mm";
    public static String FORMAT_B = "yyyy-MM-dd";
    public static String FORMAT_C = "HH:mm MM/dd";
   // public static String FORMAT_D = App.INSTANCE.getString(R.string.libtimeFormatUtil1);
    public static String FORMAT_E = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_F = "yyyy-MM-dd HH:mm";
    public static String FORMAT_G = "MM-dd HH:mm";
    public static String FORMAT_H = "HH:mm:ss";
   // public static String FORMAT_I = App.INSTANCE.getString(R.string.libtimeFormatUtil2);
    public static String FORMAT_J = "yyyy/MM/dd HH:mm:ss";
    public static String FORMAT_K = "HH:mm";


    public static SimpleDateFormat SF_FORMAT_A = new SimpleDateFormat(FORMAT_A);
    public static SimpleDateFormat SF_FORMAT_B = new SimpleDateFormat(FORMAT_B);
    public static SimpleDateFormat SF_FORMAT_C = new SimpleDateFormat(FORMAT_C);
    //public static SimpleDateFormat SF_FORMAT_D = new SimpleDateFormat(FORMAT_D);
    public static SimpleDateFormat SF_FORMAT_E = new SimpleDateFormat(FORMAT_E);
    public static SimpleDateFormat SF_FORMAT_F = new SimpleDateFormat(FORMAT_F);
    public static SimpleDateFormat SF_FORMAT_G = new SimpleDateFormat(FORMAT_G);
    public static SimpleDateFormat SF_FORMAT_H = new SimpleDateFormat(FORMAT_H);
   // public static SimpleDateFormat SF_FORMAT_I = new SimpleDateFormat(FORMAT_I);
    public static SimpleDateFormat SF_FORMAT_J = new SimpleDateFormat(FORMAT_J);
    public static SimpleDateFormat SF_FORMAT_K = new SimpleDateFormat(FORMAT_K);


    /**
     * 转化为 hh:mm:ss 格式
     *
     * @param second
     * @return
     */
    public static String second2TimeSecond(long second) {
        long hours = second / 3600;
        long minutes = (second % 3600) / 60;
        long seconds = second % 60;

        String hourString = "";
        String minuteString = "";
        String secondString = "";
        if (hours < 10) {
            hourString = "0";
            if (hours == 0) {
                hourString += "0";
            } else {
                hourString += String.valueOf(hours);
            }
        } else {
            hourString = String.valueOf(hours);
        }
        if (minutes < 10) {
            minuteString = "0";
            if (minutes == 0) {
                minuteString += "0";
            } else {
                minuteString += String.valueOf(minutes);
            }
        } else {
            minuteString = String.valueOf(minutes);
        }
        if (seconds < 10) {
            secondString = "0";
            if (seconds == 0) {
                secondString += "0";
            } else {
                secondString += String.valueOf(seconds);
            }
        } else {
            secondString = String.valueOf(seconds);
        }
        return hourString + ":" + minuteString + ":" + secondString;
    }

/*    *//**
     * @param time
     * @return
     *//*
    public static String parseTime(Long time) {
        if (time < 60) {
            return time + App.INSTANCE.getString(R.string.libtimeFormatUtil3);
        }
        if (time < 60 * 60) {
            long minute = time / 60;
            long second = time % 60;
            return minute + App.INSTANCE.getString(R.string.libtimeFormatUtil4) + second + App.INSTANCE.getString(R.string.libtimeFormatUtil3);
        }
        if (time < 24 * 60 * 60) {
            long hour = time / (60 * 60);
            long minute = (time % (60 * 60)) / 60;
            long second = time % 60;
            return hour + App.INSTANCE.getString(R.string.libtimeFormatUtil5) + minute + App.INSTANCE.getString(R.string.libtimeFormatUtil4) + second + App.INSTANCE.getString(R.string.libtimeFormatUtil3);
        }

        long day = time / (60 * 60 * 24);
        long hour = time % (60 * 60 * 24) / (60 * 60);
        long minute = (time % (60 * 60)) / 60;
        long second = time % 60;
        return day + App.INSTANCE.getString(R.string.libtimeFormatUtil6) + hour + App.INSTANCE.getString(R.string.libtimeFormatUtil5) + minute + App.INSTANCE.getString(R.string.libtimeFormatUtil4) + second + App.INSTANCE.getString(R.string.libtimeFormatUtil3);

    }*/
}
