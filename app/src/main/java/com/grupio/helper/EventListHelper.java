package com.grupio.helper;

import com.grupio.data.EventData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JSN on 5/9/16.
 */
public class EventListHelper {

    public List<EventData> parseResult(String result){

         List<EventData> eventList = new ArrayList<>();


//        JSONObject jsonObject = null;
//        try {
//            jsonObject = new JSONObject(result);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


//        if(jsonObject != null){
            JSONArray jArray = null;
            try {
                jArray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            EventData data = null;

            if(jArray!=null && jArray.length()>0){
                for(int i=0;i<jArray.length();i++){
                    data = new EventData();

                    JSONObject jObject= null;
                    try {
                        jObject = jArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(jObject != null){
                        try {
                            data.setEvent_id(jObject.getString("event_id").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setOrganization_id(jObject.getString("organization_id").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setApsn_org_id(jObject.getString("apsn_org_id").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setEvent_name(jObject.getString("name").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setStart_date(servertoClientString(jObject.getString("start_date").trim()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setEnd_date(servertoClientString(jObject.getString("end_date").trim()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setDescription(jObject.getString("description").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setImageURL(jObject.getString("image").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setAddress1(jObject.getString("address1").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setAddress2(jObject.getString("address2").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setCity(jObject.getString("city").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setState(jObject.getString("state").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setCountry(jObject.getString("country").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setZipcode(jObject.getString("zipcode").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setUser_id(jObject.getString("user_id").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setTimezone(jObject.getString("timezone").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setRegister_url(jObject.getString("register_url").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setHashtag(jObject.getString("hashtag").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setApsn_host_name(jObject.getString("apsn_host_name").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setApsn_admin_email(jObject.getString("apsn_admin_email").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setApsn_admin_password(jObject.getString("apsn_admin_password").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setModeratorAvailable(jObject.getString("photo_gallery_moderator_enable").trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        eventList.add(data);
                    }
                }
            }
//        }

        return eventList;

    }

    protected String servertoClientString(String simpleDate) {
        String serverString ="";
        try {
            SimpleDateFormat clientFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat serverFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date clientDate = clientFormat.parse(simpleDate);
            serverString = serverFormat.format(clientDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return serverString;
    }

}
