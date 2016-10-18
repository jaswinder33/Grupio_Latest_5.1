package com.grupio.apis;

import android.content.Context;

import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.data.EventData;
import com.grupio.helper.EventListHelper;
import com.grupio.session.ConstantData;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by JSN on 1/9/16.
 */
public class EventListAPI implements Callable<List<EventData>> {

    private String queryText;
    private Context context;

    public EventListAPI(Context context, String queryText) {
        this.queryText=queryText;
        this.context=context;
    }

    @Override
    public List<EventData> call() throws Exception {

//        List<NameValuePair> nameValuePairs = new ArrayList<>();
//        nameValuePairs.add(new BasicNameValuePair("keyword", ""));
//        nameValuePairs.add(new BasicNameValuePair("orgid", ConstantData.ORG_Id));
//        nameValuePairs.add(new BasicNameValuePair("format", "json"));
//        String encodedStr = URLEncoder.encode(queryText);
//        String queryString = "?keyword=" + encodedStr + "&orgid=" + ConstantData.ORG_Id + "&format=json" + "&version=5.0";
//        String eventResult = GridHome.ut_obj.httpPostRequestForCookies(ConstantData.SERVER_URL + "search.php" + queryString, nameValuePairs,context);
//
//        EventListHelper eHelper = new EventListHelper();

        String queryString = "?keyword=" + queryText + "&orgid=" + ConstantData.ORG_ID + "&format=json";
        String url = ConstantData.EVENT_LIST_API + queryString;

        APIRequest request = new GetRequest();
        String response = request.requestResponse(url, new HashMap<String, String>(),context);

        EventListHelper eHelper = new EventListHelper();

        return eHelper.parseResult(response);
    }
}
