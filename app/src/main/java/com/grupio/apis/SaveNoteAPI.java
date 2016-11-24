package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.dao.NotesDAO;
import com.grupio.message.apis.APICallBack;
import com.grupio.notes.NotesData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JSN on 23/11/16.
 */

public class SaveNoteAPI extends BaseAsyncTask<NotesData, Boolean> {

    public SaveNoteAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.notelist_api);
    }

    @Override
    public Boolean handleBackground(NotesData... params) {

        NotesData note = params[0];

        JSONObject jo = new JSONObject();

        try {
            jo.put("note_id", note.getId().equals(note.getNoteId()) ? "0" : note.getNoteId());
            jo.put("note_type", note.getNoteType());
            jo.put("note_text", note.getNoteText());
            jo.put("note_date", note.getNoteDate());
            jo.put("note_reminder", note.getNoteReminder());
            jo.put("operation", note.getLastOperation());
        } catch (Exception e) {
        }

        Log.i("SaveNoteAPI", "handleBackground: request: " + jo.toString());

        APIRequest apiRequest = new CookieRequest(mContext);
        String response = apiRequest.makeRequest(url, jo.toString());
//{"note_id":3128,"Status":"1"}

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonObject != null) {
            boolean isSuccess = false;
            try {
                isSuccess = jsonObject.getString("Status").equals("1");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (isSuccess) {
                try {
                    String note_id = jsonObject.getString("note_id");
                    note.setNoteId(note_id);
                    note.setNoteSync("1");
                    NotesDAO.getInstance(mContext).saveNote(note);
                    ((APICallBackWithResponse) mListener).onSuccess(note_id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }
}
