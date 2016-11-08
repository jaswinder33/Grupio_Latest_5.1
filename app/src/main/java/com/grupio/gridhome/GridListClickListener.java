package com.grupio.gridhome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.grupio.R;
import com.grupio.activities.WebViewActivity;
import com.grupio.animation.SlideOut;
import com.grupio.attendee.ListActivity;
import com.grupio.dao.SessionTracksDAO;
import com.grupio.data.MenuData;
import com.grupio.home.HomeActivity;
import com.grupio.login.LoginActivity;
import com.grupio.logistics.LogisticsActivity;
import com.grupio.message.MessageActivity;
import com.grupio.schedule.ScheduleTrackListActivity;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;
import com.grupio.venuemaps.VenueMapActivity;

/**
 * Created by JSN on 4/10/16.
 */

public class GridListClickListener implements RecyclerView.OnItemTouchListener {

    GestureDetector mGestureDetector;
    MenuClick scheduleListClick = mBundle -> {
    };
    private Context context;
    MenuClick homeclick = mBundle -> sendIntent(HomeActivity.class, mBundle);
    MenuClick messageclick = mBundle -> sendIntent(MessageActivity.class, mBundle);
    MenuClick attendeeclick = mBundle -> sendIntent(ListActivity.class, mBundle);
    MenuClick speakerclick = mBundle -> sendIntent(ListActivity.class, mBundle);
    MenuClick exhibitorClick = mBundle -> sendIntent(ListActivity.class, mBundle);
    MenuClick loginClick = (Bundle mBundle) -> sendIntent(LoginActivity.class, mBundle);
    MenuClick logisticsClick = (Bundle mBundle) -> sendIntent(LogisticsActivity.class, mBundle);
    MenuClick venueMapsClick = (Bundle mbundle) -> sendIntent(VenueMapActivity.class, mbundle);
    MenuClick discussionBoardClick = mBundle -> sendIntent(WebViewActivity.class, mBundle);
    MenuClick scheduleTracklistClick = mBundle -> sendIntent(ScheduleTrackListActivity.class, mBundle);

    public GridListClickListener(Context context) {
        this.context = context;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    public void sendIntent(Class<?> classInstance, Bundle mBundle) {
        Intent mIntent = new Intent(context, classInstance);
        if (mBundle != null) {
            mIntent.putExtras(mBundle);
        }
        context.startActivity(mIntent);
        SlideOut.getInstance().startAnimation((Activity) context);
    }

    public void performClick(Bundle mBundle, MenuClick click) {
        click.onMenuClick(mBundle);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());

        if (childView != null && mGestureDetector.onTouchEvent(e)) {

            int position = view.getChildAdapterPosition(childView);
            MenuData menu = ((MenuListAdapter) view.getAdapter()).getItemAtPos(position);

            Bundle mBundle = null;
            switch (menu.getMenuName()) {

                case "activity_feed":
                    //Leave pendingGr
                    break;

                case "detail":
                    performClick(null, homeclick);
                    break;


                case "schedule":
                    boolean isTrackPresent = SessionTracksDAO.getInstance(context).checkIfTrackExist();
                    if (isTrackPresent) {
                        performClick(mBundle, scheduleTracklistClick);
                    } else {
                        performClick(mBundle, scheduleListClick);
                    }
                    break;

                case "mycalendar":
                    break;

                case "speakers":
                    mBundle = new Bundle();
                    mBundle.putString("type", "Speaker");
                    performClick(mBundle, speakerclick);
                    break;

                case "exhibitors":
                    mBundle = new Bundle();
                    mBundle.putString("type", "Exhibitors");
                    performClick(mBundle, exhibitorClick);
                    break;

                case "sponsors":
                    break;

                case "maps":
                    mBundle = new Bundle();
                    mBundle.putString("type", "venueMaps");
                    performClick(mBundle, venueMapsClick);
                    break;


                case "live":
                    mBundle = new Bundle();
                    mBundle.putString("type", "Live");
                    performClick(mBundle, venueMapsClick);
                    break;

                case "social":
                    break;

                case "logistics":
                    mBundle = new Bundle();
                    mBundle.putString("type", "logistics1");
                    performClick(mBundle, logisticsClick);
                    break;

                case "logistics2":
                    mBundle = new Bundle();
                    mBundle.putString("type", "logistics2");
                    performClick(mBundle, logisticsClick);
                    break;

                case "logistics3":
                    mBundle = new Bundle();
                    mBundle.putString("type", "logistics3");
                    performClick(mBundle, logisticsClick);
                    break;
                case "my account":
                    break;

                case "survey":
                    mBundle = new Bundle();
                    mBundle.putString("type", "Survey");
                    performClick(mBundle, venueMapsClick);
                    break;

                case "attendees":
                    mBundle = new Bundle();
                    mBundle.putString("type", "Attendee");
                    performClick(mBundle, attendeeclick);
                    break;

                case "matches":
                    break;

                case "messages":
                    if (Preferences.getInstances(context).getUserInfo() == null) {
                        mBundle = new Bundle();
                        mBundle.putString("from", "messages");
                        performClick(mBundle, loginClick);
                    } else {
                        performClick(mBundle, messageclick);
                    }

                    break;

                case "Alerts":
                    mBundle = new Bundle();
                    mBundle.putString("type", "alert");
                    performClick(mBundle, venueMapsClick);
                    break;

                case "downloads":
                    break;

                case "qrcode":
                    break;

                case "search":
                    break;

                case "discussion_board":

                    if (Preferences.getInstances(context).getUserInfo() == null) {
                        mBundle = new Bundle();
                        mBundle.putString("from", "discussion_board");
                        performClick(mBundle, loginClick);
                    } else {
                        String url = context.getString(R.string.base_url) + context.getString(R.string.discussion_board_api) + ConstantData.EVENT_ID;
                        mBundle = new Bundle();
                        mBundle.putString("url", url);
                        performClick(mBundle, discussionBoardClick);
                    }


                    break;

                case "my_notes":
                    break;

                case "things_to_do":
                    break;
                case "photo_gallery":
                    break;
                case "i2i":
                    break;

                case "chat":
                    break;

                case "game":
                    break;

                default:
                    break;
            }
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
