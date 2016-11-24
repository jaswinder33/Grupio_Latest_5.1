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
    public void deleteNotes(List<NotesData> mList, Context mContext) {
        mListener.showProgress();
        mInteractor.delete(mList, mContext, this);
    }

    @Override
    public <T> void fetchList(T type, Context mContext) {
        mInteractor.fetchList(type, mContext, this);
    }

    @Override
    public void fetchNote(String type, String id, Context mContext) {
        mInteractor.fetchNote(type, id, mContext, this);
    }

    @Override
    public void doEmail(List<NotesData> mList, Context mContext) {
        mInteractor.doEmail(mList, mContext, this);
    }

    @Override
    public void saveNote(NotesData mNotes, Context mContext) {
        mInteractor.saveNote(mNotes, mContext, this);
    }


    @Override
    public void onFailure(String failure) {
        mListener.hideProgress();
        mListener.failure(failure);
    }

    @Override
    public void onListFetch(List<NotesData> mList) {
        mListener.hideProgress();
        mListener.showList(mList);
    }

    @Override
    public void onNoteFetch(NotesData noteData) {
        mListener.showNote(noteData);
    }

    @Override
    public void onNoteDelete(List<NotesData> mList) {
        mListener.showList(mList);
    }

    @Override
    public void onNoteSaved(NotesData noteData) {
        mListener.onNoteSaved(noteData);
    }

    @Override
    public void updateNoteId(String id) {
        mListener.upateNoteId(id);
    }

    @Override
    public void onColorFetch(String colorCode) {
        mListener.setHeaderColor(colorCode);
    }

    @Override
    public void onDeleteBtnShow() {
        mListener.showDeleteBtn();
    }

    @Override
    public void onHeaderText(String text) {
        mListener.setHeaderText(text);
    }
}
