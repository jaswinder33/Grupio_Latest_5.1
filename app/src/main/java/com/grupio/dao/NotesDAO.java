package com.grupio.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.grupio.db.NotesTable;
import com.grupio.notes.NotesData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 17/11/16.
 */

public class NotesDAO extends BaseDAO {

    protected NotesDAO(Context mContext) {
        super(mContext);
    }

    public static NotesDAO getInstance(Context mContext) {
        return new NotesDAO(mContext);
    }

    public void insertList() {

    }

    public void saveNote(NotesData mNotesData) {
        openDB(1);

        boolean isNoteExists = false;

        String query = "select exists (select * from " + NotesTable.NOTES_TABLE + " where " + NotesTable.NOTE_ID + "='" + mNotesData.getNoteId() + "' and " + NotesTable.NOTE_TYPE + "='" + mNotesData.getNoteType() + "')";

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null) {
                mCursor.moveToFirst();
                isNoteExists = mCursor.getString(0).equals("1");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCursor != null && mCursor.isClosed()) {
                mCursor.close();
            }
        }

        ContentValues cv = new ContentValues();
        cv.put(NotesTable.NOTE_ID, mNotesData.getNoteId());
        cv.put(NotesTable.NOTE_TYPE, mNotesData.getNoteType());
        cv.put(NotesTable.NOTE_TEXT, mNotesData.getNoteText());
        cv.put(NotesTable.LAST_OPERATION, mNotesData.getLastOperation());
        cv.put(NotesTable.NOTE_SYNC, mNotesData.getNoteSync());

        if (isNoteExists) {
            db.update(NotesTable.NOTES_TABLE, cv, NotesTable.NOTE_ID + "=? AND " + NotesTable.NOTE_TYPE + "=?", new String[]{mNotesData.getNoteId() + "", mNotesData.getNoteType()});
        } else {
            db.insert(NotesTable.NOTES_TABLE, null, cv);
        }

        closeDb();
    }

    public List<NotesData> getNote(String id, String type) {

        List<NotesData> mNoteslist = new ArrayList<>();

        String query = "select * from " + NotesTable.NOTES_TABLE + " where " + NotesTable.NOTE_ID + "='" + id + "' and " + NotesTable.NOTE_TYPE + "='" + type + "';";

        openDB(1);
        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null) {
                mCursor.moveToFirst();

                NotesData mNotesData;
                do {
                    mNotesData = new NotesData();

                    mNotesData.setNoteId(mCursor.getString(0));
                    mNotesData.setNoteType(mCursor.getString(1));
                    mNotesData.setNoteText(mCursor.getString(2));
                    mNotesData.setLastOperation(mCursor.getString(3));
                    mNotesData.setNoteSync(mCursor.getString(4));

                    mNoteslist.add(mNotesData);
                } while (mCursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }

            closeDb();
        }

        return mNoteslist;

    }

    public void setNoteSynced(String id, String type) {

    }


}
