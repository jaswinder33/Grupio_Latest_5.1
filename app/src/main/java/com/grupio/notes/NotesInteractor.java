package com.grupio.notes;

import android.content.Context;

import com.grupio.Utils.Utility;
import com.grupio.apis.APICallBackWithResponse;
import com.grupio.apis.SessionNote;
import com.grupio.dao.NotesDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 11/11/16.
 */

public class NotesInteractor implements NotesContract.Interactor {

    @Override
    public void fetchList(Context mContext, NotesContract.OnInteraction mListener) {

        if (Utility.hasInternet(mContext)) {
            fetchListFromServer(mContext, mListener);
        } else {
            fetchListFromDb(mContext, mListener);
        }

    }

    @Override
    public void fetchNote(String type, String id, Context mContext, NotesContract.OnInteraction mListener) {

        List<NotesData> mNotesList = new ArrayList<>();
        mNotesList.addAll(NotesDAO.getInstance(mContext).getNote(id, type));

        if (!mNotesList.isEmpty()) {
            mListener.onNoteFetch(mNotesList.get(0));
        }
    }

    @Override
    public void delete(Context mContext, NotesContract.OnInteraction mListener) {

    }

    @Override
    public void doEmail(Context mContext, NotesContract.OnInteraction mListener) {

    }

    @Override
    public void saveNote(NotesData mNotes, Context mContext, NotesContract.OnInteraction mListener) {
        NotesDAO.getInstance(mContext).saveNote(mNotes);
        mListener.onNoteSaved();

        JSONObject mJObj = new JSONObject();
        try {
            mJObj.put("session_id", mNotes.getNoteId());
            mJObj.put("notes", mNotes.getNoteText());
            mJObj.put("operation", mNotes.getLastOperation());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new SessionNote(mContext, new APICallBackWithResponse() {
            @Override
            public void onSuccess(String response) {
                mListener.updateNoteId(response);
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String msg) {

            }
        }).doCall(mJObj.toString());

    }

    public void fetchListFromServer(Context mContext, NotesContract.OnInteraction mListener) {
    }

    public void fetchListFromDb(Context mContext, NotesContract.OnInteraction mListener) {
    }

}
