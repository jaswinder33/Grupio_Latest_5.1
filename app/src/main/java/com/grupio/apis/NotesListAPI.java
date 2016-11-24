package com.grupio.apis;

/**
 * Created by JSN on 21/11/16.
 */

import android.content.Context;
import android.text.TextUtils;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.dao.NotesDAO;
import com.grupio.notes.NotesListActivity;

import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;

/**
 * This api fetch list of notes from server
 */
public class NotesListAPI extends BaseAsyncTask<Void, Boolean> {
    public NotesListAPI(Context mcontext) {
        super(mcontext);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.notelist_api);
    }

    @Override
    public Boolean handleBackground(Void... params) {

        Map<String, String> mParams = new HashMap<>();
        mParams.put("operation", "3");

        APIRequest apiRequest = new CookieRequest(mContext);
        String response = apiRequest.makeRequest(url, JSONValue.toJSONString(mParams));

        if (response != null && !TextUtils.isEmpty(response)) {
            NotesDAO.getInstance(mContext).insertList(response, NotesListActivity.My_NOTES);
        }

        return null;
    }
}
