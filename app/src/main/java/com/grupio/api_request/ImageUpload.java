package com.grupio.api_request;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by JSN on 2/12/16.
 */

public class ImageUpload extends APIRequest {

    private Map<String, String> mheaderlist = new HashMap<>();

    public void setHeader(Map<String, String> mheaderlist) {
        this.mheaderlist = mheaderlist;
    }

    @Override
    public String makeRequest(String endPoint, String filePath) {

        String responseStr = "";

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        URL url;
        try {
            url = new URL(endPoint);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (getCustomHeaders() != null && !getCustomHeaders().isEmpty()) {
                Map<String, String> mheaderlist = getCustomHeaders();
                Iterator mIterator = mheaderlist.entrySet().iterator();
                while (mIterator.hasNext()) {
                    Map.Entry<String, String> mObj = (Map.Entry<String, String>) mIterator.next();
                    conn.setRequestProperty(mObj.getKey(), mObj.getValue());
                }
            }

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            conn.setDoInput(true);
            conn.setDoOutput(true);

            DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + filePath + "\"" + lineEnd);
            outputStream.writeBytes(lineEnd);

            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // Read file
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                Log.i("Uploading Image", "makeRequest: " + bytesRead);
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            InputStream is;
            int status = conn.getResponseCode();

            if (status >= 400)
                is = conn.getErrorStream();
            else
                is = conn.getInputStream();


//            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            responseStr = response.toString();
            rd.close();
            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStr;
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
