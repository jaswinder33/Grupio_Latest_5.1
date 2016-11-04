package com.grupio.venuemaps;

import android.content.Context;
import android.view.View;

import com.grupio.base.SingleElementListBaseAdapter;
import com.grupio.data.SurveyData;

/**
 * Created by JSN on 3/11/16.
 */

public class SurveyAdapter extends SingleElementListBaseAdapter<SurveyData> {

    public SurveyAdapter(Context context) {
        super(context);
    }

    @Override
    public void getView(int position, SingleElementListBaseAdapter.Holder holder) {
        holder.name.setText(getItem(position).getName());
        holder.imageView.setVisibility(View.GONE);
    }
}
