package com.grupio.base;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.dao.EventDAO;
import com.grupio.db.EventTable;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by JSN on 19/10/16.
 */
public abstract class BaseListAdapter<Person, Holder> extends ArrayAdapter<Person> implements SectionIndexer, StickyListHeadersAdapter {

    public boolean isFirstName = false;
    Holder mHolder;
    private String eventColor;
    private boolean showHeaders = true;

    public BaseListAdapter(Context context) {
        super(context, 0);
        eventColor = EventDAO.getInstance(getContext()).getValue(EventTable.COLOR_THEME);
        String name_order = EventDAO.getInstance(context).getValue(EventTable.NAME_ORDER);
        isFirstName = name_order.equals("firstname_lastname");
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int section) {


        if (section == 35) {
            return 0;
        }
        for (int i = 0; i < getCount(); i++) {

            String l = "";
            if (isFirstName) {
                l = getFirstName(i);

            } else {
                l = getLastName(i);
            }
            char firstChar = l.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {

        HeaderViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.headertextview, parent, false);
            holder = new HeaderViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        if (isFirstName) {
            if (!getFirstName(position).equals("")) {
                holder.session_track.setText(getFirstName(position).substring(0, 1).toUpperCase());
            }
        } else {
            if (!getLastName(position).equals("")) {
                holder.session_track.setText(getLastName(position).substring(0, 1).toUpperCase());
            }
        }

        if (!eventColor.equals("")) {
            holder.trackLayoutId.setBackgroundColor(Color.parseColor(eventColor));
        }

        if (!showHeaders) {
            holder.trackLayoutId.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {

        char ch = 0;

        if (isFirstName) {
            if (!getFirstName(position).equals("")) {
                ch = getFirstName(position).toLowerCase().substring(0, 1).charAt(0);
            }
        } else {
            if (!getLastName(position).equals("")) {
                ch = getLastName(position).toLowerCase().substring(0, 1).charAt(0);
            }
        }
        return ch;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(getLayout(), parent, false);
            mHolder = setViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (Holder) convertView.getTag();
        }

        handleGetView(position, mHolder);

        return convertView;
    }

    public void setShowHeaders(boolean flag) {
        showHeaders = flag;
    }

    public abstract String getFirstName(int position);

    public abstract String getLastName(int position);

    public abstract int getLayout();

    public abstract void handleGetView(int position, Holder mHolder);

    public abstract Holder setViewHolder(View convertView);

    class HeaderViewHolder {
        TextView session_track;
        View trackLayoutId;

        public HeaderViewHolder(View view) {
            session_track = (TextView) view.findViewById(R.id.session_track);
            trackLayoutId = view.findViewById(R.id.trackLayoutId);
        }
    }

}
