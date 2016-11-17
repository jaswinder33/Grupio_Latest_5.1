package com.grupio.api_request;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;


/**
 * Created by JSN on 19/9/16.
 */

public class GetRequest extends APIRequest {
	
	public String requestResponse(String endPoint, Map<String, String> params, Context mContext) {
		

		URL url = null;
		try {
			url = new URL(endPoint);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) url.openConnection();
//			con.setReadTimeout(30000);
//			con.setConnectTimeout(30000);
//			con.setInstanceFollowRedirects(true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// optional default is GET
		try {
			con.setRequestMethod("GET");
		} catch (ProtocolException e) {
			e.printStackTrace();
		}

		BufferedReader in = null;
		try {
			in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String inputLine;
		StringBuffer response = new StringBuffer();

		try {
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//print result
		System.out.println(response.toString());

		return response.toString();
	}

	@Override
	protected String getRequestType() {
		return "GET";
	}

	@Override
	protected Map<String, String> getCustomHeaders() {
		return null;
	}
}
