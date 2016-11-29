package com.grupio.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.grupio.attendee.ListConstant;
import com.grupio.db.NotesTable;
import com.grupio.helper.NotesHelper;
import com.grupio.notes.NotesData;
import com.grupio.notes.NotesDetailsActivity;
import com.grupio.notes.NotesListActivity;

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

    public void insertList(String response, String type) {

        deleteTable(type);

        List<NotesData> mNotesList = new ArrayList<>();

        NotesHelper notesHelper = new NotesHelper();
        mNotesList.addAll(notesHelper.parseList(response));

        openDB(1);

        try {
            db.beginTransaction();

            SQLiteStatement stmt = db.compileStatement("INSERT INTO " + NotesTable.NOTES_TABLE + "("
                    + NotesTable.ID + ","
                    + NotesTable.NOTE_ID + ","
                    + NotesTable.NOTE_TYPE + ","
                    + NotesTable.NOTE_TEXT + ","
                    + NotesTable.REMINDER + ","
                    + NotesTable.LAST_OPERATION + ","
                    + NotesTable.NOTE_SYNC
                    + ") VALUES(?,?,?,?,?,?,?)");

            if (mNotesList != null && mNotesList.size() > 0) {
                for (int i = 0; i < mNotesList.size(); i++) {

                    stmt.bindString(1, mNotesList.get(i).getId());
                    stmt.bindString(2, mNotesList.get(i).getNoteId());
                    stmt.bindString(3, mNotesList.get(i).getNoteType());
                    stmt.bindString(4, mNotesList.get(i).getNoteText());
                    stmt.bindString(5, mNotesList.get(i).getNoteReminder());
                    stmt.bindString(6, mNotesList.get(i).getLastOperation());
                    stmt.bindString(7, mNotesList.get(i).getNoteSync());

                    stmt.executeInsert();
                    stmt.clearBindings();

                }
            }

            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeDb();

    }

    public void saveNote(NotesData mNotesData) {
        openDB(1);

        boolean isNoteExists = false;

        String query;
        if (mNotesData.getNoteType().equals(NotesListActivity.My_NOTES) || mNotesData.getNoteType().equals(NotesListActivity.THINGS_TO_DO)) {
            query = "select exists (select * from " + NotesTable.NOTES_TABLE + " where " + NotesTable.ID + "='" + mNotesData.getId() + "' and " + NotesTable.NOTE_TYPE + "='" + mNotesData.getNoteType() + "')";
        } else {
            query = "select exists (select * from " + NotesTable.NOTES_TABLE + " where " + NotesTable.NOTE_ID + "='" + mNotesData.getNoteId() + "' and " + NotesTable.NOTE_TYPE + "='" + mNotesData.getNoteType() + "')";
        }


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
        cv.put(NotesTable.ID, mNotesData.getId());
        cv.put(NotesTable.NOTE_ID, mNotesData.getNoteId());
        cv.put(NotesTable.NOTE_TYPE, mNotesData.getNoteType());
        cv.put(NotesTable.NOTE_TEXT, mNotesData.getNoteText());
        cv.put(NotesTable.NOTE_DATE, mNotesData.getNoteDate());
        cv.put(NotesTable.REMINDER, mNotesData.getNoteReminder());
        cv.put(NotesTable.LAST_OPERATION, mNotesData.getLastOperation());
        cv.put(NotesTable.NOTE_SYNC, mNotesData.getNoteSync());

        if (isNoteExists) {
            if (mNotesData.getNoteType().equals(NotesListActivity.My_NOTES) || mNotesData.getNoteType().equals(NotesListActivity.THINGS_TO_DO)) {
                db.update(NotesTable.NOTES_TABLE, cv, NotesTable.ID + "=? AND " + NotesTable.NOTE_TYPE + "=?", new String[]{mNotesData.getId() + "", mNotesData.getNoteType()});
            } else {
                db.update(NotesTable.NOTES_TABLE, cv, NotesTable.NOTE_ID + "=? AND " + NotesTable.NOTE_TYPE + "=?", new String[]{mNotesData.getNoteId() + "", mNotesData.getNoteType()});
            }
        } else {
            db.insert(NotesTable.NOTES_TABLE, null, cv);
        }

        closeDb();
    }

    public NotesData getNote(String id, String type) {

        NotesData mNotesData = null;


        String query = "select * from " + NotesTable.NOTES_TABLE + " where " + NotesTable.NOTE_ID + "='" + id + "' and " + NotesTable.NOTE_TYPE + "='" + type + "';";

        openDB(1);
        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null && mCursor.getCount() > 0) {
                mCursor.moveToFirst();
                do {
                    mNotesData = new NotesData();
                    mNotesData.setId(mCursor.getString(0));
                    mNotesData.setNoteId(mCursor.getString(1));
                    mNotesData.setNoteType(mCursor.getString(2));
                    mNotesData.setNoteText(mCursor.getString(3));
                    mNotesData.setNoteReminder(mCursor.getString(4));
                    mNotesData.setNoteDate(mCursor.getString(5));
                    mNotesData.setLastOperation(mCursor.getString(6));
                    mNotesData.setNoteSync(mCursor.getString(7));
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

        return mNotesData;

    }

    public void setNoteSynced(String id, String type) {

    }

    public List<NotesData> getNoteList(String type) {

        List<NotesData> mNotesList = new ArrayList<>();

        String query = "select * from " + NotesTable.NOTES_TABLE + " where " + NotesTable.NOTE_TYPE + "='" + type + "' and " + NotesTable.LAST_OPERATION + " !='" + NotesDetailsActivity.DELETE + "';";

        openDB(0);

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null && mCursor.getCount() > 0) {

                mCursor.moveToFirst();

                NotesData mData;

                do {
                    mData = new NotesData();

                    mData.setId(mCursor.getString(0));
                    mData.setNoteId(mCursor.getString(1));
                    mData.setNoteType(mCursor.getString(2));
                    mData.setNoteText(mCursor.getString(3));
                    mData.setNoteReminder(mCursor.getString(4));
                    mData.setNoteDate(mCursor.getString(5));
                    mData.setLastOperation(mCursor.getString(6));
                    mData.setNoteSync(mCursor.getString(7));

                    mNotesList.add(mData);
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

        return mNotesList;

    }

    public void deleteTable(String type) {

        String[] constraint = new String[0];

        if (type.equals(NotesListActivity.My_NOTES) || type.equals(NotesListActivity.THINGS_TO_DO)) {
            constraint = new String[]{NotesListActivity.My_NOTES, NotesListActivity.THINGS_TO_DO, NotesDetailsActivity.UPDATE};
        } else if (type.equals(ListConstant.SESSION)) {
            constraint = new String[]{ListConstant.SESSION, NotesDetailsActivity.UPDATE};
        } else if (type.equals(ListConstant.EXHIBITOR)) {
            constraint = new String[]{ListConstant.EXHIBITOR, NotesDetailsActivity.UPDATE};
        }

        openDB(0);
        try {
            db.delete(NotesTable.NOTES_TABLE, NotesTable.NOTE_TYPE + "=? OR " + NotesTable.NOTE_TYPE + "=? AND " + NotesTable.LAST_OPERATION, constraint);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDb();
        }
    }

    public void deleteNote(NotesData mNoteData) {
        openDB(0);
        try {
            db.delete(NotesTable.NOTES_TABLE, NotesTable.NOTE_TYPE + "=? AND " + NotesTable.NOTE_ID + "=?", new String[]{mNoteData.getNoteType(), mNoteData.getNoteId()});
            Log.i("DAO", "deleteNote: Success: " + mNoteData.getNoteText());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("DAO", "deleteNote: Failed: " + mNoteData.getNoteText());
        }
        closeDb();
    }

}
