package com.grupio.apis;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.dao.NotesDAO;
import com.grupio.message.apis.APICallBack;
import com.grupio.notes.NotesData;
import com.grupio.notes.NotesDetailsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 23/11/16.
 */

public class DeleteNotesAPI extends BaseAsyncTask<List<NotesData>, Boolean> {

    public DeleteNotesAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.notelist_api);
    }

    @Override
    public Boolean handleBackground(List<NotesData>... params) {

        List<NotesData> mList = new ArrayList<>();
        mList.addAll(params[0]);

        String noteIDs = "";

        if (!mList.isEmpty()) {
            for (int i = 0; i < mList.size(); i++) {
                if (!mList.get(i).getNoteSync().equals("0")) {
                    noteIDs += mList.get(i).getNoteId() + ",";
                }
            }
        }

        Log.i("API", "handleBackground: Noteid" + noteIDs);

        if (!TextUtils.isEmpty(noteIDs)) {
            noteIDs = noteIDs.substring(0, noteIDs.lastIndexOf(","));

            JSONObject jo = new JSONObject();
            try {
                jo.put("notes_ids", noteIDs);
                jo.put("operation", "2");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            APIRequest api = new CookieRequest(mContext);
            String response = api.makeRequest(url, jo.toString());

            try {
                JSONObject jObj = new JSONObject(response);

                if (jObj != null) {
                    String status = jObj.getString("Status");
                    if (status.equals(NotesDetailsActivity.UPDATE)) {
                        deleteNotes(mList);
                        return true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public void deleteNotes(List<NotesData> mList) {
        for (int i = 0; i < mList.size(); i++) {
            NotesDAO.getInstance(mContext).deleteNote(mList.get(i));
        }
    }
}
