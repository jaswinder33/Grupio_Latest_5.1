package com.grupio.helper;

import android.content.Context;

import com.grupio.data.SurveyData;

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


        }

        return mSurveyList;

    }
}
