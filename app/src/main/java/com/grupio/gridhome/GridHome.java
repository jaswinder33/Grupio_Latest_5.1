package com.grupio.gridhome;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.animation.SlideIn;
import com.grupio.data.MenuData;
import com.grupio.eventlist.EventListActivity;
import com.grupio.session.Preferences;

import java.util.List;

public class GridHome extends BaseActivity<GridHomePresenter> implements GridView {

    private static final int UPDATE_INTERVAL = 15 * 60 * 1000;
    Runnable updaterRunnable = () -> {
        //            Intent mIntent = new Intent(GridHome.this, DataFetchService.class);
//            startService(mIntent);
//            mHandler.postDelayed(updaterRunnable, UPDATE_INTERVAL);
    };
    private ImageButton switchEvent, moreInfo;
    private RecyclerView mRecyclerView;
    private MenuListAdapter mAdapter;
    private Handler mHandler;

    @Override
    public int getLayout() {
        return R.layout.activity_grid_home;
    }

    @Override
    public void setUp() {
        handleLeftBtn(false, true);
        handleRightBtn(false, null);
        setBackground(true);
        mHandler = new Handler();
        getPresenter().fetchMenuList();
    }

    @Override
    public void initIds() {
        switchEvent = (ImageButton) findViewById(R.id.switchEvent);
        moreInfo = (ImageButton) findViewById(R.id.moreInfo);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 3);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);

        background = (RelativeLayout) findViewById(R.id.gridHomeRoot);
    }

    @Override
    public boolean isHeaderForGridPage() {
        return true;
    }

    @Override
    public void setListeners() {
        switchEvent.setOnClickListener(this);
        moreInfo.setOnClickListener(this);
        mRecyclerView.addOnItemTouchListener(new GridListClickListener(this));
    }

    @Override
    public GridHomePresenter setPresenter() {
        return new GridHomePresenter(this, this);
    }

    @Override
    public String getScreenName() {
        return "";
    }

    @Override
    public String getBannerName() {
        return null;
    }

    @Override
    public void showGrid(List<MenuData> menuList) {
        Log.i(TAG, "showGrid: List size" + menuList.size());

        mAdapter = new MenuListAdapter(this);
        mAdapter.addAll(menuList);
        mRecyclerView.setAdapter(mAdapter);

        Preferences.getInstances(this).setIsAppVisited(true);
    }

    @Override
    public void notifyAdapter() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyItem(int position) {
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void showHeader(boolean flag) {

    }

    @Override
    public void showCalendarCount(String count, int position) {
        notifyItem(position);
    }

    @Override
    public void alertCount(String count, int position) {
        notifyItem(position);
    }

    @Override
    public void chatCount(String count, int position) {
        notifyItem(position);
    }

    @Override
    public void messageCount(String count, int position) {
        notifyItem(position);
    }

    @Override
    public void startUpdater() {
        mHandler.postDelayed(updaterRunnable, UPDATE_INTERVAL);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {

            case R.id.switchEvent:
                Intent mIntent = new Intent(this, EventListActivity.class);
                startActivity(mIntent);
                SlideIn.getInstance().startAnimation(this);

                break;

            case R.id.moreInfo:

                break;

        }

    }

    @Override
    public void handleRightBtnClick() {

    }


}
