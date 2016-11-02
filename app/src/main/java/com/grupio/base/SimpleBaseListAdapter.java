package com.grupio.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by JSN on 24/10/16.
 */

public abstract class SimpleBaseListAdapter<T, ViewHolder> extends ArrayAdapter<T> {

    protected ViewHolder mHolder;

    public SimpleBaseListAdapter(Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(getLayout(), parent, false);
            mHolder = setViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        getView(position, mHolder);

        return convertView;
    }

    public abstract int getLayout();

    public abstract ViewHolder setViewHolder(View convertView);

    public abstract void getView(int position, ViewHolder holder);

}
