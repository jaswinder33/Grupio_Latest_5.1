package com.grupio.photogallery;

import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;
import com.grupio.interfaces.ClickHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 5/12/16.
 */

public class PhotoGalleryDetail extends BaseActivity<PhotoGalleryPresenterImp> implements IPhotoGalleryContract.IViewI {

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private List<PhotoGalleryData> mPhotosList = new ArrayList<>();

    @Override
    public int getLayout() {
        return R.layout.layout_photogallery_detail;
    }

    @Override
    public void initIds() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    public boolean isHeaderForGridPage() {
        return false;
    }

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    public String getBannerName() {
        return null;
    }

    @Override
    public PhotoGalleryPresenterImp setPresenter() {
        return new PhotoGalleryPresenterImp(this);
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void setUp() {
        handleRightBtn(true, OVERFLOW);
        getPresenter().prepareList(this);
        getPresenter().fetchPhotosList(this, true);
    }

    @Override
    public void handleRightBtnClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();

        inflater.inflate(R.menu.dropdown_menu, popup.getMenu());
        Menu menu = popup.getMenu();
//        MenuItem menuItem = menu.findItem(R.id.save_btn);

        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.save_btn:
                        savePic();
                        break;
                }
                return true;
            }
        });

    }


    @Override
    public void showList(List<PhotoGalleryData> mList) {
        mPhotosList.addAll(mList);
        adapter = new ViewPagerAdapter();
        adapter.addAll(mList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(getImagePosition());
    }

    @Override
    public void showPhoto(PhotoGalleryData mData) {
//// Implement this method for Photogallery screen
    }

    @Override
    public void showModeratorDialog() {
// Implement this method for Photogallery screen
    }

    @Override
    public void showSuccessDialog() {
// Implement this method for Photogallery screen
    }

    @Override
    public void showDownloadNotification(PhotoGalleryData mData) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        getPresenter().downloadImage(this, mData, notificationManager, mBuilder);
    }

    @Override
    public void showProgress(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void onFailure(String msg) {
        showToast(msg);
    }

    public int getImagePosition() {

        int id = 0;
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            id = mBundle.getInt("position", 0);
        }
        return id;
    }

    public void savePic() {

        ClickHandler okHandler = () -> {
            PhotoGalleryData mData = mPhotosList.get(viewPager.getCurrentItem());
            showDownloadNotification(mData);
        };

        CustomDialog.getDialog(this, okHandler).show(getString(R.string.image_download_dialog));
    }

    public class ViewPagerAdapter extends PagerAdapter {

        List<PhotoGalleryData> mPhotoGalleryList = new ArrayList<>();
        private ImageView imageView;
        private TextView captionText;

        public void addAll(List<PhotoGalleryData> mlist) {
            mPhotoGalleryList.addAll(mlist);
        }

        @Override
        public int getCount() {
            return mPhotoGalleryList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = LayoutInflater.from(PhotoGalleryDetail.this).inflate(R.layout.gallerydetail, null);
            imageView = (ImageView) view.findViewById(R.id.imgdetail);
            captionText = (TextView) view.findViewById(R.id.caption);

            if (TextUtils.isEmpty(mPhotoGalleryList.get(position).getCaption())) {
                captionText.setVisibility(View.VISIBLE);
                captionText.setText(mPhotoGalleryList.get(position).getCaption());
            } else {
                captionText.setVisibility(View.GONE);
            }

            String thumbNailUrl = getString(R.string.image_base_url) + mPhotoGalleryList.get(position).getThumb_image();
            ImageLoader.getInstance().displayImage(thumbNailUrl, imageView, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    imageView.setImageBitmap(loadedImage);
                    try {
                        ImageLoader.getInstance().displayImage(getString(R.string.image_base_url) + mPhotoGalleryList.get(position).getImage_url(), imageView, Utility.getDisplayOptionsPhotoGallery());
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }


}
