package com.grupio.gridhome;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.dao.EventDAO;
import com.grupio.data.MenuData;
import com.grupio.session.Preferences;

import java.util.List;

public class GridHome extends BaseActivity implements GridView {

    private ImageButton switchEvent, moreInfo;
    private RecyclerView mRecyclerView;
    private GridHomePresenter mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_home);
        init();

        handleLeftBtn(false,true);
        handleRightBtn(false, null);

        setListeners();
    }

    public void init(){
        switchEvent = (ImageButton) findViewById(R.id.switchEvent);
        moreInfo = (ImageButton) findViewById(R.id.moreInfo);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 3 );

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);

        header = (TextView) findViewById(R.id.header);
        leftBtn = (ImageView) findViewById(R.id.leftBtn);
        rightBtn = (TextView) findViewById(R.id.rightBtn);
    }

    public void setListeners(){
        switchEvent.setOnClickListener(this);
        moreInfo.setOnClickListener(this);
        mListener = new GridHomePresenter(this, this);
    }


    @Override
    public void showGrid(List<MenuData> menuList) {
        Log.i(TAG, "showGrid: List size" + menuList.size());

        MenuListAdapter mAdapter = new MenuListAdapter(this);
        mAdapter.addAll(menuList);
        mRecyclerView.setAdapter(mAdapter);

        Preferences.getInstances(this).setIsAppVisited(true);
    }

    @Override
    public void notifyAdapter() {
    }

    @Override
    public void showHeader(boolean flag) {

    }

    @Override
    public void showCalendarCount(String count) {

    }

    @Override
    public void alertCount(String count) {

    }

    @Override
    public void chatCount(String count) {

    }

    @Override
    public void messageCount(String count) {

    }

    @Override
    public void startUpdater() {

    }


}
