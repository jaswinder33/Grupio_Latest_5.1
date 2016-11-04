package com.grupio.helper;

import android.content.Context;

import com.grupio.data.SurveyData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 2/11/16.
 */

public class SurveyHelper {

    public List<SurveyData> parseList(Context mContext, String response) {

        List<SurveyData> mSurveyList = new ArrayList<>();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonObject != null) {

            JSONArray jsonArray = null;
            try {
                jsonArray = jsonObject.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jsonArray != null && jsonArray.length() > 0) {

                SurveyData mSurveyData;
                for (int i = 0; i < jsonArray.length(); i++) {
                    mSurveyData = new SurveyData();

                    try {
                        mSurveyData.setName(jsonArray.getJSONObject(i).getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mSurveyData.setUrl(jsonArray.getJSONObject(i).getString("url"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mSurveyList.add(mSurveyData);

                }
            }

        }

        return mSurveyList;

    }
}
