package com.grupio;

import android.content.Context;
import android.view.View;

import com.grupio.attendee.ListBaseAdapter;
import com.grupio.attendee.SetExhibitorData;
import com.grupio.dao.EventDAO;
import com.grupio.data.ExhibitorData;
import com.grupio.db.EventTable;

/**
 * Created by JSN on 21/10/16.
 */

public class ExhibitorAdapter extends ListBaseAdapter<ExhibitorData> {

    boolean showExhibitorImage = true;

    public ExhibitorAdapter(Context context) {
        super(context);
        showExhibitorImage = EventDAO.getInstance(context).getValue(EventTable.HIDE_EXHIBITOR_IMAGES).equals("n");
    }

    @Override
    public String getFirstName(int position) {
        return getItem(position).getName();
    }

    @Override
    public String getLastName(int position) {
        return getItem(position).getName();
    }

    @Override
    public void handleGetView(int position, View view, ViewHolder mHolder) {


        SetExhibitorData<ExhibitorAdapter> mSetExhibitorData = new SetExhibitorData<>(getContext());
        mSetExhibitorData.setAdapter(this);
        mSetExhibitorData.setShowExhibitorImage(showExhibitorImage).setData(getItem(position), mHolder);


       /* mHolder.name.setText(getItem(position).getName());

        *//**
         * Implement this after localizaton implementation
         *//*
//        if(! getItem(position).getLocation().equals("")){
//            if(!LocalisationDataProcessor.BOOTH.equals(""))
//                mHolder.title.setText(LocalisationDataProcessor.BOOTH + ":" + getItem(position).getLocation());
//            else
//                mHolder.title.setText(getItem(position).getLocation());
//        }
//        else{
//            mHolder.title.setText("");
//        }

        mHolder.title.setText(getItem(position).getLocation());

        if (showExhibitorImage) {
            ImageLoader.getInstance().displayImage(getItem(position).getImage(), mHolder.image, Utility.getDisplayOptionsAttendee());
        }

        mHolder.mButton.setVisibility(View.VISIBLE);

        if (getItem(position).isFav()) {
            mHolder.mButton.setImageResource(R.drawable.ic_star_on);
            mHolder.mButton.setColorFilter(Color.BLUE);
        } else {
            mHolder.mButton.setImageResource(R.drawable.ic_star_off);
            mHolder.mButton.setColorFilter(Color.BLACK);
        }


        mHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(position).isFav()) {
                    getItem(position).setIsFav("0");
                    ExhibitorDAO.getInstance(getContext()).likeUnlikeExhb(getItem(position).getExhibitorId(), 0);
                } else {
                    getItem(position).setIsFav("1");
                    ExhibitorDAO.getInstance(getContext()).likeUnlikeExhb(getItem(position).getExhibitorId(), 1);
                }
                notifyDataSetChanged();
            }
        });*/


    }
}
