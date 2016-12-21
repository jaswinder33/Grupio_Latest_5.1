package com.grupio.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.attendee.AttendeeListAdapter;
import com.grupio.data.AttendeesData;

/**
 * Created by JSN on 20/12/16.
 */

public class InviteeAdapter extends AttendeeListAdapter {

    public static final String DECLINE = "-1";
    public static final String WAIT = "-1";
    public static final String ACCEPT = "2";

    public InviteeAdapter(Context context) {
        super(context);
    }

    @Override
    public void handleGetView(int position, View view, ViewHolder mHolder) {
        super.handleGetView(position, view, mHolder);

        String status = getItem(position).getMeetingStatus();

        if (status.equals(DECLINE)) {
            mHolder.mButton.setImageResource(R.drawable.ic_cross_mark);
        } else if (status.equals(ACCEPT)) {
            mHolder.mButton.setImageResource(R.drawable.ic_success);
        } else {
            mHolder.mButton.setImageResource(R.drawable.ic_clock);
        }

        mHolder.mButton.setColorFilter(getContext().getResources().getColor(R.color.exhibitor_buttons_blue));
        mHolder.mButton.setVisibility(View.VISIBLE);


        mHolder.mButton.setOnClickListener(view1 -> {

            Object[] params = BaseActivity.CustomDialog.getDialog(getContext()).showWithCustomView(R.layout.layout_invitee_action, CustomDialogHolder.class);
            CustomDialogHolder mCustomDialogHolder = (CustomDialogHolder) params[0];
            AlertDialog dialog = (AlertDialog) params[1];

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            GradientDrawable backgroundDrawable1 = (GradientDrawable) mCustomDialogHolder.dialogBackground.getBackground();
            backgroundDrawable1.setColor(getContext().getResources().getColor(R.color.white));

            GradientDrawable backgroundDrawable2 = (GradientDrawable) mCustomDialogHolder.cancelBtn.getBackground();
            backgroundDrawable2.setColor(getContext().getResources().getColor(R.color.white));

            mCustomDialogHolder.acceptBtn.setOnClickListener(view2 -> {
                updateStatus(getItem(position), position, ACCEPT);
                dialog.dismiss();
            });
            mCustomDialogHolder.declineBtn.setOnClickListener(view2 -> {
                updateStatus(getItem(position), position, DECLINE);
                dialog.dismiss();
            });
            mCustomDialogHolder.cancelBtn.setOnClickListener(view2 -> dialog.dismiss());
        });
    }

    public void updateStatus(AttendeesData mData, int position, String status) {
        ((MeetingDetailsActivity) getContext()).updateMeetingStatus(mData, position, status);
    }

    public class CustomDialogHolder {

        TextView acceptBtn, declineBtn, cancelBtn;
        LinearLayout dialogBackground;

        public CustomDialogHolder(View view) {
            acceptBtn = (TextView) view.findViewById(R.id.accept);
            declineBtn = (TextView) view.findViewById(R.id.decline);
            cancelBtn = (TextView) view.findViewById(R.id.cancel);
            dialogBackground = (LinearLayout) view.findViewById(R.id.background);
        }
    }
}
