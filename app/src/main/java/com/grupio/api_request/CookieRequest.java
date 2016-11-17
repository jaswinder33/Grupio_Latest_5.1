package com.grupio.api_request;

import android.content.Context;

import com.grupio.BuildConfig;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;

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
public class CookieRequest extends APIRequest {

	Context mContext;

	public CookieRequest(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public String requestResponse(String endPoint, Map<String, String> params, Context mContext) {

		String response = "";
		URL url;

		try {
			url = new URL(endPoint);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Cookie", "attendee_id=" + Preferences.getInstances(mContext).getAttendeeId() + ";" + "event_id=" + ConstantData.EVENT_ID + ";" + "organization_id=" + BuildConfig.ORG_ID);
			conn.setRequestProperty("Accept", "application/json");
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
		mHeaderList.put("Cookie", "attendee_id=" + Preferences.getInstances(mContext).getAttendeeId() + ";" + "event_id=" + ConstantData.EVENT_ID + ";" + "organization_id=" + BuildConfig.ORG_ID);

		return mHeaderList;
	}

}
