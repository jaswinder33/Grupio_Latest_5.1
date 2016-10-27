package com.grupio.venuemaps;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.base.SimpleBaseListAdapter;
import com.grupio.data.MapsData;

/**
 * Created by JSN on 24/10/16.
 */

public class MapsListAdapter extends SimpleBaseListAdapter<MapsData, MapsListAdapter.Holder> {

    public MapsListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.layout_single_element_list_child;
    }

    @Override
    public Holder setViewHolder(View convertView) {
        return new Holder(convertView);
    }

    @Override
    public void getView(int position, Holder holder) {
        holder.name.setText(getItem(position).getName());
    }

    public class Holder {
        private TextView name;

        public Holder(View convertView) {
            name = (TextView) convertView.findViewById(R.id.map_name);
        }
    }

}
