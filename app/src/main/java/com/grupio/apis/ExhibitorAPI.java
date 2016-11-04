package com.grupio.apis;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.dao.ExhibitorDAO;
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
    public String getEndPoint() {
        return mContext.getResources().getString(R.string.exhibitor_apis) + ConstantData.EVENT_ID;
    }

    @Override
    public void callApi() {
        APIRequest request = new GetRequest();
        String response = request.requestResponse(url, new HashMap<>(), mContext);
        Log.i("API", "ExhibitorAPI");

        if (response != null && !TextUtils.isEmpty(response)) {
            ExhibitorDAO.getInstance(mContext).insert(response);
        }

        //fetch exhibitorcategory.
        new ExhibitorCategoryAPI(mContext).doCall();
    }
}
