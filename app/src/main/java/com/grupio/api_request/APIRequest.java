package com.grupio.api_request;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSN on 19/9/16.
 */

public abstract class APIRequest {

    Map<String, String> params = new HashMap<>();

    public abstract String requestResponse(String endPoint, Map<String, String> params, Context mContext);

}
