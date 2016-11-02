package com.grupio.base;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupio.R;

/**
 * Created by JSN on 2/11/16.
 */

public abstract class SingleElementListBaseAdapter<T> extends SimpleBaseListAdapter<T, SingleElementListBaseAdapter.Holder> {

    public SingleElementListBaseAdapter(Context context) {
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

    public abstract void getView(int position, Holder holder);

    public class Holder {
        public TextView name;
        public ImageView imageView;

        public Holder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.resource_image);
            name = (TextView) convertView.findViewById(R.id.map_name);
        }
    }
}
