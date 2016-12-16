package com.grupio.base;

/**
 * Created by JSN on 8/12/16.
 */


import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.Collection;

/**
 * This class is base of all classes that are used to set view in AttendeeAdapter, SpeakerAdapter, SponsorAdapter, SessionAdapter, ExhibitorAdapter
 */
public abstract class BaseSetData<T, VH, K> {

    private K mAdpater;
    private Context mContext;

    public BaseSetData(Context mContext) {
        this.mContext = mContext;
    }

    public Context getContext() {
        return mContext;
    }

    public abstract void setData(T data, VH mHolder);

    protected K getAdapter() {
        return mAdpater;
    }

    public void setAdapter(K adapter) {
        this.mAdpater = adapter;
    }

    protected void notifyDataSetChanged() {
        ((ArrayAdapter<T>) mAdpater).notifyDataSetChanged();
    }

    protected int getPosition(T data) {
        return ((ArrayAdapter<T>) mAdpater).getPosition(data);
    }

    protected T getItem(int position) {
        return ((ArrayAdapter<T>) mAdpater).getItem(position);
    }

    protected int getCount() {
        return ((ArrayAdapter<T>) mAdpater).getCount();
    }

    protected void remove(T item) {
        ((ArrayAdapter<T>) mAdpater).remove(item);
    }

    protected void addAll(Collection<? extends T> collection) {
        ((ArrayAdapter<T>) mAdpater).addAll(collection);
    }


}
