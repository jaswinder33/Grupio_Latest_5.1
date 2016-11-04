package com.grupio.helper;

import android.util.Log;

import com.grupio.data.AlertData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by JSN on 2/11/16.
 */

public class AlertHelper {

    public List<AlertData> parseList(String response) {

        List<AlertData> mAlertList = new ArrayList<>();

        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jObj != null) {

            JSONArray jArray = null;
            try {
                jArray = jObj.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jArray != null && jArray.length() > 0) {
                AlertData mData;
                for (int i = 0; i < jArray.length(); i++) {
                    mData = new AlertData();

                    try {
                        mData.setAlertId(jArray.getJSONObject(i).getString("alert_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {

                        String date = jArray.getJSONObject(i).getString("datetime");
//                        mData.setDate(jArray.getJSONObject(i).getString("datetime"));

                        try {
                            //to set the format Nov 06,2011
                            String[] months = {"Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};

                            String dateNew = date.substring(0, 10);
                            Log.e("Date for notification", date);


                            //to set the time...

                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Calendar time_cal = Calendar.getInstance();
                            try {
                                time_cal.setTime(sdf1.parse(date));
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }


                            long time = time_cal.getTimeInMillis();

                            String alert_time = getStartHour("" + time);
                            Log.e("Time for notification", alert_time);

                            //to set the Date...

                            Calendar cal = Calendar.getInstance();
                            cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
                            int dd = cal.get(Calendar.DATE);
                            String mm = months[cal.get(Calendar.MONTH)];
                            int yy = cal.get(Calendar.YEAR);
                            String eventDate = mm + " " + dd + " " + "," + " " + yy;

                            mData.setDate(eventDate + " " + "at" + " " + alert_time);


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setNotificationText(jArray.getJSONObject(i).getString("notification_text"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mData.setRead(jArray.getJSONObject(i).getString("is_read").equals("y"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mAlertList.add(mData);
                }
            }
        }

        return mAlertList;

    }

    String getStartHour(String str) {
        long time1 = Long.parseLong(str);
        //time*=1000;
        SimpleDateFormat sdf = new SimpleDateFormat("h:mma");
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(time1);
        return sdf.format(cal1.getTime());

    }


}
