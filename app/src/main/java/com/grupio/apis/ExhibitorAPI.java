package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.session.ConstantData;

import java.util.HashMap;


/**
 * Created by JSN on 1/9/16.
 */
public class ExhibitorAPI extends BaseApiCall {

    public ExhibitorAPI(Context context) {
        super(context);
    }

    @Override
    public void run() {
        super.run();

        String url = ConstantData.EXHIBITORS_API + ConstantData.EVENT_ID + "&format=json";

        APIRequest request = new GetRequest();
        String response =  request.requestResponse(url, new HashMap<String, String>(), mContext);

//        if (response != null) {
//            ExhibitorDAO.getInstance(mContext).insert(response);
//        }


        Log.i("API", "ExhibitorAPI");

    }
}
