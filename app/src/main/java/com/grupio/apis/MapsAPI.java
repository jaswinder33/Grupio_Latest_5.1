package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.dao.MapsDAO;
import com.grupio.data.MapsData;
import com.grupio.helper.MapDataProcessor;
import com.grupio.session.ConstantData;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JSN on 22/8/16.
 */
public class MapsAPI extends BaseApiCall {

    public MapsAPI(Context mContext) {
        super(mContext);
    }

    @Override
    public String getEndPoint() {
        return mContext.getString(R.string.maps_api) + ConstantData.EVENT_ID;
    }

    @Override
    public void callApi() {
//        String url = ConstantData.MAPS_API + ConstantData.EVENT_ID + ConstantData.API_FORMAT;

        APIRequest request = new GetRequest();
        String resposne = request.requestResponse(url, new HashMap<String, String>(), mContext);

        if (resposne != null) {
            MapsDAO.getInstance(mContext).insertData(resposne);
            ArrayList<MapsData> mmapdata = MapDataProcessor.getSponsorListFromJSON(mContext, resposne);

            if(mmapdata != null && mmapdata.size() > 0){
                for(int i=0; i<mmapdata.size();i++){

                    String name = "";
                    String mapUrl = mmapdata.get(i).getUrl();

                    if(!mapUrl.equals("")){
                        downloadResource(mmapdata.get(i).getName(), mapUrl);
                    }
                }
            }
        }

        Log.i("API", "MapsAPI");
    }


    public void downloadResource(final String fileName, final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Dot is used to hide the folder
                Utility.downloadFile(mContext, url, fileName, mContext.getString(R.string.Resources) + File.separator + mContext.getString(R.string.Maps), "");
            }
        }).start();
    }



}
