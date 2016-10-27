package com.grupio.apis;

import android.content.Context;

import com.grupio.BuildConfig;
import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.data.EventData;
import com.grupio.helper.EventListHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JSN on 1/9/16.
 */
public class EventListAPI extends BaseCallable<List<EventData>> {

    private String queryText;
    private Context context;

    public EventListAPI(Context context, String queryText) {
        super(context);
        this.queryText=queryText;
    }

    @Override
    public String getEndPoint() {

        try {
            queryText = URLEncoder.encode(queryText, "UTF-16");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String queryString = "?keyword=" + queryText + "&orgid=" + BuildConfig.ORG_ID + "&format=json";
        return mContext.getString(R.string.event_list) + queryString;
    }

    @Override
    public List<EventData> callApi() {

        APIRequest request = new GetRequest();
        String response = request.requestResponse(url, new HashMap<>(), context);

        EventListHelper eHelper = new EventListHelper();

        return eHelper.parseResult(response);
    }

//    @Override
//    public List<EventData> call() throws Exception {
//
//        String queryString = "?keyword=" + queryText + "&orgid=" + ConstantData.ORG_ID + "&format=json";
//        String url = ConstantData.EVENT_LIST_API + queryString;
//
//        APIRequest request = new GetRequest();
//        String response = request.requestResponse(url, new HashMap<>(),context);
//
//        EventListHelper eHelper = new EventListHelper();
//
//        return eHelper.parseResult(response);
//    }
}
