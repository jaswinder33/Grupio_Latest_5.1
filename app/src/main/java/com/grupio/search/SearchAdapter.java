package com.grupio.search;

import android.content.Context;
import android.view.View;

import com.grupio.R;
import com.grupio.attendee.ListBaseAdapter;
import com.grupio.attendee.SetAttendeeData;
import com.grupio.attendee.SetExhibitorData;
import com.grupio.attendee.SetSessionData;
import com.grupio.attendee.SetSpeakerData;
import com.grupio.attendee.SetSponsorData;
import com.grupio.attendee.SponsorAdapter;
import com.grupio.base.BaseHolder;
import com.grupio.base.BaseListAdapter;
import com.grupio.dao.EventDAO;
import com.grupio.data.AttendeesData;
import com.grupio.data.ExhibitorData;
import com.grupio.data.ScheduleData;
import com.grupio.data.SpeakerData;
import com.grupio.data.SponsorData;
import com.grupio.db.EventTable;
import com.grupio.interfaces.Person;
import com.grupio.schedule.ScheduleAdapter;

/**
 * Created by JSN on 7/12/16.
 */
public class SearchAdapter extends BaseListAdapter<Person, BaseHolder> {

    public static final String TAG = "SearchAdapter";
    boolean showAttendeeImage = false;
    boolean hideSpeakerImage = false;
    boolean showTrackColor = false;
    boolean showExhibitorImage = true;

    public SearchAdapter(Context context) {
        super(context);
        showAttendeeImage = EventDAO.getInstance(getContext()).getValue(EventTable.HIDE_ATTENDEE_IMAGES).equalsIgnoreCase("n");
        hideSpeakerImage = EventDAO.getInstance(context).getValue(EventTable.HIDE_SPEAKER_IMAGES).equals("n");
        showTrackColor = EventDAO.getInstance(context).getValue(EventTable.SHOWTRACKS).equals("y");
        showExhibitorImage = EventDAO.getInstance(context).getValue(EventTable.HIDE_EXHIBITOR_IMAGES).equals("n");
    }

    @Override
    public String getFirstName(int position) {

        Person mobj = getItem(position);

        if (mobj instanceof AttendeesData) {
            return "Attendee";
        } else if (mobj instanceof ScheduleData) {
            return "Sessions";
        } else if (mobj instanceof SpeakerData) {
            return "Speakers";
        } else if (mobj instanceof SponsorData) {
            return "Sponsors";
        } else if (mobj instanceof ExhibitorData) {
            return "Exhibitors";
        }

        return "";
    }

    @Override
    public int getItemViewType(int position) {

        Person mobj = getItem(position);

        if (mobj instanceof AttendeesData || mobj instanceof SpeakerData || mobj instanceof ExhibitorData) {
            return 1;
        } else if (mobj instanceof ScheduleData) {
            return 2;
        } else if (mobj instanceof SponsorData) {
            return 3;
        }

        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public String getLastName(int position) {
        return getFirstName(position);
    }

    @Override
    public int getLayout(int position) {

        // Layout for Attendee, Speaker, Exhibitor
        if (getItemViewType(position) == 1) {
            return R.layout.layout_attendee_list_child;
        }
        // Layout for Sessions
        else if (getItemViewType(position) == 2) {
            return R.layout.layout_session_list_child;
        }
        // Layout for Sponsor
        else if (getItemViewType(position) == 3) {
            return R.layout.layout_sponsor;
        }

        return 0;
    }

    @Override
    public void handleGetView(int position, View view, BaseHolder mHolder) {

        Person mData = getItem(position);
        if (mData instanceof AttendeesData) {
            SetAttendeeData<SearchAdapter> mSetAttendeeData = new SetAttendeeData(getContext());
            mSetAttendeeData.setAdapter(this);
            mSetAttendeeData.setShowAttendeeImage(showAttendeeImage).isFirstName(isFirstName).setData((AttendeesData) mData, (ListBaseAdapter.ViewHolder) mHolder);
        } else if (mData instanceof SpeakerData) {
            System.out.println("SearchAdapter.handleGetView: " + mHolder.getClass().getName());
            SetSpeakerData<SearchAdapter> mSetSpeakerData = new SetSpeakerData(getContext());
            mSetSpeakerData.setAdapter(this);
            mSetSpeakerData.setHideSpeakerImage(hideSpeakerImage)
                    .isFirstName(isFirstName)
                    .setData((SpeakerData) mData, (ListBaseAdapter.ViewHolder) mHolder);
        } else if (mData instanceof SponsorData) {
            SetSponsorData<SearchAdapter> mSetSponsorData = new SetSponsorData(getContext());
            mSetSponsorData.setAdapter(this);
            mSetSponsorData.setData((SponsorData) mData, (SponsorAdapter.Holder) mHolder);
        } else if (mData instanceof ScheduleData) {
            SetSessionData<SearchAdapter> mSetSessionData = new SetSessionData(getContext());
            mSetSessionData.setAdapter(this);
            mSetSessionData.setShowTrackColor(showTrackColor)
                    .setData(mData, (ScheduleAdapter.ViewHolder) mHolder);
        } else if (mData instanceof ExhibitorData) {
            SetExhibitorData<SearchAdapter> mSetExhibitorData = new SetExhibitorData<>(getContext());
            mSetExhibitorData.setAdapter(this);
            mSetExhibitorData.setShowExhibitorImage(showExhibitorImage).setData((ExhibitorData) mData, (ListBaseAdapter.ViewHolder) mHolder);
        }

    }

    @Override
    public BaseHolder setViewHolder(View convertView, int position) {
        Person mData = getItem(position);
        // Layout for Attendee, Speaker, Exhibitor
        if (getItemViewType(position) == 1) {
            return new ListBaseAdapter.ViewHolder(convertView);
        }
        // Layout for Sessions
        else if (getItemViewType(position) == 2) {
            return new ScheduleAdapter.ViewHolder(convertView);
        }
        // Layout for Sponsor
        else if (getItemViewType(position) == 3) {
            return new SponsorAdapter.Holder(convertView);
        }
        return new SearchHolder(convertView);
    }

    public static class SearchHolder implements BaseHolder {
        public SearchHolder(View view) {

        }
    }
}
