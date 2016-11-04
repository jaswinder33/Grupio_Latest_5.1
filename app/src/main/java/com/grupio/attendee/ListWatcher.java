package com.grupio.attendee;

/**
 * Created by JSN on 11/8/16.
 */
public class ListWatcher {

    private static ListWatcher mListWatcher;
    private Watcher mListener;

    private ListWatcher() {
    }

    public static ListWatcher getInstance() {
        if (mListWatcher == null) {
            mListWatcher = new ListWatcher();
        }
        return mListWatcher;
    }

    public void registerListener(Watcher mListener) {
        this.mListener = mListener;
    }

    public void unregisterListener() {
        mListener = null;
    }

    public void notifyList() {
        if (mListener != null) {
            mListener.refreshList();
        }
    }

    public interface Watcher {
        void refreshList();
    }

}
