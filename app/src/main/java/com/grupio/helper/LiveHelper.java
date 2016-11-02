package com.grupio.helper;

import android.content.Context;

import com.grupio.data.LiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 2/11/16.
 */

public class LiveHelper {

    public List<LiveData> parseList(Context mcontext, String response) {

        List<LiveData> mLiveFeedList = new ArrayList<>();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonObject != null) {

           /*
            Store version in db
             */
//            try {
//                String version = jsonObject.getString("version");
//                VersionData vData = new VersionData();
//                vData.name = VersionDao.LIVEFEED_VERSION;
//                vData.oldVersion = version;
//                VersionDao.getInstance(mcontext).insertDataInOldColumn(vData);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            JSONArray jsonArray = null;
            try {
                jsonArray = jsonObject.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (jsonArray != null && jsonArray.length() > 0) {

                LiveData liveData;
                for (int i = 0; i < jsonArray.length(); i++) {
                    liveData = new LiveData();

                    try {
                        liveData.setUrl(jsonArray.getJSONObject(i).getString("url"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        liveData.setId(jsonArray.getJSONObject(i).getString("livefeed_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        liveData.setImageUrl(jsonArray.getJSONObject(i).getString("img_url"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        liveData.setName(jsonArray.getJSONObject(i).getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mLiveFeedList.add(liveData);
                }

            }

        }

        return mLiveFeedList;

    }
}
