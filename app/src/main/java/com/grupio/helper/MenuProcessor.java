package com.grupio.helper;

import android.content.Context;
import android.util.Log;

import com.grupio.apis.ApiInter;
import com.grupio.dao.MenuDAO;
import com.grupio.dao.VersionDao;
import com.grupio.data.MenuData;
import com.grupio.data.VersionData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by JSN on 22/8/16.
 */
public class MenuProcessor {

    public List<MenuData> processResult(String response, Context mcontext) {

        List<MenuData> mDataList = new ArrayList<>();

//        JSONArray jArray ;



//        JSONObject jObj = null;
//        try {
//            jObj = new JSONObject(response);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        try {
//            VersionData vData = new VersionData();
//            vData.name = "menu";
//            vData.oldVersion = jObj.getString("version");
//
//            VersionDao.getInstance(mcontext).insertDataInOldColumn(vData);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        JSONArray mArray = null;
        try {
            mArray = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mArray != null && mArray.length() > 0) {

            MenuData mData;
            for (int i = 0; i < mArray.length(); i++) {
                mData = new MenuData();
                try {
                    mData.setMenuName(mArray.getJSONObject(i).getString("menu_name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    mData.setMenuOrder(mArray.getJSONObject(i).getString("menu_order"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    mData.setmDisplayName(mArray.getJSONObject(i).getString("display_name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    mData.setMenuIconUrl(mArray.getJSONObject(i).getString("menuicon_url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mDataList.add(mData);
            }
        }
        return mDataList;
    }

    public static final String TAG ="MenuProcessoe";
    public List<ApiInter> fetchMenuListToDownload(Context mContext) {

        Log.i(TAG, "fetchMenuListToDownload: ");

        List<MenuData> mDataList = new ArrayList<>();
        Log.i(TAG, "fetchMenuListToDownload: fetch from db");
        mDataList.addAll(MenuDAO.getInstance(mContext).fetchMenu());


        Log.i(TAG, "fetchMenuListToDownload: fetch complete size:" + mDataList.size());

        Log.i(TAG, "fetchMenuListToDownload: remove extra logistics apis");
        int[] indexLogistics = new int[7];
        int counter = 0;
        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getMenuName().equals("logistics")
                    || mDataList.get(i).getMenuName().equals("logistics1")
                    || mDataList.get(i).getMenuName().equals("logistics2")
                    || mDataList.get(i).getMenuName().equals("logistics3")
                    || mDataList.get(i).getMenuName().equals("logistics4")
                    || mDataList.get(i).getMenuName().equals("logistics5")
                    || mDataList.get(i).getMenuName().equals("logistics6")) {
                indexLogistics[counter] = i;
                counter++;
            }
        }

        for (int i = 1; i < indexLogistics.length; i++) {
            if (indexLogistics[i] != 0) {
                mDataList.remove(indexLogistics[i] - (i-1));
            }
        }

        Log.i(TAG, "fetchMenuListToDownload: removed extra logistics apis. size:" + mDataList.size());

        Log.i(TAG, "fetchMenuListToDownload: get instances");

        List<ApiInter> mlist = new ArrayList<>();

        if (mDataList != null && mDataList.size() > 0) {
            MenuListManager mMenuManager = new MenuListManager();

            for (int j = 0; j < mDataList.size(); j++) {
                mlist.add(mMenuManager.getApiInstance(mDataList.get(j).getMenuName(),mContext));
            }

            Log.i(TAG, "fetchMenuListToDownload: remove null instances");
            mlist.removeAll(Collections.singleton(null));
        }

        Log.i(TAG, "fetchMenuListToDownload: complete");
        return mlist;
    }

}
