package com.grupio.helper;

import android.content.Context;

import com.grupio.dao.VersionDao;
import com.grupio.data.LogisticsData;
import com.grupio.data.VersionData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 24/10/16.
 */

public class LogisticsHelper {

    public List<LogisticsData> parseData(Context mContext, String response) {

        List<LogisticsData> mLogisticsList = new ArrayList<>();

        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jObj != null) {

            try {
                VersionData vData = new VersionData();
                vData.name = VersionDao.LOGISTICS_VERSION;
                vData.oldVersion = jObj.getString("version");
                VersionDao.getInstance(mContext).insertDataInOldColumn(vData);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray jArray = null;
            try {
                jArray = jObj.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jArray != null && jArray.length() > 0) {

                LogisticsData mLogisticsData;
                for (int i = 0; i < jArray.length(); i++) {
                    mLogisticsData = new LogisticsData();

                    try {
                        mLogisticsData.setLogistics_id(jArray.getJSONObject(i).getString("logistics_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mLogisticsData.setName(jArray.getJSONObject(i).getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mLogisticsData.setUrl(jArray.getJSONObject(i).getString("url"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mLogisticsData.setDoctype(jArray.getJSONObject(i).getString("doctype"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mLogisticsData.setType(jArray.getJSONObject(i).getString("type"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mLogisticsData.setOrder(jArray.getJSONObject(i).getString("order"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mLogisticsList.add(mLogisticsData);
                }


            }

        }


        return mLogisticsList;
    }

}
