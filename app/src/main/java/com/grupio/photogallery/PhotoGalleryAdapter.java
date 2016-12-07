package com.grupio.photogallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.base.BaseRecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by JSN on 5/12/16.
 */

public class PhotoGalleryAdapter extends BaseRecyclerView<PhotoGalleryData, PhotoGalleryAdapter.Holder> {

    public PhotoGalleryAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getLayout() {
        return R.layout.layout_photogallery_child;
    }

    @Override
    public Holder getViewHolder(View view) {
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        PhotoGalleryData mData = getItem(position);
        ImageLoader.getInstance().displayImage(getContext().getString(R.string.image_base_url) + mData.getThumb_image(), holder.mImage, Utility.getDisplayOptionsPhotoGallery());
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImage;

        public Holder(View view) {
            super(view);
            mImage = (ImageView) view.findViewById(R.id.galleryPhoto);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            getListener().onClick(getItem(getAdapterPosition()), getAdapterPosition());
        }
    }
}
