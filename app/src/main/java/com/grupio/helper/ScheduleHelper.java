package com.grupio.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.grupio.animation.SlideOut;
import com.grupio.apis.LikeUnlikeSessionAPI;
import com.grupio.dao.EventDAO;
import com.grupio.dao.VersionDao;
import com.grupio.data.LogisticsData;
import com.grupio.data.ScheduleData;
import com.grupio.data.TrackData;
import com.grupio.data.VersionData;
import com.grupio.db.EventTable;
import com.grupio.session.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by JSN on 24/8/16.
 */
public class ScheduleHelper {

    public static boolean isLoginRequiredToLike(Context mContext, Class<?> mClassName) {

        if (EventDAO.getInstance(mContext).getValue(EventTable.MYSCHEDULE_LOGIN_ENABLED).equals("y") && Preferences.getInstances(mContext).getAttendeeId() == null) {
            Intent mIntent = new Intent();
            mIntent.setClass(mContext, mClassName);
            mIntent.putExtra("from", mClassName.getSimpleName());
            mContext.startActivity(mIntent);
            SlideOut.getInstance().startAnimation((Activity) mContext);
            return true;
        }

        return false;
    }


    public static void addRemoveSession(String operation, String sessionId, Context mContext) {
        new LikeUnlikeSessionAPI(mContext).doCall(operation, sessionId);
    }

    // 09:00AM - 10:10AM
    public static String formatSessionDate(String startDate, String endDate) {
        return formatSessionDate(startDate, endDate, "yyyy-MM-dd HH:mm:ss");
    }

    // 09:00AM - 10:10AM
    public static String formatSessionDate(String startDate, String endDate, String format) {
        String date = "";

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Calendar mCalStart = Calendar.getInstance();
        Calendar mCalEnd = Calendar.getInstance();

        try {
            mCalStart.setTime(sdf.parse(startDate));
            mCalEnd.setTime(sdf.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final SimpleDateFormat sdfTime = new SimpleDateFormat("h:mma");
        Calendar calFinal = Calendar.getInstance();

        calFinal.setTimeInMillis(mCalStart.getTimeInMillis());
        date = sdfTime.format(calFinal.getTime()) + " - ";

        calFinal.setTimeInMillis(mCalEnd.getTimeInMillis());
        date += sdfTime.format(calFinal.getTime());

        return date;
    }


    public List<ScheduleData> parseJSON(Context mContext, String response) {

        List<ScheduleData> mList = new ArrayList<>();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonObject != null) {

            try {
                VersionData vData = new VersionData();
                vData.name = VersionDao.SCHEDULE_VERSION;
                vData.newVersion = "";
                vData.oldVersion = jsonObject.getString("version");
                VersionDao.getInstance(mContext).insertDataInOldColumn(vData);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            JSONArray jsonArray = null;
            try {
                jsonArray = jsonObject.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (jsonArray != null && jsonArray.length() > 0) {

                ScheduleData sd;
                for (int i = 0; i < jsonArray.length(); i++) {
                    sd = new ScheduleData();

                    try {
                        sd.setSession_id(jsonArray.getJSONObject(i).getString("session_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        sd.setName(jsonArray.getJSONObject(i).getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        sd.setStart_time(jsonArray.getJSONObject(i).getString("start_time"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        sd.setEnd_time(jsonArray.getJSONObject(i).getString("end_time"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        sd.setTrack(jsonArray.getJSONObject(i).getString("track"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        sd.setSummary(jsonArray.getJSONObject(i).getString("summary"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        sd.setLocation(jsonArray.getJSONObject(i).getString("location"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        sd.setParent_session_id(jsonArray.getJSONObject(i).getString("parent_session_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        sd.setHas_child(jsonArray.getJSONObject(i).getString("has_child"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        sd.setMaxSeatsAvailable(jsonArray.getJSONObject(i).getString("max_limit_of_attendees"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {

                        JSONArray speakerList = jsonArray.getJSONObject(i).getJSONArray("speaker_id");
                        sd.setSpeakerListAsString(speakerList.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray sessionlinks = jsonArray.getJSONObject(i).getJSONArray("sessionlinks");
                        sd.setResourceListAsString(sessionlinks.toString());
                    } catch (JSONException e) {
//                        e.printStackTrace();
                    }

                    mList.add(sd);
                }
            }
        }

        return mList;
    }

    public ArrayList<ScheduleData> getTrackListFromJSON(String str) throws Exception {

        ArrayList<ScheduleData> trackDataList = null;

        try {
            trackDataList = new ArrayList<ScheduleData>();

            JSONArray jArray = new JSONObject(str).getJSONArray("data");

            if (jArray != null && jArray.length() > 0) {
                ScheduleData data = null;

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObject = jArray.getJSONObject(i);
                    data = new ScheduleData();

                    try {
                        data.setTrack(jObject.getString("track").trim());
                    } catch (Exception e) {

                    }

                    try {
                        data.setColor(jObject.getString("color").trim());
                    } catch (Exception e) {

                    }

                    try {
                        data.setOrder(jObject.getString("order_no").trim());
                    } catch (Exception e) {

                    }

                    trackDataList.add(data);
                }
            }
        } catch (Exception e) {

        }

        return trackDataList;
    }

    public List<TrackData> parseTrackList(String response) {


        List<TrackData> mList = new ArrayList<>();


        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonObject != null) {

            JSONArray jArray = null;
            try {
                jArray = jsonObject.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jArray != null && jArray.length() > 0) {

                TrackData td;

                for (int i = 0; i < jArray.length(); i++) {

                    td = new TrackData();

                    try {
                        td.id = jArray.getJSONObject(i).getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        td.track = jArray.getJSONObject(i).getString("track");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        td.color = jArray.getJSONObject(i).getString("color");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        td.category = jArray.getJSONObject(i).getString("category");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        td.order = jArray.getJSONObject(i).getString("order_no");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mList.add(td);
                }
            }
        }

        return mList;

    }

    public List<String> getSpeakerList(String resposne) {

        List<String> mList = new ArrayList<>();

        JSONArray jArray = null;
        try {
            jArray = new JSONArray(resposne);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jArray != null && jArray.length() > 0) {
            for (int i = 0; i < jArray.length(); i++) {
                try {
                    mList.add(jArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return mList;

    }

    public List<LogisticsData> getSessionLinks(String response) {

        List<LogisticsData> mapList = new ArrayList<>();

        JSONArray jarray = null;
        try {
            jarray = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jarray != null && jarray.length() > 0) {

            for (int j = 0; j < jarray.length(); j++) {

                JSONObject sessionMapObject = null;
                try {
                    sessionMapObject = jarray.getJSONObject(j);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                LogisticsData mapdata = new LogisticsData();
                mapdata.setLogistics_id(j + "");
                try {
                    mapdata.setName(sessionMapObject.getString("name").trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    mapdata.setUrl(sessionMapObject.getString("url").trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    mapdata.setType(sessionMapObject.getString("type").trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    mapdata.setLoginRequired(sessionMapObject.getString("login_required").equals("y"));
                } catch (Exception e) {

                }

                mapList.add(mapdata);
            }
        }
        return mapList;
    }


}
