package com.grupio.notes;

import android.content.Context;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.apis.APICallBackWithResponse;
import com.grupio.apis.DeleteNotesAPI;
import com.grupio.apis.SaveNoteAPI;
import com.grupio.apis.SessionNote;
import com.grupio.backend.DateTime;
import com.grupio.dao.EventDAO;
import com.grupio.dao.NotesDAO;
import com.grupio.data.EmailData;
import com.grupio.db.EventTable;
import com.grupio.message.apis.APICallBack;
import com.grupio.services.EmailService;
import com.grupio.services.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 11/11/16.
 */

public class NotesInteractor implements NotesContract.Interactor {

    @Override
    public <T> void fetchList(T type, Context mContext, NotesContract.OnInteraction mListener) {

        String color = EventDAO.getInstance(mContext).getValue(EventTable.COLOR_THEME);
        mListener.onColorFetch(color);
        mListener.onListFetch(NotesDAO.getInstance(mContext).getNoteList((String) type));
    }

    @Override
    public void fetchNote(String type, String id, Context mContext, NotesContract.OnInteraction mListener) {

        String timezone = EventDAO.getInstance(mContext).getValue(EventTable.GET_TIMEZONE);
        timezone = timezone.replace("UTC", "GMT");
        timezone = timezone.replace(" ", "");
        timezone = timezone.replace("hours", "");
        timezone = timezone.replace("hour", "");
        timezone = timezone.replace("hrs", "");
        timezone = timezone.replace("hr", "");

        if (type.equals(NotesListActivity.THINGS_TO_DO)) {
            mListener.onHeaderText(mContext.getString(R.string.things_todo_heading));
        } else if (type.equals(NotesListActivity.My_NOTES)) {
            mListener.onHeaderText(mContext.getString(R.string.note_heading));
        }

        mListener.onColorFetch(EventDAO.getInstance(mContext).getValue(EventTable.COLOR_THEME));
        NotesData mNote = NotesDAO.getInstance(mContext).getNote(id, type);
        if (mNote != null) {
            mNote.setTimeZone(timezone);
            mListener.onNoteFetch(mNote);
            if (type.equals(NotesListActivity.THINGS_TO_DO) || type.equals(NotesListActivity.My_NOTES)) {
                mListener.onDeleteBtnShow();
            }
        } else {
            if (type.equals(NotesListActivity.THINGS_TO_DO)) {
                String time = DateTime.getInstance().currentTimeInTimeZone(timezone);
                time = DateTime.getInstance().formatDate("dd-MMM-yyyy hh:mma", time);

                mNote = new NotesData();
                mNote.setId(id);
                mNote.setNoteDate(time);
                mNote.setTimeZone(timezone);
                mNote.setNoteId("0");
                mNote.setNoteType(type);

                mListener.onNoteFetch(mNote);
            }
        }
    }

    @Override
    public void delete(List<NotesData> mList, Context mContext, NotesContract.OnInteraction mListener) {

        List<NotesData> mNotesList = new ArrayList<>();

        //Again fetch all data from db
        for (int i = 0; i < mList.size(); i++) {
            mNotesList.add(NotesDAO.getInstance(mContext).getNote(mList.get(i).getNoteId(), mList.get(i).getNoteType()));
        }

        List<NotesData> mOnlineNotesList = new ArrayList<>();


        for (int i = 0; i < mNotesList.size(); i++) {
            if (mNotesList.get(i).getNoteSync().equals("1")) {
                mOnlineNotesList.add(NotesDAO.getInstance(mContext).getNote(mNotesList.get(i).getNoteId(), mNotesList.get(i).getNoteType()));
            } else {
                NotesDAO.getInstance(mContext).deleteNote(mNotesList.get(i));
            }
        }

        if (Utility.hasInternet(mContext)) {
            new DeleteNotesAPI(mContext, new APICallBack() {
                @Override
                public void onSuccess() {
                    String type = mList.get(0).getNoteType();
                    mListener.onListFetch(NotesDAO.getInstance(mContext).getNoteList(type));
                }

                @Override
                public void onFailure(String msg) {
                    mListener.onFailure(msg);
                }
            }).doCall(mOnlineNotesList);
        } else {
            mListener.onFailure(mContext.getString(R.string.no_internet));

            for (int i = 0; i < mOnlineNotesList.size(); i++) {
                mOnlineNotesList.get(i).setLastOperation(NotesDetailsActivity.DELETE);
                NotesDAO.getInstance(mContext).saveNote(mOnlineNotesList.get(i));
            }

            String type = mList.get(0).getNoteType();
            mListener.onListFetch(NotesDAO.getInstance(mContext).getNoteList(type));

        }


    }

    @Override
    public void doEmail(List<NotesData> mList, Context mContext, NotesContract.OnInteraction mListener) {

        String text = "Your Notes";

        for (int i = 0; i < mList.size(); i++) {
            text += "\n\n" + (i + 1) + "  " + mList.get(i).getNoteText() + ".\n\n";
        }

        EmailData email = new EmailData("", "My Notes", text);

        Service<EmailData> emailService = new Service<>(new EmailService());
        emailService.sendMessage(email, mContext, null);

    }

    @Override
    public void saveNote(NotesData mNotes, Context mContext, NotesContract.OnInteraction mListener) {

        NotesDAO.getInstance(mContext).saveNote(mNotes);
        NotesData mNoteData = NotesDAO.getInstance(mContext).getNote(mNotes.getNoteId(), mNotes.getNoteType());

        if (mNoteData != null) {
            mListener.onNoteSaved(mNoteData);
        }

        if (mNotes.getNoteType().equals(NotesListActivity.My_NOTES) || mNotes.getNoteType().equals(NotesListActivity.THINGS_TO_DO)) {
            saveNoteToServer(mNotes, mContext, mListener);
        } else {
            saveSessionNote(mNotes, mContext, mListener);
        }
    }

    public void saveSessionNote(NotesData mNotes, Context mContext, NotesContract.OnInteraction mListener) {
        NotesDAO.getInstance(mContext).saveNote(mNotes);
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
                mListener.onFailure(msg);
            }
        }).doCall(mNotes);
    }

    public void saveNoteToServer(NotesData mNotes, Context mContext, NotesContract.OnInteraction mListener) {

        new SaveNoteAPI(mContext, new APICallBackWithResponse() {
            @Override
            public void onSuccess(String response) {
                mListener.updateNoteId(response);
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String msg) {
                mListener.onFailure(msg);
            }
        }).doCall(mNotes);


    }
}
