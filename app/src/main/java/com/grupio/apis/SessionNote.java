package com.grupio.apis;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.dao.NotesDAO;
import com.grupio.notes.NotesData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JSN on 17/11/16.
 */

public class SessionNote extends BaseAsyncTask<String, Boolean> {

    public SessionNote(Context mcontext, APICallBackWithResponse mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.session_note);
    }

    @Override
    public Boolean handleBackground(String... params) {

        APIRequest api = new CookieRequest(mContext);
        String response = api.makeRequest(url, params[0]);

        //{"Status":"1","session_id":"2528398"}
        if (response != null && !TextUtils.isEmpty(response)) {


            JSONObject mJobj = null;
            try {
                mJobj = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (mJobj != null) {
                try {

                    String noteId = mJobj.getString("session_id");

                    Gson mGson = new Gson();
                    NotesData mNotesData = mGson.fromJson(params[0], NotesData.class);
                    mNotesData.setNoteId(noteId);
                    mNotesData.setNoteSync("1");

                    NotesDAO.getInstance(mContext).saveNote(mNotesData);
                    ((APICallBackWithResponse) mListener).onSuccess(noteId);

                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
