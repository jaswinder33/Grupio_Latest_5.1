package com.grupio.social;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.PostRequest;
import com.grupio.api_request.PostRequestWithCustomHeader;
import com.grupio.session.Preferences;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;

public class LinkedinDialog extends Dialog {


    //both keys are newly generated.
    private static final String CLIENT_ID = "789n70dwv0onhn";
    private static final String SECRET_KEY = "QovYwOIpkQsmEqDx";
    private static final String STATE = "E3ZYKC1T6H2yP4z"; //made up
    private static final String REDIRECT_URI = "https://www.linkedin.com/developer/apps/4086434/auth";
    //These are constants used for build the urls
    private static final String AUTHORIZATION_URL = "https://www.linkedin.com/uas/oauth2/authorization";
    private static final String ACCESS_TOKEN_URL = "https://www.linkedin.com/uas/oauth2/accessToken";
    private static final String SECRET_KEY_PARAM = "client_secret";
    private static final String RESPONSE_TYPE_PARAM = "response_type";
    private static final String GRANT_TYPE_PARAM = "grant_type";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String RESPONSE_TYPE_VALUE = "code";
    private static final String CLIENT_ID_PARAM = "client_id";
    private static final String STATE_PARAM = "state";
    private static final String REDIRECT_URI_PARAM = "redirect_uri";
    private static final String SCOPE_PARAM = "scope";
    private static final String SCOPE = "w_share";
    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUALS = "=";
    /*---------------------------------------*/
    public boolean isPostedSuccess = false;
    public String message = "";
    Context context;
    private String shareText = "";

    private ProgressDialog progressDialog = null;
    private boolean isPosted = false;
    private OnVerifyListener listeners;


    public LinkedinDialog(Context context, String text) {
        super(context);
        this.context = context;
        shareText = text;
    }

    /**
     * Method that generates the url for get the access token from the Service
     *
     * @return Url
     */
    private static String getAccessTokenUrl(String authorizationToken) {


        String url = ACCESS_TOKEN_URL
                + QUESTION_MARK
                + GRANT_TYPE_PARAM + EQUALS + GRANT_TYPE
                + AMPERSAND
                + RESPONSE_TYPE_VALUE + EQUALS + authorizationToken
                + AMPERSAND
                + CLIENT_ID_PARAM + EQUALS + CLIENT_ID
                + AMPERSAND
                + REDIRECT_URI_PARAM + EQUALS + REDIRECT_URI
                + AMPERSAND
                + SECRET_KEY_PARAM + EQUALS + SECRET_KEY;

        Log.i("code", url);

        return url;


    }


    /**
     * Method that generates the url for get the authorization token from the Service
     *
     * @return Url
     */
    private static String getAuthorizationUrl() {
        return AUTHORIZATION_URL
                + QUESTION_MARK + RESPONSE_TYPE_PARAM + EQUALS + RESPONSE_TYPE_VALUE
                + AMPERSAND + CLIENT_ID_PARAM + EQUALS + CLIENT_ID
                + AMPERSAND + STATE_PARAM + EQUALS + STATE
                + AMPERSAND + REDIRECT_URI_PARAM + EQUALS + REDIRECT_URI;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ln_dialog);
        setWebView();

    }

    private void setWebView() {
        final WebView mWebView = (WebView) findViewById(R.id.webkitWebView1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.requestFocus(View.FOCUS_DOWN);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.show();
                }

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                try {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String authorizationUrl) {

                if (authorizationUrl.startsWith(REDIRECT_URI)) {
                    Log.i("Authorize", "");
                    Uri uri = Uri.parse(authorizationUrl);
                    String stateToken = uri.getQueryParameter(STATE_PARAM);
                    if (stateToken == null || !stateToken.equals(STATE)) {
                        Log.e("Authorize", "State token doesn't match");
                        return true;
                    }
                    //If the user doesn't allow authorization to our application, the authorizationToken Will be null.
                    String authorizationToken = uri.getQueryParameter(RESPONSE_TYPE_VALUE);
                    if (authorizationToken == null) {
                        listeners.onVerify(isPostedSuccess, "Post Cancelled");
                        return true;
                    }
                    String accessTokenUrl = getAccessTokenUrl(authorizationToken);
                    new PostRequestAsyncTask().execute(accessTokenUrl);

                } else {
                    mWebView.loadUrl(authorizationUrl);
                }

                return true;
            }


        });

        String authUrl = getAuthorizationUrl();
        mWebView.loadUrl(authUrl);

    }

    public void setVerifierListener(OnVerifyListener data) {
        listeners = data;
    }

    public interface OnVerifyListener {
        void onVerify(Boolean isPosted, String message);
    }

    public class PostRequestAsyncTask extends AsyncTask<String, Void, Boolean> {

        String accessToken = "";

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(String... urls) {

            long addThisTimeStamp = 0;
            String response;
            if (Preferences.getInstances(context).getLinedinToken() != null) {
                response = Preferences.getInstances(context).getLinedinToken();
                addThisTimeStamp = 0;
            } else {
                String url = urls[0];
                APIRequest api = new PostRequest();
                response = api.requestResponse(url, new HashMap<>(), context);
                addThisTimeStamp = System.currentTimeMillis();
            }


            JSONObject resultJson = null;
            try {
                resultJson = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (resultJson != null) {

                long expiresIn = 0;
                try {
                    expiresIn = resultJson.has("expires_in") ? resultJson.getInt("expires_in") : 0;
                    expiresIn = expiresIn + addThisTimeStamp;

                    boolean isExpired = expiresIn - System.currentTimeMillis() <= 0;

                    if (isExpired) {
                        message = "expired";
                        Preferences.getInstances(context).setLinkedinToken(null);
                        return false;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    accessToken = resultJson.has("access_token") ? resultJson.getString("access_token") : null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    resultJson.put("expires_in", expiresIn);
                    Preferences.getInstances(context).setLinkedinToken(resultJson.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (accessToken != null) {
                    String shareUrl = "https://api.linkedin.com/v1/people/~/shares?format=json";

                    Map<String, String> mHeaders = new HashMap<>();

                    mHeaders.put("Authorization", "Bearer " + accessToken);
                    mHeaders.put("Content-Type", "application/json");
                    mHeaders.put("x-li-format", "json");

                    Map<String, Object> jsonMap = new HashMap<>();
                    jsonMap.put("comment", shareText);

                    try {
                        JSONObject visibilityObject = new JSONObject();
                        visibilityObject.put("code", "anyone");
                        jsonMap.put("visibility", visibilityObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    APIRequest apiRequest = new PostRequestWithCustomHeader();
                    ((PostRequestWithCustomHeader) apiRequest).setHeader(mHeaders);

                    apiRequest.setListener(code -> {
                        if (code == 201) {
                            isPosted = true;
                        }
                    });
                    apiRequest.makeRequest(shareUrl, JSONValue.toJSONString(jsonMap));
                }
            }

            if (isPosted) {
                message = "Successfully Posted";
                return true;
            }

            message = "Post Failed";
            return false;
        }

        @Override
        protected void onPostExecute(Boolean status) {
            if (listeners != null) {
                if (message.equals("expired") && !isShowing()) {
                    show();
                } else {
                    listeners.onVerify(status, message);
                }
            }
        }
    }
}