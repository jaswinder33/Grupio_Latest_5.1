package com.grupio.backend;

import android.os.SystemClock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by JSN on 9/11/16.
 */

public class DateTime {

    public static DateTime getInstance() {
        return new DateTime();
    }

    /**
     * Get current time with UTC timezone
     *
     * @return
     */
//    public String currentTimeInUTC() {
//
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        df.setTimeZone(TimeZone.getDefault());
//        String formattedDate = "";
//        try {
//            Date date = df.parse(SystemClock.);
//            df.setTimeZone(TimeZone.getTimeZone("UTC"));
//            formattedDate = df.format(date);
//        } catch (ParseException e) {
//
//            e.printStackTrace();
//        }
//
//        return formattedDate;
//
//        return formattedDate;
//
//
//    }

//    public String formatDate(Date date) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return sdf.format(date);
//    }
//
//    public String formatDate(long timestamp) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return sdf.format(timestamp);
//    }
//
//    public Date getCurrentTime(){
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return sdf.parse(new Date());
//    }
    public static boolean timeStampExpired(int timeStamp) {
        return (SystemClock.currentThreadTimeMillis() - timeStamp > 0);
    }

    /**
     * Return time in millis of date passed
     *
     * @param date
     * @return
     */
    public long getTimeInMillis(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar mCal = Calendar.getInstance();

        try {
            mCal.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mCal.getTimeInMillis();

    }

    /**
     * Return time in 10:00AM format
     *
     * @param date
     * @return
     */
    public String getTime(String date) {

        SimpleDateFormat sdfTime = new SimpleDateFormat("h:mma");
        Calendar mCal = Calendar.getInstance();

        mCal.setTimeInMillis(getTimeInMillis(date));

        return sdfTime.format(mCal.getTime());

    }


    /**
     * Convert UTC to local time
     *
     * @param time
     * @return
     */
//    public String convertUTCtoMyTime(String time) {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//        Date date = null;
//        try {
//            date = sdf.parse(time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        sdf.setTimeZone(TimeZone.getDefault());
//
//        SimpleDateFormat sdfNew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        return sdfNew.format(date);
//    }

    /**
     * Convert local time to UTC timezone
     *
     * @param time
     * @return
     */
//    public String convertMyTimeToUTC(String time) {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", TimeZone.getTimeZone("UTC"));
//        Calendar mCal = Calendar.getInstance();
//
//        mCal.setTimeInMillis(getTimeInMillis(time));
//        return sdf.format(mCal.getTime());
//    }

    /**
     * Format given date with given format
     *
     * @param format
     * @param date
     * @return
     */
    public String formatDate(String format, String date) {

        SimpleDateFormat sdfTime = new SimpleDateFormat(format);
        Calendar mCal = Calendar.getInstance();

        mCal.setTimeInMillis(getTimeInMillis(date));

        return sdfTime.format(mCal.getTime());
    }

}
