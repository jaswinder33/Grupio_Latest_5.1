package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.Utils.Utility;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.dao.MenuDAO;
import com.grupio.data.MenuData;
import com.grupio.helper.MenuProcessor;
import com.grupio.session.ConstantData;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by JSN on 22/8/16.
 */
public class MenuAPI implements Callable<List<ApiInter>> {
    String response;
    private Context mContext;
    public static final String TAG ="MenuAPI";

    public MenuAPI(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public List<ApiInter> call() throws Exception {

        List<ApiInter> mList = new ArrayList<>();

        String url = ConstantData.MENU_API + ConstantData.EVENT_ID + ConstantData.API_FORMAT;

        APIRequest request = new GetRequest();
        response = request.requestResponse(url, new HashMap<String, String>(), mContext);

        Log.i(TAG, "MenuAPI");

        if (response != null) {
            MenuDAO.getInstance(mContext).insertData(response);
            Log.i(TAG, "MenuAPI: data inserted in db " );

            MenuProcessor mp = new MenuProcessor();

            List<MenuData> mDataList = new ArrayList<>();
            Log.i(TAG, "MenuAPI: fetch menu list " );
            mDataList.addAll(MenuDAO.getInstance(mContext).fetchMenu());

            Log.i(TAG, "MenuAPI: download menus icons " );
            for (int i = 0; i < mDataList.size(); i++) {
                String iconUrl = mDataList.get(i).getMenuIconUrl();

                if (iconUrl != null && !iconUrl.equals("")) {
                    downloadResource(mDataList.get(i).getMenuName(), iconUrl);
                }
            }


            //Add other important apis
            mList.add(new EventDetailAPI(mContext));
            mList.add(new GraphicsAPI(mContext));
            mList.addAll(mp.fetchMenuListToDownload(mContext));
            mList.add(new Ads(mContext));
            mList.add(new LocaleApi(mContext));
        }


        Log.i(TAG, "MenuAPI: return list size:" +  mList.size());
        return mList;
    }


    public void downloadResource(final String fileName, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Dot is used to hide the folder
                Utility.downloadFile(mContext, url, fileName, ConstantData.RESOURCES + File.separator + ConstantData.MENUS, "");
            }
        }).start();
    }

}
