package com.grupio.search;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.base.BaseListAdapter;
import com.grupio.data.AttendeesData;
import com.grupio.data.ExhibitorData;
import com.grupio.data.ScheduleData;
import com.grupio.data.SpeakerData;
import com.grupio.data.SponsorData;
import com.grupio.interfaces.Person;

/**
 * Created by JSN on 7/12/16.
 */

public class SearchAdapter extends BaseListAdapter<Person, SearchAdapter.SearchHolder> {

    public SearchAdapter(Context context) {
        super(context);
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
    public void handleGetView(int position, SearchHolder mHolder) {

    }

    @Override
    public SearchHolder setViewHolder(View convertView, int position) {
        return new SearchHolder(convertView, position);
    }

    public class SearchHolder {

        public ImageView imageView;
        public ImageButton mLikeBtn, addBtn;
        public TextView sessionName, speakerList, sessionDate, sessionLocation, sessionTrack;
        public ListView mChildSessionList;
        public TextView name, title;
        public ImageView image;
        public TextView presenceTextView;
        public ImageButton mButton;

        public SearchHolder(View view, int position) {
            if (position == 1) {
                name = (TextView) view.findViewById(R.id.attendee_name);
                title = (TextView) view.findViewById(R.id.attendee_company_title);
                image = (ImageView) view.findViewById(R.id.attendee_image);
                presenceTextView = (TextView) view.findViewById(R.id.presenceTextView);
                mButton = (ImageButton) view.findViewById(R.id.addItem);
            } else if (position == 2) {
                mLikeBtn = (ImageButton) view.findViewById(R.id.favBtn);
                addBtn = (ImageButton) view.findViewById(R.id.addBtn);
                sessionName = (TextView) view.findViewById(R.id.sessionName);
                speakerList = (TextView) view.findViewById(R.id.sessionSpeakerList);
                sessionDate = (TextView) view.findViewById(R.id.sessionDate);
                sessionLocation = (TextView) view.findViewById(R.id.sessionLocation);
                sessionTrack = (TextView) view.findViewById(R.id.sessionTrack);
                mChildSessionList = (ListView) view.findViewById(R.id.childList);
            } else if (position == 3) {
                name = (TextView) view.findViewById(R.id.nameSponsor);
                imageView = (ImageView) view.findViewById(R.id.imageSponsor);
            }
        }
    }

}
