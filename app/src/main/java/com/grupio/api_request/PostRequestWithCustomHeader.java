package com.grupio.api_request;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSN on 16/11/16.
 */

public class PostRequestWithCustomHeader extends APIRequest {

    private Map<String, String> mheaderlist = new HashMap<>();

    public void setHeader(Map<String, String> mheaderlist) {
        this.mheaderlist = mheaderlist;
    }

    @Override
    public String requestResponse(String endPoint, Map<String, String> params, Context mContext) {
        return "";
    }

    @Override
    protected String getRequestType() {
        return "POST";
    }

    @Override
    protected Map<String, String> getCustomHeaders() {
        return mheaderlist;
    }


}
