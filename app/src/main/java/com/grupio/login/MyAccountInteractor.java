package com.grupio.login;

import android.content.Context;
import android.text.TextUtils;

import com.grupio.apis.APICallBackWithResponse;
import com.grupio.apis.InterestAPI;
import com.grupio.apis.UpdateProfileAPI;
import com.grupio.apis.UploadImage;
import com.grupio.dao.EventDAO;
import com.grupio.data.AttendeesData;
import com.grupio.db.EventTable;
import com.grupio.helper.AttendeeProcessor;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.Preferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JSN on 30/11/16.
 */

public class MyAccountInteractor implements MyAccountContract.Interactor {

    @Override
    public void fetchUserInfo(Context mContext, MyAccountContract.OnInteraction mOnInteraction) {
        List<AttendeesData> mList = new ArrayList<>();
        mList.addAll(getUserData(mContext));
        boolean isFirstName = EventDAO.getInstance(mContext).getValue(EventTable.NAME_ORDER).equals("firstname");
        mOnInteraction.showDetails(mList.get(0), isFirstName);
        mOnInteraction.onInterestFetch(getAllInterest(mContext), getUserInterest(mList));
    }

    @Override
    public void updateImage(String url, Context mContext, MyAccountContract.OnInteraction mOnInteraction) {

        new UploadImage(mContext, new APICallBackWithResponse() {
            @Override
            public void onSuccess(String response) {
                mOnInteraction.onImageUpdated(response);
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String msg) {

            }
        }).doCall(url);

    }

    @Override
    public void updateUserInfo(AttendeesData mData, Context mContext, MyAccountContract.OnInteraction mOnInteraction) {
        new UpdateProfileAPI(mContext, new APICallBack() {
            @Override
            public void onSuccess() {
                mOnInteraction.onUserInfoUpdation();
            }

            @Override
            public void onFailure(String msg) {
                mOnInteraction.onFailure(msg);
            }
        }).doCall(mData);
    }

    @Override
    public void fetchInterest(Context mContext, MyAccountContract.OnInteraction mOnInteraction) {


        new InterestAPI(mContext, new APICallBack() {
            @Override
            public void onSuccess() {
                mOnInteraction.onInterestFetch(getAllInterest(mContext), getUserInterest(getUserData(mContext)));
            }

            @Override
            public void onFailure(String msg) {
                mOnInteraction.onFailure(msg);
            }
        }).doCall();
    }

    public List<String> parseData(String respone) {

        List<String> mList = new ArrayList<>();

        JSONArray jrr = null;
        try {
            jrr = new JSONArray(respone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jrr != null && jrr.length() > 0) {
            for (int i = 0; i < jrr.length(); i++) {
                try {
                    mList.add(jrr.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return mList;
    }

    public List<AttendeesData> getUserData(Context mContext) {
        String userInfo = Preferences.getInstances(mContext).getUserInfo();

        List<AttendeesData> mList = new ArrayList<>();

        AttendeeProcessor ap = new AttendeeProcessor();
        mList.addAll(ap.getAttendeesListFromJSON(mContext, userInfo, false));

        return mList;
    }

    public List<String> getUserInterest(List<AttendeesData> mList) {
        String response = mList.get(0).getIntrests();
        if (response != null && !TextUtils.isEmpty(response)) {
            String[] minterest = mList.get(0).getIntrests().split(",");
            return Arrays.asList(minterest);
        }

        return new ArrayList<>();
    }

    public List<String> getAllInterest(Context mContext) {
        String response = Preferences.getInstances(mContext).getInterests();
        if (response != null) {
            return parseData(response);
        }
        return new ArrayList<>();
    }
}
