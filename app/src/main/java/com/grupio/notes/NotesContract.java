package com.grupio.notes;

import android.content.Context;

import java.util.List;

/**
 * Created by JSN on 11/11/16.
 */

public interface NotesContract {

    interface View {
        void showProgress();

        void hideProgress();

        void showList(List<NotesData> mList);

        void showNote(NotesData note);

        void onNoteSaved(NotesData mNote);

        void upateNoteId(String id);

        void setHeaderColor(String colorCode);
    }

    interface Presenter {

        <T> void fetchList(T type, Context mContext);

        void fetchNote(String type, String id, Context mContext);

        void delete(Context mContext);

        void doEmail(Context mContext);

        void saveNote(NotesData mNotes, Context mContext);
    }

    interface Interactor {

        <T> void fetchList(T type, Context mContext, OnInteraction mListener);

        void fetchNote(String type, String id, Context mContext, OnInteraction mListener);

        void delete(Context mContext, OnInteraction mListener);

        void doEmail(Context mContext, OnInteraction mListener);

        void saveNote(NotesData mNotes, Context mContext, OnInteraction mListener);
    }

    interface OnInteraction {

        void onFailure();

        void onListFetch(List<NotesData> mList);

        void onNoteFetch(NotesData noteData);

        void onNoteDelete();

        void onNoteSaved(NotesData noteData);

        void updateNoteId(String id);

        void onColorFetch(String colorCode);
    }

}
