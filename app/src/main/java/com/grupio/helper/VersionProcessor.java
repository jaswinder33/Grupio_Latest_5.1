package com.grupio.helper;

import com.grupio.dao.VersionDao;
import com.grupio.data.VersionData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 19/8/16.
 */
public class VersionProcessor {

    /*
    {

    "custommenu": {
        "version": "1"
    },
    "menu": {
        "version": "332"
    },
    "graphics": {
        "version": "39"
    },
    "track": {
        "version": "24"
    },
    "language": {
        "version": "13"
    },
    "Ads": {
        "version": "403"
    },
    "surveys": {
        "version": "362"
    },
    "logistics": {
        "version": "104"
    },
    "map": {
        "version": "65"
    },
    "newsfeed": {
        "version": "5"
    },
    "matches": {
        "version": "4"
    },
    "livefeed": {
        "version": "75"
    },
    "sponsor": {
        "version": "334"
    },
    "exhibitor": {
        "version": "538"
    },
    "attendee": {
        "version": 1
    },
    "speaker": {
        "version": "747"
    },
    "schedule": {
        "version": "707"
    },
    "event": {
        "version": "735"
    }

}
     */

    public List<VersionData> parseResult(String result) {

        List<VersionData> mVersionList = new ArrayList<>();

        JSONObject mObj = null;
        try {
            mObj = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mObj != null) {
            mVersionList.add(getInstance(VersionDao.CUSTOM_MENU_VERSION, "custommenu", mObj));
            mVersionList.add(getInstance(VersionDao.MENU_VERSION, "menu", mObj));
            mVersionList.add(getInstance(VersionDao.GRAPHICS_VERSION, "graphics", mObj));
            mVersionList.add(getInstance(VersionDao.TRACK_VERSION, "track", mObj));
            mVersionList.add(getInstance(VersionDao.LANGUAGE_VERSION, "language", mObj));
            mVersionList.add(getInstance(VersionDao.ADS_VERSION, "Ads", mObj));
            mVersionList.add(getInstance(VersionDao.SURVEYS_VERSION, "surveys", mObj));
            mVersionList.add(getInstance(VersionDao.LOGISTICS_VERSION, "logistics", mObj));
            mVersionList.add(getInstance(VersionDao.MAP_VERSION, "map", mObj));
            mVersionList.add(getInstance(VersionDao.NEWSFEEDMENU_VERSION, "newsfeed", mObj));
            mVersionList.add(getInstance(VersionDao.MATCHES_VERSION, "matches", mObj));
            mVersionList.add(getInstance(VersionDao.LIVEFEED_VERSION, "livefeed", mObj));
            mVersionList.add(getInstance(VersionDao.SPONSORS_VERSION, "sponsor", mObj));
            mVersionList.add(getInstance(VersionDao.EXHIBITORS_VERSION, "exhibitor", mObj));
            mVersionList.add(getInstance(VersionDao.ATTENDEE_VERSION, "attendee", mObj));
            mVersionList.add(getInstance(VersionDao.SPEAKER_VERSION, "speaker", mObj));
            mVersionList.add(getInstance(VersionDao.SCHEDULE_VERSION, "schedule", mObj));
            mVersionList.add(getInstance(VersionDao.EVENT_VERSION, "event", mObj));
        }

        return mVersionList;

    }

    public VersionData getInstance(String name, String variable, JSONObject mObj) {
        VersionData versionData = new VersionData();
        try {
            versionData.name = name;
            versionData.newVersion = mObj.getJSONObject(variable).getString("version");
            return versionData;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return versionData;
    }
}
