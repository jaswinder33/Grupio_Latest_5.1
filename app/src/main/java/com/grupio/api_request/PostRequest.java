package com.grupio.api_request;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by JSN on 19/9/16.
 */

public class PostRequest extends APIRequest {

	@Override
	public String requestResponse(String endPoint, Map<String, String> params, Context mContext) {

		String response = "";
		URL url;

		try {
			url = new URL(endPoint);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(true);
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("ClientVersion", "" + 1);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			OutputStream os = conn.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(getPostDataString(params));

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
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(response.toString());

		return response.toString();

	}

	@Override
	protected String getRequestType() {
		return "POST";
	}

	@Override
	protected Map<String, String> getCustomHeaders() {

		Map<String, String> mHeaderList = new HashMap<>();
		mHeaderList.put("Accept", "application/json");
		mHeaderList.put("ClientVersion", String.valueOf(1));

		return mHeaderList;
	}

}
