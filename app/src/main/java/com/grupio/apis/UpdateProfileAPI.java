package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.data.AttendeesData;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSN on 1/12/16.
 */

public class UpdateProfileAPI extends BaseAsyncTask<AttendeesData, Boolean> {

    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String COMPANY = "company";
    public static final String TITLE = "title";
    public static final String PRIMARY_PHONE = "primary_phone";
    public static final String EMAIL = "email";
    public static final String KEYWORD = "keywords";
    public static final String BIO = "bio";
    public static final String INTEREST = "intrests";
    public static final String ENABLE_MESSAGE = "enable_messaging";
    public static final String HIDE_PROFILE = "hide_attendee";
    public static final String ENABLE_CONTACT_INFO = "hide_contact_info";

    public UpdateProfileAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.update_profile_api) + ConstantData.EVENT_ID;
    }

    @Override
    public Boolean handleBackground(AttendeesData... params) {

        AttendeesData mAttendee = params[0];
        Map<String, String> mParamList = new HashMap<>();
        mParamList.put("fname", mAttendee.getFirst_name());
        mParamList.put("lname", mAttendee.getLast_name());
        mParamList.put("cmpny", mAttendee.getCompany());
        mParamList.put("title", mAttendee.getTitle());
        mParamList.put("phone", mAttendee.getPrimary_phone());
        mParamList.put("keywords", mAttendee.getKeywords());
        mParamList.put("bio", mAttendee.getBio());
        mParamList.put("intrests", mAttendee.getIntrests());
        mParamList.put("enableMessage", mAttendee.getEnable_messaging());
        mParamList.put("hideAttendee", mAttendee.getHideMe());
        mParamList.put("hideContactAttendee", mAttendee.getHide_contact_info());

        Log.i("UpdateProfile ", "handleBackground: " + JSONValue.toJSONString(mParamList));
        APIRequest mRequest = new CookieRequest(mContext);
        String response = mRequest.requestResponse(url, mParamList, mContext);
//{"status_code":0,"description":"Profile updated successfully"}

        if (response != null) {
            JSONObject mJson = null;
            try {
                mJson = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (mJson != null) {
                boolean status = false;
                try {
                    status = mJson.getString("status_code").equals("0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (status) {
                    updateUserProfile(mAttendee, mContext);
                    return true;
                }
            }
        }

        return false;
    }

    public void updateUserProfile(AttendeesData mData, Context mContext) {

        String userdata = Preferences.getInstances(mContext).getUserInfo();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(userdata);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (userdata != null) {
            JSONArray jArray = null;
            try {
                jArray = jsonObject.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jArray != null && jArray.length() > 0) {

                JSONObject jObj = null;
                try {
                    jObj = jArray.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jObj != null) {

                    try {
                        jObj.remove(FIRST_NAME);
                        jObj.put(FIRST_NAME, mData.getFirst_name());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        jObj.remove(LAST_NAME);
                        jObj.put(LAST_NAME, mData.getLast_name());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        jObj.remove(COMPANY);
                        jObj.put(COMPANY, mData.getCompany());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        jObj.remove(TITLE);
                        jObj.put(TITLE, mData.getTitle());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        jObj.remove(PRIMARY_PHONE);
                        jObj.put(PRIMARY_PHONE, mData.getPrimary_phone());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        jObj.remove(EMAIL);
                        jObj.put(EMAIL, mData.getEmail());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    jObj.remove(KEYWORD);
                    try {
                        jObj.put(KEYWORD, mData.getKeywords());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    jObj.remove(BIO);
                    try {
                        jObj.put(BIO, mData.getBio());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    jObj.remove(INTEREST);
                    try {
                        jObj.put(INTEREST, mData.getIntrests());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    jObj.remove(ENABLE_MESSAGE);
                    try {
                        jObj.put(ENABLE_MESSAGE, mData.getEnable_messaging());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    jObj.remove(HIDE_PROFILE);
                    try {
                        jObj.put(HIDE_PROFILE, mData.getHideMe());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    jObj.remove(ENABLE_CONTACT_INFO);
                    try {
                        jObj.put(ENABLE_CONTACT_INFO, mData.getHide_contact_info());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONArray mJsonArray = new JSONArray();

                    try {
                        jObj.put("data", jObj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                  /*  JSONArray mJsonArray = new JSONArray();
                    mJsonArray.put("data", jObj);*/


//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                        jArray.remove(0);
//                        jArray.put(jObj);
//                    }
                }
            }
        }
    }
}
