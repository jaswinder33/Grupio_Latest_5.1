package com.grupio.backend;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.text.TextUtils;

import com.grupio.dao.SessionDAO;
import com.grupio.data.ScheduleData;
import com.grupio.notes.NotesData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by JSN on 9/11/16.
 */

public class DateTime {

    public static DateTime getInstance() {
        return new DateTime();
    }

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
     * Get current time with UTC timezone
     *
     * @return
     */
    public String currentTimeInTimeZone(String timeZone) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone(timeZone));
        String formattedDate = "";

        try {
            formattedDate = df.format(Calendar.getInstance(TimeZone.getTimeZone(timeZone)).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;

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

    public int getDateVarialbe(int column, String date, String timezoneStr) {

        TimeZone timeZone = TimeZone.getTimeZone(timezoneStr);

        SimpleDateFormat srcFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mma");
        srcFormat.setTimeZone(timeZone);
        Date srcDate = null;
        try {
            srcDate = srcFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeZone(timeZone);
        mCalendar.setTime(srcDate);

        return mCalendar.get(column);

    }

    public String updateDate(String newDate, String oldDate, String timezoneStr, boolean isDate) {

        TimeZone timeZone = TimeZone.getTimeZone(timezoneStr);

        SimpleDateFormat srcFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mma");
        srcFormat.setTimeZone(timeZone);
        Date oldDateGenereated = null;
        try {
            oldDateGenereated = srcFormat.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeZone(timeZone);
        mCalendar.setTime(oldDateGenereated);

        if (isDate) {
            String[] dateArr = newDate.split("-");
            mCalendar.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]));
        } else {
            String[] timeArr = newDate.split(":");
            mCalendar.set(Calendar.HOUR, Integer.parseInt(timeArr[0]));
            mCalendar.set(Calendar.MINUTE, Integer.parseInt(timeArr[1]));
        }

        return srcFormat.format(mCalendar.getTime());

    }


    private String get2digitFormat(int value) {
        return String.format("%02d", value);
    }


    public <T> String saveToCalendar(Context mContext, T data) {

        String startTimeStr;
        String endTimeStr;
        String title;
        String description;
        SimpleDateFormat sdf1;
        String id;

        if (data instanceof ScheduleData) {
            ScheduleData mScheduleData = (ScheduleData) data;
            startTimeStr = mScheduleData.getStart_time();
            endTimeStr = mScheduleData.getEnd_time();
            title = mScheduleData.getName();
            description = mScheduleData.getSummary();
            id = mScheduleData.getSession_id();
            sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        } else {
            NotesData noteData = (NotesData) data;
            startTimeStr = noteData.getNoteDate();
            endTimeStr = noteData.getNoteDate();
            title = "My Note";
            description = noteData.getNoteText();
            id = noteData.getNoteId();

            sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mma");
        }

        Calendar starttime = Calendar.getInstance();
        Calendar endtime = Calendar.getInstance();

        try {
            starttime.setTime(sdf1.parse(startTimeStr));
            endtime.setTime(sdf1.parse(endTimeStr));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        ContentResolver cr = mContext.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, starttime.getTimeInMillis());
        values.put(CalendarContract.Events.DTEND, endtime.getTimeInMillis());
        values.put(CalendarContract.Events.TITLE, title);
        values.put(CalendarContract.Events.DESCRIPTION, description);
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.HAS_ALARM, 1);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

        Uri uri = null;
        if (Permissions.getInstance().hasCalendarPermission((Activity) mContext).permissions.size() == 0) {
            uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        }

        String eventId = uri != null ? uri.getLastPathSegment() : "0";

        SessionDAO.getInstance(mContext).persistCalendarId(eventId, id);

        setReminder(mContext, eventId);
        return eventId;
    }

    public <T> void removeFromCalendar(Context mContext, T data) {

        String eventID = "";

        if (data instanceof ScheduleData) {
            ScheduleData mScheduleData = (ScheduleData) data;
            eventID = mScheduleData.getCalenderAddId();
        } else {
            NotesData noteData = (NotesData) data;
            eventID = noteData.getNoteReminder();
        }

        if (eventID != null && !TextUtils.isEmpty(eventID) && !eventID.equals("0")) {
            ContentResolver cr = mContext.getContentResolver();
            Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, Long.valueOf(eventID));
            cr.delete(deleteUri, null, null);
        }
    }

    public String setReminder(Context mContext, String eventId) {

        if (eventId.equals("0")) {
            return "";
        }

        ContentResolver cr = mContext.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.MINUTES, 15);
        values.put(CalendarContract.Reminders.EVENT_ID, eventId);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        if (Permissions.getInstance().hasCalendarPermission((Activity) mContext).permissions.size() == 0) {
            Uri uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
        }


        return "";
    }

}
