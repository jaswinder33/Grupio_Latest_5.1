package com.grupio.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.grupio.session.ConstantData;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jsn on 16/5/16.
 */
public class MyDbHandler extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = ConstantData.DATABASE;
    private static final int DATABASE_VERSION = 1;
    private static MyDbHandler mMyDbHandler;
    SQLiteDatabase db = null;
    private AtomicInteger mOpenCounter = new AtomicInteger();


    private MyDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static MyDbHandler getInstance(Context context) {
        if (mMyDbHandler == null) {
            synchronized (MyDbHandler.class) {
                if (mMyDbHandler == null) {
                    mMyDbHandler = new MyDbHandler(context);
                }
            }
        }
        return mMyDbHandler;
    }

    public synchronized SQLiteDatabase getDBObject(int isWrtitable) {
        if (mOpenCounter.incrementAndGet() == 1) {
            db = (isWrtitable == 1) ? getWritableDatabase() : getReadableDatabase();
        }
        return db;
    }

    public synchronized void closeDb() {
        if (mOpenCounter.decrementAndGet() == 0) {
            db.close();
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotesTable.CREATE_NOTES_TABLE);
        db.execSQL(MessageTable.CREATE_MSG_TABLE);
        db.execSQL(AttendeeTable.CREATE_ATTENDEE_TABLE);
        db.execSQL(SpeakerTable.CREATE_SPEAKER_TABLE);
        db.execSQL(ExhibitorTable.CREATE_EXHIBITOR_TABLE);
        db.execSQL(ExhibitorLikeTable.CREATE_EXHIBITOR_LIKE_TABLE);
        db.execSQL(MenuTable.CREATE_MENU_TABLE);
        db.execSQL(VersionTable.CREATE_VERSION_TABLE);
        db.execSQL(SessionTable.CREATE_SESSION_TABLE);
        db.execSQL(SessionTracksTable.CREATE_SESSION_TRACK_TABLE);
        db.execSQL(EventTable.CREATE_EVENT_TABLE);
        db.execSQL(MapsTable.CREATE_MAPS_TABLE);
        db.execSQL(LogisticsTable.CREATE_LOGISTICS_TABLE);
        db.execSQL(LiveTable.CREATE_LIVE_TABLE);
        db.execSQL(SurveyTable.CREATE_SURVEY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
