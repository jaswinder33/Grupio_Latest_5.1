package com.grupio.apis;

import android.content.Context;
import android.text.TextUtils;

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

public class SessionNote extends BaseAsyncTask<NotesData, Boolean> {

    public SessionNote(Context mcontext, APICallBackWithResponse mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.session_note);
    }

    @Override
    public Boolean handleBackground(NotesData... params) {

        NotesData mNotesData = params[0];

        JSONObject mJObj = new JSONObject();
        try {
            mJObj.put("session_id", mNotesData.getNoteId());
            mJObj.put("notes", mNotesData.getNoteText());
            mJObj.put("operation", mNotesData.getLastOperation());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIRequest api = new CookieRequest(mContext);
        String response = api.makeRequest(url, mJObj.toString());

        //{"Status":"1","session_id":"2483807","id":9146}
        if (response != null && !TextUtils.isEmpty(response)) {


            JSONObject mJobj = null;
            try {
                mJobj = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (mJobj != null) {
                try {

                    String id = mJobj.getString("id");


//                    Gson mGson = new Gson();
//                    NotesData mNotesData = mGson.fromJson(params[0], NotesData.class);
                    mNotesData.setId(id);
                    mNotesData.setNoteSync("1");

                    NotesDAO.getInstance(mContext).saveNote(mNotesData);
                    ((APICallBackWithResponse) mListener).onSuccess(id);

                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
