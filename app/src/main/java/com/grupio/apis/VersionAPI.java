package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.data.VersionData;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by JSN on 19/8/16.
 */

/**
 * This api fetch versions of all apis and store in db.
 *
 * @param event_id = event_id of event.
 *                 format = format required is json.
 *                 <p/>
 *                 Sample link:
 *                 https://conf.dharanet.com/conf/v1/main/getversions.php?format=json&event_id=151
 */
public class VersionAPI implements Callable<List<VersionData>> {

    private String result;
    private Context mContext;

    public VersionAPI(Context mContext) {
        this.mContext=mContext;
    }


    @Override
    public List<VersionData> call() throws Exception {

        String url = ConstantData.VERSION_API + Preferences.getInstances(mContext).getEventId();

        APIRequest request = new GetRequest();
        String response =  request.requestResponse(url, new HashMap<String, String>(),mContext);


//        result = GridHome.ut_obj.postData(url, new ArrayList<NameValuePair>(), mContext);

        Log.i("API", "VersionAPI");

        if(result != null){
//            VersionProcessor vp = new VersionProcessor();
//            VersionDao.getInstance(mContext).insertDataInNewColumn(vp.parseResult(result));
        }

        return null;
    }
}
