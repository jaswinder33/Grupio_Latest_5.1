package com.grupio.apis;

import android.content.Context;

/**
 * Created by JSN on 12/12/16.
 */

public class MeetingAPI extends BaseAsyncTask<Void, String> {
    public MeetingAPI(Context mcontext) {
        super(mcontext);
    }

    @Override
    public String endPoint() {
        return null;
    }

    @Override
    public String handleBackground(Void... params) {
        return null;
    }
}
