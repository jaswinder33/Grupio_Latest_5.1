package com.grupio.helper;

import android.content.Context;


import com.grupio.Utils.Utility;
import com.grupio.dao.VersionDao;
import com.grupio.data.EventData;
import com.grupio.data.VersionData;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JSN on 22/8/16.
 */
public class EventDataProcessor {

    public EventData parseResult(String result,Context mContext){

        EventData data = new EventData();

        JSONObject jObj = null;
        try {
             jObj = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
        Save version in version api
         */
        if(jObj != null){
            try {
                VersionData vData = new VersionData();
                vData.name="event";
                vData.oldVersion=jObj.getString("version");
                VersionDao.getInstance(mContext).insertDataInOldColumn(vData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            JSONArray jArray = new JSONObject(result).getJSONArray("data");
            if (jArray != null && jArray.length() > 0) {


                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObject = jArray.getJSONObject(i);

                    try {
                        data.setEvent_id(jObject.getString("event_id").trim());
                        ConstantData.EVENT_ID = jObject.getString("event_id").trim();
                    } catch (Exception e) {
                    }

                    try {
                        data.setEvent_name(jObject.getString("name").trim());
                    } catch (Exception e) {
                    }

                    try {
                        data.setStart_date(Utility.servertoClientString(jObject.getString("start_date").trim()));
                    } catch (Exception e) {
                    }

                    try {
                        data.setEnd_date(Utility.servertoClientString(jObject.getString("end_date").trim()));
                    } catch (Exception e) {
                    }
                    try {
                        data.setDescription(jObject.getString("description").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setImageURL(jObject.getString("image").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setLarge_image(jObject.getString("large_image").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setWeb_logo_image(jObject.getString("web_logo_image").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setStatic_map_url(jObject.getString("static_map_url").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setAddress1(jObject.getString("address1").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setAddress2(jObject.getString("address2").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setVenue(jObject.getString("venue"));
                    } catch (Exception e) {
                    }
                    try {
                        data.setCity(jObject.getString("city").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setState(jObject.getString("state").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setCountry(jObject.getString("country").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setZipcode(jObject.getString("zipcode").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setTimezone(jObject.getString("timezone").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setRegister_url(jObject.getString("register_url").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setHashtag(jObject.getString("hashtag").trim());
                    } catch (Exception e) {
                    }
                    String loginRequired = null;
                    try {
                        loginRequired = jObject.getString("login_required");
                        data.setLoginRequired(loginRequired);
                        Preferences.getInstances(mContext).setLoginRequired(loginRequired.equals("y") ? true : false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        data.setRegistration_required(jObject.getString("registration_required").trim());
                    } catch (Exception e) {
                    }

                    try {
                        data.setAboutText(jObject.getString("about_text").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setLattitude(jObject.getString("latitude").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setLongitude(jObject.getString("longitude").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setAbout_section_text(jObject.getString("about_section_text").trim());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        data.setSocial_section_text(jObject.getString("social_section_text").trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        data.setSurvey_login_req(jObject.getString("survey_login_req").trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        data.setMycalendar_login_required(jObject.getString("mycalendar_login_required").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setMyschedule_login_enabled(jObject.getString("myschedule_login_enabled").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setHide_attendee_info(jObject.getString("hide_attendee_contact_info"));
                    } catch (Exception e) {
                    }
                    try {
                        data.setHide_speaker_info(jObject.getString("hide_speaker_contact_info"));
                    } catch (Exception e) {
                    }
                    try {
                        data.setHide_exhibitor_images(jObject.getString("hide_exhibitor_images").trim());
                        data.setHide_sponsor_images(jObject.getString("hide_sponsor_images").trim());
                        data.setHide_speaker_images(jObject.getString("hide_speaker_images").trim());
                        data.setHide_attendee_images(jObject.getString("hide_attendee_images").trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        data.setEvent_color(jObject.getString("color_theme").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setNotes_login_required(jObject.getString("notes_login_required").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setAttendee_login_required(jObject.getString("attendee_login_required").trim());
                    } catch (Exception e) {
                    }

                    try {
                        data.setLocale(jObject.getString("locale"));
                        Preferences.getInstances(mContext).setLocale(data.getLocale());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        data.setShow_attendee_sessions(jObject.getString("show_attendee_sessions").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setShow_schedule_button(jObject.getString("show_schedule_button").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setShow_myschedule_button(jObject.getString("show_myschedule_button").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setShow_notes_button(jObject.getString("show_notes_button").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setName_order(jObject.getString("name_order").trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        data.setSort_order(jObject.getString("sort_order").trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        data.setEvent_website_url(jObject.getString("event_website_url").trim());
                    } catch (Exception e) {
                    }
                    try {
                        data.setEnable_activity_feed(jObject.getString("enable_activity_feed"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        data.setAllow_to_post_feed(jObject.getString("allowed_to_post_feed"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        data.setModeratorAvailable(jObject.getString("photo_gallery_moderator_enable").trim());
                    } catch (Exception e) {
                    }

                    try {
                        data.setShowTracks(jObject.getString("tracks"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        data.setAppVersion(jObject.getString("version"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        data.setForceDelete(jObject.getString("force_delete"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        data.setForceUpdate(jObject.getString("force_upgrade"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;

    }

}
