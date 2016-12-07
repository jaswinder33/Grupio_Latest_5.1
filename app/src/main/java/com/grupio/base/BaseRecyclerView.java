package com.grupio.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by JSN on 5/12/16.
 */

public abstract class BaseRecyclerView<T, Holder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<Holder> {

    private Context mContext;
    private List<T> mItemlist = new ArrayList<>();
    private OnClick mListener;

    public BaseRecyclerView(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(getLayout(), parent, false);
        return getViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mItemlist.size();
    }

    public abstract int getLayout();

    public abstract Holder getViewHolder(View view);

    public void add(T item) {
        mItemlist.add(item);
    }

    public void addAt(int pos, T item) {
        mItemlist.add(pos, item);
    }

    public void addAll(Collection<? extends T> collection) {
        mItemlist.addAll(collection);
    }

    public void clear() {
        mItemlist.clear();
    }

    public Context getContext() {
        return mContext;
    }

    public T getItem(int position) {
        return mItemlist.get(position);
    }

    public void setOnClickListener(OnClick mListener) {
        this.mListener = mListener;
    }

    public OnClick getListener() {
        return mListener;
    }

    public interface OnClick<T> {
        void onClick(T data, int position);
    }
}
