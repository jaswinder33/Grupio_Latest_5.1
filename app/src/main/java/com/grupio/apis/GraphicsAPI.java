package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.Utils.Utility;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.helper.GraphicsProcessor;
import com.grupio.session.ConstantData;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by JSN on 22/8/16.
 */
public class GraphicsAPI extends BaseApiCall {

//    protected Context mContext;
    public GraphicsAPI(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void run() {
        super.run();

        String graphicUrl =  ConstantData.GRAPHICS_API + ConstantData.EVENT_ID +  ConstantData.API_FORMAT;

        APIRequest request = new GetRequest();
        String response = request.requestResponse(graphicUrl, new HashMap<String, String>(), mContext);


//        String response = GridHome.ut_obj.postData(graphicUrl,new ArrayList<NameValuePair>(),mContext);

        if(response != null){

            GraphicsProcessor gp = new GraphicsProcessor();
            Map<String, String> mUrlList = gp.parseResult(response, mContext);

            Iterator iterator = mUrlList.entrySet().iterator();

            String fileName  ;
            String fileUrl ;

            while (iterator.hasNext()){
                Map.Entry<String,String> pair = (Map.Entry<String, String>) iterator.next();

                fileName = pair.getKey();
                fileUrl = pair.getValue();

                if(fileUrl != null && !fileUrl.equals("")){
                    downloadResource(fileName, fileUrl);
                }
            }
        }
        Log.i("API", "GraphicsAPI");

    }


    public void downloadResource(final String fileName, final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Dot is used to hide the folder
                Utility.downloadFile(mContext, url, fileName, ConstantData.RESOURCES + File.separator + ConstantData.RESOURCES, "");
            }
        }).start();
    }

}
