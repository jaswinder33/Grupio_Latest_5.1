package com.grupio.helper;

import android.content.Context;

import com.grupio.dao.AttendeeDAO;
import com.grupio.data.AttendeesData;
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

    public List<AttendeesData> parseInvitations(String response, Context context) {
        List<AttendeesData> invitedAttendee = new ArrayList<>();
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jArray != null && jArray.length() > 0) {
            AttendeesData mAttendee;
            for (int i = 0; i < jArray.length(); i++) {
                mAttendee = new AttendeesData();
                try {
                    mAttendee.setAttendee_id(jArray.getJSONObject(i).getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAttendee = new AttendeesData(AttendeeDAO.getInstance(context).getAttendeeDetal(mAttendee.getAttendee_id()));

                try {
                    mAttendee.setMeetingStatus(jArray.getJSONObject(i).getString("status"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                invitedAttendee.add(mAttendee);
            }
        }

        return invitedAttendee;
    }

    /*meetingtime": [
            {
              "start_time": "08:00:00",
              "end_time": "09:00:00",
              "room": "1"
            }
          ]
          */
    public String[] parseMeetingTime(String resonse) {

        String startTime = "";
        String endTime = "";

        JSONArray timeArray = null;
        try {
            timeArray = new JSONArray(resonse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (timeArray != null && timeArray.length() > 0) {

            try {
                startTime = timeArray.getJSONObject(0).getString("start_time");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                endTime = timeArray.getJSONObject(0).getString("end_time");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return new String[]{startTime, endTime};

    }

    public String updateInvitiesStatus(int position, String status, String response) {


        JSONArray jArray = null;
        try {
            jArray = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //{"id":"73422936","status":"-1"}
        if (jArray != null && jArray.length() > 0) {
            JSONObject inivityObj = null;
            try {
                inivityObj = jArray.getJSONObject(position);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (inivityObj != null) {

                try {
                    String id = inivityObj.getString("id");

                    JSONObject newObj = new JSONObject();
                    newObj.put("id", id);
                    newObj.put("status", status);

                    JSONArray newArray = new JSONArray();
                    for (int i = 0; i < jArray.length(); i++) {

                        if (i == position) {
                            newArray.put(i, newObj);
                        } else {
                            newArray.put(i, jArray.getJSONObject(i));
                        }
                    }

                    return newArray.toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        return response;

    }

}
