package com.grupio.api_request;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by JSN on 19/9/16.
 */

public abstract class APIRequest {

    RequestWatcher mListener;

    protected static String getPostDataString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    public abstract String requestResponse(String endPoint, Map<String, String> params, Context mContext);

    protected abstract String getRequestType();

    protected abstract Map<String, String> getCustomHeaders();

    public String makeRequest(String endPoint, String params) {
        String response = "";
        URL url;

        try {
            url = new URL(endPoint);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setInstanceFollowRedirects(true);
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setRequestMethod(getRequestType());

            if (getCustomHeaders() != null && !getCustomHeaders().isEmpty()) {
                Map<String, String> mheaderlist = getCustomHeaders();
                Iterator mIterator = mheaderlist.entrySet().iterator();
                while (mIterator.hasNext()) {
                    Map.Entry<String, String> mObj = (Map.Entry<String, String>) mIterator.next();
                    conn.setRequestProperty(mObj.getKey(), mObj.getValue());
                }
            }

            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(params);

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

            if (mListener != null) {
                mListener.ResponseCode(responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(response.toString());
        return response.toString();
    }

    public void setListener(RequestWatcher mListener) {
        this.mListener = mListener;
    }

    public interface RequestWatcher {
        void ResponseCode(int code);
    }
}
