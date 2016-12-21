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
import com.grupio.data.ScheduleData;
import com.grupio.data.SponsorData;
import com.grupio.db.EventTable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by JSN on 19/10/16.
 */
public abstract class BaseListAdapter<T, Holder> extends ArrayAdapter<T> implements SectionIndexer, StickyListHeadersAdapter {

    protected boolean isFirstName = false;

    private T mT;
    private String eventColor;
    private boolean showHeaders = true;

    private boolean isSession = false;
    private boolean isHeaderFullName = false;

    public BaseListAdapter(Context context) {
        super(context, 0);
        eventColor = EventDAO.getInstance(getContext()).getValue(EventTable.COLOR_THEME);
        String name_order = EventDAO.getInstance(context).getValue(EventTable.NAME_ORDER);
        isFirstName = name_order.equals("firstname_lastname");

        findPersonType(getClass());

        if (mT instanceof ScheduleData) {
            isSession = true;
        }

        if (mT instanceof SponsorData) {
            isHeaderFullName = true;
        }

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

        if (!eventColor.equals("")) {
            holder.session_track.setBackgroundColor(Color.parseColor(eventColor));
        }

        if (!showHeaders) {
            holder.session_track.setVisibility(View.GONE);
        }


        if (isSession) {

            ScheduleData mScheduleData = (ScheduleData) getItem(position);

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();

            try {
                cal.setTime(sdf1.parse(mScheduleData.getStart_time()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat sdf2 = new SimpleDateFormat("hh aaa");
            sdf2.format(cal.getTime());

            holder.session_track.setText(sdf2.format(cal.getTime()));

        } else {

            if (isHeaderFullName) {
                holder.session_track.setText(getFirstName(position));
            } else {
                if (isFirstName) {
                    if (!getFirstName(position).equals("")) {
                        holder.session_track.setText(getFirstName(position).substring(0, 1).toUpperCase());
                    }
                } else {
                    if (!getLastName(position).equals("")) {
                        holder.session_track.setText(getLastName(position).substring(0, 1).toUpperCase());
                    }
                }
            }
        }

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {

        if (isSession) {
            ScheduleData mScheduleData = (ScheduleData) getItem(position);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(sdf1.parse(mScheduleData.getStart_time()));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            long time = cal.getTimeInMillis();

            String time1 = mScheduleData.getStartHour("" + time);

            long timenew = Long.parseLong(time1.substring(0, time1.indexOf(":")));

            return timenew;
        } else {
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
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder mHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(getLayout(position), parent, false);
            mHolder = setViewHolder(convertView, position);
            convertView.setTag(mHolder);
        } else {
            mHolder = (Holder) convertView.getTag();
        }

        handleGetView(position, convertView, mHolder);

        return convertView;
    }

    public void setShowHeaders(boolean flag) {
        showHeaders = flag;
    }

    public void setFullHeader(boolean flag) {
        isHeaderFullName = flag;
    }

    public abstract String getFirstName(int position);

    public abstract String getLastName(int position);

    public abstract int getLayout(int position);

    public abstract void handleGetView(int position, View view, Holder mHolder);

    public abstract Holder setViewHolder(View convertView, int position);


    private void findPersonType(Class<?> classObject) {

        Class<?> classObj = classObject;

        Type typeParam = classObj.getGenericSuperclass();

        while (!(typeParam instanceof ParameterizedType)) {
            classObj = classObj.getSuperclass();
            typeParam = classObj.getGenericSuperclass();

        }

        Type[] typeArray = ((ParameterizedType) typeParam).getActualTypeArguments();

        for (Type type1 : typeArray) {
            try {
                mT = ((Class<T>) type1).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    protected T getAdapterType() {
        return mT;
    }

    class HeaderViewHolder {
        TextView session_track;

        public HeaderViewHolder(View view) {
            session_track = (TextView) view.findViewById(R.id.session_track);
        }
    }
}
