package com.grupio.notes;

import android.content.Context;

import java.util.List;

/**
 * Created by JSN on 11/11/16.
 */

public class NotesPresenter implements NotesContract.Presenter, NotesContract.OnInteraction {

    NotesContract.View mListener;
    NotesInteractor mInteractor;

    NotesPresenter(NotesContract.View mListener) {
        this.mListener = mListener;
        mInteractor = new NotesInteractor();
    }

    @Override
    public void fetchList(Context mContext) {

    }

    @Override
    public void fetchNote(Context mContext) {

    }

    @Override
    public void delete(Context mContext) {

    }

    @Override
    public void doEmail(Context mContext) {

    }

    @Override
    public void saveNote(NotesData data) {

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onListFetch(List<NotesData> mList) {

    }

    @Override
    public void onNoteFetch(String noteData) {

    }

    @Override
    public void onNoteDelete() {

    }

    @Override
    public void onNoteSaved() {

    }
}
