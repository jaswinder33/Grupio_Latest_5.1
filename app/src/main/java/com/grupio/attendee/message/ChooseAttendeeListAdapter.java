package com.grupio.attendee.message;

import android.content.Context;
import android.view.View;

import com.grupio.R;
import com.grupio.attendee.AttendeeListAdapter;
import com.grupio.data.AttendeesData;

import java.util.ArrayList;


/**
 * Created by JSN on 20/10/16.
 */

/**
 * https://www.javacodegeeks.com/2013/12/advanced-java-generics-retreiving-generic-type-arguments.html
 * read this article
 */

public class ChooseAttendeeListAdapter extends AttendeeListAdapter {
    private ArrayList<AttendeesData> selectedAttendee = new ArrayList<>();
    private String attendeeId;

    public ChooseAttendeeListAdapter(Context context, ArrayList<AttendeesData> selectedAttendeeList, String attendeeId) {
        super(context);
        this.attendeeId = attendeeId;
        selectedAttendee.addAll(selectedAttendeeList);
    }

    @Override
    public void handleGetView(int position, ViewHolder mHolder) {
        super.handleGetView(position, mHolder);
        mHolder.mButton.setVisibility(View.VISIBLE);

        if (getItem(position).isAttendeeSelected()) {
            mHolder.mButton.setImageResource(R.drawable.ic_success);
        } else {
            mHolder.mButton.setImageResource(R.drawable.ic_minus);
        }


        mHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getItem(position).getAttendee_id().equals(attendeeId)) {

                    if (getItem(position).isAttendeeSelected()) {
                        getItem(position).setAttendeeSelected(false);

                        for (int i = 0; i < selectedAttendee.size(); i++) {
                            if (getItem(position).getAttendee_id().equals(selectedAttendee.get(i).getAttendee_id())) {
                                selectedAttendee.remove(i);
                                break;
                            }
                        }
                    } else {
                        getItem(position).setAttendeeSelected(true);
                        selectedAttendee.add(getItem(position));
                    }

                    notifyDataSetChanged();
                }
            }
        });
    }

    public ArrayList<AttendeesData> getSelectedAttendee() {
        return selectedAttendee;
    }
}
