package com.grupio.notes;

import android.content.Context;

import com.grupio.R;
import com.grupio.base.SingleElementListBaseAdapter;
import com.grupio.data.DownloadedResource;

/**
 * Created by JSN on 29/11/16.
 */

public class DownloadListAdapter extends SingleElementListBaseAdapter<DownloadedResource> {

    boolean isForViewAll = false;

    public DownloadListAdapter(Context context) {
        super(context);
    }

    public DownloadListAdapter(Context context, boolean isForViewAll) {
        this(context);
        this.isForViewAll = isForViewAll;
    }

    @Override
    public void getView(int position, SingleElementListBaseAdapter.Holder holder) {

        DownloadedResource resource = getItem(position);

        if (resource.type.equalsIgnoreCase("pdf")) {
            mHolder.imageView.setImageResource(R.drawable.pdf_icon);
        } else if (resource.type.equalsIgnoreCase("ppt")) {
            mHolder.imageView.setImageResource(R.drawable.ppt_small_icon);
        } else if (resource.type.equalsIgnoreCase("doc") || resource.type.equalsIgnoreCase("docx")) {
            mHolder.imageView.setImageResource(R.drawable.doc);
        } else if (resource.type.equalsIgnoreCase("xls") || resource.type.equalsIgnoreCase("xlsx")) {
            mHolder.imageView.setImageResource(R.drawable.xl);
        } else {
            mHolder.imageView.setImageResource(R.drawable.web_icon);
        }

        String text;
        if (isForViewAll) {
            text = resource.section + ": " + resource.typeName + "\nDocumentName: " + resource.name;
        } else {
            text = resource.section + ": " + resource.name;
        }
        holder.name.setText(text);
    }
}
