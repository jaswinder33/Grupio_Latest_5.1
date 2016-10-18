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
public class AttendeeAPI extends BaseApiCall {

    public AttendeeAPI(Context context) {
        super(context);
    }

    @Override
    public void run() {
        super.run();

        String url = ConstantData.ATTENDEES_API + ConstantData.EVENT_ID + "&format=json";
        APIRequest request = new GetRequest();
        String response = request.requestResponse(url, new HashMap<String, String>(), mContext);


//        String response = GridHome.ut_obj.postData(url, new ArrayList<NameValuePair>(), mContext);

//        if(response != null){
//            //insert new attendees in db
//            AttendeeDAO.getInstance(mContext).insert(response);
//        }

        Log.i("API", "AttendeeListAPI");
    }
}
