package com.grupio.helper;

import android.content.Context;

import com.grupio.dao.VersionDao;
import com.grupio.data.ScheduleData;
import com.grupio.data.TrackData;
import com.grupio.data.VersionData;
import com.grupio.data.mapList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 24/8/16.
 */
public class ScheduleHelper {


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
                        sd.setMaxSeatsAvailable(jsonArray.getJSONObject(i).getString("max_limit_of_attendees"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {

                        JSONArray speakerList = jsonArray.getJSONObject(i).getJSONArray("speaker_id");
                        sd.setSpeakerListAsString(speakerList.toString());

                        //compatibility with old code
                        try {
                            JSONArray jarray = speakerList;

                            if (jarray != null && jarray.length() != 0) {
                                String[] speakersArray = new String[jarray.length()];

                                for (int j = 0; j < jarray.length(); j++) {
                                    speakersArray[j] = jarray.getString(j);
                                }
                                sd.setSpeakes(speakersArray);
                            }
                        } catch (Exception e) {

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {

                        JSONArray sessionlinks = jsonArray.getJSONObject(i).getJSONArray("sessionlinks");

                        sd.setResourceListAsString(sessionlinks.toString());

                        // compatible with old code

                        List<mapList> mapList = null;

                        try {

                            JSONArray jarray = sessionlinks;

                            if (jarray != null && jarray.length() != 0) {
                                mapList = new ArrayList<mapList>();

                                for (int j = 0; j < jarray.length(); j++) {

                                    JSONObject sessionMapObject = jarray.getJSONObject(j);

                                    mapList mapdata = new mapList();
                                    mapdata.setMapId(sd.getSession_id());
                                    mapdata.setMapName(sessionMapObject.getString("name").trim());
                                    mapdata.setMapUrl(sessionMapObject.getString("url").trim());
                                    mapdata.setMapType(sessionMapObject.getString("type").trim());
                                    try {
                                        mapdata.setLoginRequired(sessionMapObject.getString("login_required"));
                                    } catch (Exception e) {

                                    }

                                    mapList.add(mapdata);
                                }
                            }

                            sd.setMapListOfSche(mapList);

                        } catch (Exception e) {

                        }


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

    public List<mapList> getSessionLinks(String response) {

        List<mapList> mapList = new ArrayList<>();

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

                mapList mapdata = new mapList();
                mapdata.setMapId(j + "");
                try {
                    mapdata.setMapName(sessionMapObject.getString("name").trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    mapdata.setMapUrl(sessionMapObject.getString("url").trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    mapdata.setMapType(sessionMapObject.getString("type").trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    mapdata.setLoginRequired(sessionMapObject.getString("login_required"));
                } catch (Exception e) {

                }

                mapList.add(mapdata);
            }
        }
        return mapList;
    }

}
