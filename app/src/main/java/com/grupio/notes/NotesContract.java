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

        void showNote(String text);
    }

    interface Presenter {

        void fetchList(Context mContext);

        void fetchNote(Context mContext);

        void delete(Context mContext);

        void doEmail(Context mContext);

        void saveNote(NotesData data);
    }

    interface Interactor {

        void fetchListFromDb(Context mContext, OnInteraction mListener);

        void fetchListFromServer(Context mContext, OnInteraction mListener);

        void fetchNote(Context mContext, OnInteraction mListener);

        void delete(Context mContext, OnInteraction mListener);

        void doEmail(Context mContext, OnInteraction mListener);

        void saveNote(NotesData data);
    }

    interface OnInteraction {

        void onFailure();

        void onListFetch(List<NotesData> mList);

        void onNoteFetch(String noteData);

        void onNoteDelete();

        void onNoteSaved();


    }

}
