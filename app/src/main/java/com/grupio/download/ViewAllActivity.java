package com.grupio.download;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.data.DownloadedResource;
import com.grupio.interfaces.ClickHandler;
import com.grupio.logistics.DocumentController;
import com.grupio.notes.DownloadListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 29/11/16.
 */

public class ViewAllActivity extends BaseActivity<DownloadPresenter> implements DownloadContract.View {
    AdapterView.OnItemClickListener mItemClickListener = (adapterView, view1, i, l) -> {
        DownloadedResource mResource = (DownloadedResource) adapterView.getItemAtPosition(i);
        DocumentController<DownloadedResource, DownloadedResource> mDownloader = new DocumentController<>(new DownloadedResource(), new DownloadedResource(), this);
        mDownloader.viewDoc(mResource);
    };
    private ListView mListview;
    private TextView noDataAvailable;
    private List<DownloadedResource> mResourceList;
    private AlertDialog dialog;
    private DialogHolder mHolder;

    @Override
    public int getLayout() {
        return R.layout.activity_view_all;
    }

    @Override
    public void initIds() {
        mListview = (ListView) findViewById(R.id.listView);
        noDataAvailable = (TextView) findViewById(R.id.noDataAvailable);
        mListview.setEmptyView(noDataAvailable);
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
    public DownloadPresenter setPresenter() {
        return new DownloadPresenter(this);
    }

    @Override
    public void setListeners() {
        mListview.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public void setUp() {
        getPresenter().fetchAllResourceFromServer(this);
    }

    @Override
    public void handleRightBtnClick(View view) {
        ClickHandler okclick = () ->
                getPresenter().downloadAllResources(this, mResourceList);
        CustomDialog.getDialog(this, okclick).show(getString(R.string.download_all_msg));
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
    public void showCustomProgress() {
        Object[] data = CustomDialog.getDialog(this).showWithCustomView(R.layout.layout_download_all_dialog, DialogHolder.class);
        mHolder = (DialogHolder) data[0];
        dialog = (AlertDialog) data[1];
    }

    @Override
    public void hideCustomProgress() {

    }

    @Override
    public void showList(List<DownloadedResource> mData) {

        mResourceList = new ArrayList<>();
        mResourceList.addAll(mData);

        DownloadListAdapter mAdapter = new DownloadListAdapter(this, true);
        mAdapter.addAll(mData);
        mListview.setAdapter(mAdapter);

        if (!mResourceList.isEmpty()) {
            handleRightBtn(true, DOWNLOAD_ALL);
        }

    }

    @Override
    public void onFailure(String msg) {
        showToast(msg);
    }

    @Override
    public void showDownlaodProgress(int[] progress, String[] name) {

        runOnUiThread(() -> {
            mHolder.sectionName.setText(name[0]);
            mHolder.fileName.setText(name[1]);


            mHolder.sectionProgress.setMax(progress[0]);
            mHolder.sectionProgress.setProgress(progress[1]);
            mHolder.sectionCount.setText(progress[1] + "/" + progress[0]);

            try {
                mHolder.sectionPercentage.setText((progress[1] * 100 / progress[0]) + "%");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                mHolder.filePercentage.setText((progress[3] * 100 / progress[2]) + "%");
            } catch (Exception e) {
                e.printStackTrace();
            }

            mHolder.fileProgress.setMax(progress[2]);
            mHolder.fileProgress.setProgress(progress[3]);
        });
    }

    @Override
    public void allDownloadComplete() {
        runOnUiThread(() -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            showToast("Download successful!");
        });
    }

    public class DialogHolder {
        TextView sectionName;
        TextView fileName;
        TextView sectionPercentage, sectionCount;
        TextView filePercentage;
        ProgressBar sectionProgress, fileProgress;

        public DialogHolder(View view) {

            sectionName = (TextView) view.findViewById(R.id.sectionName);
            sectionProgress = (ProgressBar) view.findViewById(R.id.sectionProgress);
            sectionPercentage = (TextView) view.findViewById(R.id.sectionPercentage);
            sectionCount = (TextView) view.findViewById(R.id.sectionCount);
            fileName = (TextView) view.findViewById(R.id.fileName);
            fileProgress = (ProgressBar) view.findViewById(R.id.fileProgress);
            filePercentage = (TextView) view.findViewById(R.id.filePercentage);
        }
    }
}
