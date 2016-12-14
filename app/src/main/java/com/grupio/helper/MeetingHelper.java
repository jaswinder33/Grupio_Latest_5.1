package com.grupio.helper;

import com.grupio.data.MeetingData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 14/12/16.
 */

public class MeetingHelper {

    public List<MeetingData> parseData(String response) {

        List<MeetingData> meetingDataList = new ArrayList<>();

        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String currentData = "";
        if (jObj != null) {
            try {
                currentData = jObj.getString("current_date");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray jArray = null;
            try {
                jArray = jObj.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            MeetingData meetingData;
            if (jArray != null && jArray.length() > 0) {

                for (int i = 0; i < jArray.length(); i++) {

                    meetingData = new MeetingData();
                    meetingData.currentDate = currentData;

                    try {
                        meetingData.meetingDate = jArray.getJSONObject(i).getString("meeting_date");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONArray jArrayInner1 = null;
                    try {
                        jArrayInner1 = jArray.getJSONObject(i).getJSONArray("meeting_data");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (jArrayInner1 != null && jArrayInner1.length() > 0) {

                        for (int j = 0; j < jArrayInner1.length(); j++) {
                            try {
                                meetingData.id = jArrayInner1.getJSONObject(j).getString("meeting_id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                meetingData.creator = jArrayInner1.getJSONObject(j).getString("creater_attendee_id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                meetingData.title = jArrayInner1.getJSONObject(j).getString("title");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                meetingData.location = jArrayInner1.getJSONObject(j).getString("location");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                meetingData.description = jArrayInner1.getJSONObject(j).getString("description");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                meetingData.meetingTime = jArrayInner1.getJSONObject(j).getJSONArray("meetingtime").toString();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                JSONArray inviteesArray = jArrayInner1.getJSONObject(j).getJSONArray("invitees");

                                JSONArray collectiveArray = new JSONArray();
                                if (inviteesArray != null && inviteesArray.length() > 0) {
                                    JSONObject collectiveObj;
                                    for (int k = 0; k < inviteesArray.length(); k++) {
                                        collectiveObj = new JSONObject();
                                        collectiveObj.put("id", inviteesArray.getJSONObject(k).getString("attendee_id"));
                                        collectiveObj.put("status", inviteesArray.getJSONObject(k).getJSONObject("status").getString("time1"));
                                        collectiveArray.put(k, collectiveObj);
                                    }
                                }

                                meetingData.invities = collectiveArray.toString();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            meetingDataList.add(meetingData);
                        }
                    }
                }
            }
        }

        return meetingDataList;
    }


    public String parseInvitationData(String response) {

        System.out.println("trigger triggered");
        return "1,2";
    }

    /*meetingtime": [
            {
              "start_time": "08:00:00",
              "end_time": "09:00:00",
              "room": "1"
            }
          ]
          */
    public void parseMeetingTime(String resonse) {


    }
}
