package com.grupio.notes;

import android.content.Context;

import com.grupio.Utils.Utility;
import com.grupio.apis.APICallBackWithResponse;
import com.grupio.apis.SessionNote;
import com.grupio.dao.EventDAO;
import com.grupio.dao.NotesDAO;
import com.grupio.db.EventTable;

/**
 * Created by JSN on 11/11/16.
 */

public class NotesInteractor implements NotesContract.Interactor {

    @Override
    public <T> void fetchList(T type, Context mContext, NotesContract.OnInteraction mListener) {

        if (Utility.hasInternet(mContext)) {
            if (type instanceof NotesData) {
                fetchNoteListFromServer(mContext, mListener);
            } else {

            }
        } else {
            if (type instanceof NotesData) {
                fetchNoteListFromDb(mContext, mListener);
            } else {
                fetchToDoListFromDb(mContext, mListener);
            }
        }
    }

    @Override
    public void fetchNote(String type, String id, Context mContext, NotesContract.OnInteraction mListener) {
        mListener.onColorFetch(EventDAO.getInstance(mContext).getValue(EventTable.COLOR_THEME));
        mListener.onNoteFetch(NotesDAO.getInstance(mContext).getNote(id, type));
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
        mListener.onNoteSaved(NotesDAO.getInstance(mContext).getNote(mNotes.getNoteId(), mNotes.getNoteType()));

        mListener.onNoteSaved(NotesDAO.getInstance(mContext).getNote(mNotes.getNoteId(), mNotes.getNoteType()));

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
        }).doCall(mNotes);

    }

    public void fetchNoteListFromServer(Context mContext, NotesContract.OnInteraction mListener) {
    }

    public void fetchNoteListFromDb(Context mContext, NotesContract.OnInteraction mListener) {
    }

    public void fetchToDoListFromServer(Context mContext, NotesContract.OnInteraction mListener) {

    }

    public void fetchToDoListFromDb(Context mContext, NotesContract.OnInteraction mListener) {

    }

}
