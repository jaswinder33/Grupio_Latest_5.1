package com.grupio.helper;


import com.grupio.notes.NotesData;
import com.grupio.notes.NotesListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 22/11/16.
 */

public class NotesHelper {

    public List<NotesData> parseList(String response) {

        List<NotesData> mNotesList = new ArrayList<>();

        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jObj != null) {

            JSONArray jArray = null;
            try {
                jArray = jObj.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jArray != null && jArray.length() > 0) {

                NotesData mNotesData;

                for (int i = 0; i < jArray.length(); i++) {
                    mNotesData = new NotesData();

                    try {
                        mNotesData.setNoteId(jArray.getJSONObject(i).getString("note_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mNotesData.setNoteText(jArray.getJSONObject(i).getString("note_text"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    try {
                        mNotesData.setNoteDate(jArray.getJSONObject(i).getString("note_date"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mNotesData.setNoteReminder(jArray.getJSONObject(i).getString("note_reminder"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        String type = jArray.getJSONObject(i).getString("note_type");
                        mNotesData.setNoteType(type.equals("0") ? NotesListActivity.My_NOTES : NotesListActivity.THINGS_TO_DO);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mNotesData.setLastOperation("1");
                    mNotesData.setNoteSync("1");

                    mNotesList.add(mNotesData);
                }
            }
        }

        return mNotesList;
    }

}
