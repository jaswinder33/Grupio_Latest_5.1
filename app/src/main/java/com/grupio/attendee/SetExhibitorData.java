package com.grupio.attendee;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.base.BaseSetData;
import com.grupio.dao.ExhibitorDAO;
import com.grupio.data.ExhibitorData;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by JSN on 8/12/16.
 */

public class SetExhibitorData<T> extends BaseSetData<ExhibitorData, AttendeeListAdapter.ViewHolder, T> {

    boolean showExhibitorImage = true;

    public SetExhibitorData(Context mContext) {
        super(mContext);
    }

    @Override
    public void setData(ExhibitorData data, ListBaseAdapter.ViewHolder mHolder) {

        int position = getPosition(data);

        mHolder.name.setText(getItem(position).getName());

        /**
         * Implement this after localizaton implementation
         */
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
        });
    }

    public SetExhibitorData setShowExhibitorImage(boolean showExhibitorImage) {
        this.showExhibitorImage = showExhibitorImage;
        return this;
    }


}
