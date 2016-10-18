package com.grupio.helper;

/**
 * Created by JSN on 29/8/16.
 */
public class SessionWatcher {

    private SessionInter mSessionInter;
    private static SessionWatcher mSessionWatcher;


    public static SessionWatcher getInstance(){
        if(mSessionWatcher == null){
            mSessionWatcher = new SessionWatcher();
        }

        return mSessionWatcher;
    }

    public void registerListener(SessionInter mSessionInter) {
        this.mSessionInter = mSessionInter;
    }

    public void unregisterListener() {
        mSessionInter = null;
    }

    public void setDate(String date) {
        if (mSessionInter != null) {
            mSessionInter.updateDate(date);
        }
    }

    public interface SessionInter {
        void updateDate(String date);
    }
}
