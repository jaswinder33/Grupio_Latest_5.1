package com.grupio.photogallery;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.animation.SlideOut;
import com.grupio.base.BaseRecyclerView;
import com.grupio.fragments.BaseFragment;

import java.util.List;

/**
 * Created by JSN on 5/12/16.
 */

public class PhotoGalleryFragment extends BaseFragment<PhotoGalleryPresenter> implements IPhotoGalleryContract.IView, BaseRecyclerView.OnClick<PhotoGalleryData> {

    RecyclerView mRecyclerView;
    PhotoGalleryAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.layout_photogallery;
    }

    @Override
    public void initIds() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        GridLayoutManager mGridManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mGridManager);
    }

    @Override
    public void setListeners() {
    }

    @Override
    public void setUp() {
        getPresenter().fetchPhotosList(getActivity(), false);
    }

    @Override
    public PhotoGalleryPresenter setPresenter() {
        return new PhotoGalleryPresenter(this);
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

    @Override
    public void showList(List<PhotoGalleryData> mList) {
        mAdapter = new PhotoGalleryAdapter(getActivity());
        mAdapter.setOnClickListener(this);
        mAdapter.addAll(mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showPhoto(PhotoGalleryData mData) {
        // Implement this method for PhotoGallery detail screen.
        mAdapter.addAt(0, mData);
        mAdapter.notifyItemChanged(0);
    }

    @Override
    public void showModeratorDialog() {
        BaseActivity.CustomDialog.getDialog(getActivity()).singledBtnDialog(true).show(getString(R.string.moderator_msg));
    }

    @Override
    public void showSuccessDialog() {
        BaseActivity.CustomDialog.getDialog(getActivity()).singledBtnDialog(true).show(getString(R.string.image_uploaded_success_msg));
    }

    @Override
    public void showDownloadNotification(PhotoGalleryData mData) {

    }

    public void uploadImage(String imagePath, String caption) {
        getPresenter().uploadPhoto(getActivity(), imagePath, caption);
    }

    @Override
    public void onClick(PhotoGalleryData data, int position) {
        Intent mIntent = new Intent(getActivity(), PhotoGalleryDetail.class);
        mIntent.putExtra("data", data);
        mIntent.putExtra("position", position);
        startActivity(mIntent);
        SlideOut.getInstance().startAnimation(getActivity());
    }
}
