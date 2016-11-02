package com.grupio.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.dao.EventDAO;
import com.grupio.db.EventTable;
import com.grupio.interfaces.BaseFunctionality;


/**
 * Created by JSN on 6/7/16.
 */
public abstract class BaseFragment<Presenter> extends Fragment implements BaseFunctionality<Presenter> {

    protected View view;
    protected EditText searchEditTxt;
    private Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        view = inflater.inflate(getLayout(), container, false);
        init();
        sendReport(getScreenName());
        registerListeners();
        setUp();
        return view;
    }

    @Override
    public void showProgressDialog(String msg) {
        ((BaseActivity) getActivity()).showProgressDialog(msg);
    }

    @Override
    public void hideProgressDialog() {
        ((BaseActivity) getActivity()).hideProgressDialog();
    }

    @Override
    public void showMessageDialog() {
        ((BaseActivity) getActivity()).showMessageDialog();
    }

    @Override
    public void startBanner(String BannerName) {
        ((BaseActivity) getActivity()).startBanner(BannerName);
    }

    @Override
    public void sendReport(String screenName) {
        ((BaseActivity) getActivity()).sendReport(screenName);
    }

    public abstract String getScreenName();

    @Override
    public abstract String getBannerName();

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    public abstract Presenter setPresenter();

    @Override
    public void setupSearchBar(boolean showSearchBar, String locale) {
        View searchBarBgId = view.findViewById(R.id.searchBarBgId);
        searchEditTxt = (EditText) view.findViewById(R.id.exhibitor_filter_edtTxt);

        if (showSearchBar) {
            searchBarBgId.setVisibility(View.VISIBLE);
            String color = EventDAO.getInstance(getActivity()).getValue(EventTable.COLOR_THEME);
            if (color != null && !color.isEmpty())
                searchBarBgId.setBackgroundColor(Color.parseColor(color));
        } else {
            searchBarBgId.setVisibility(View.GONE);
        }
    }

    @Override
    public void addFilter(TextWatcher watcher) {
        searchEditTxt.addTextChangedListener(watcher);
    }

    @Override
    public void init() {
        initIds();
    }

    public abstract void initIds();

    @Override
    public void registerListeners() {
        presenter = setPresenter();
        setListeners();
    }

    @Override
    public void unRegisterListeners() {
    }

    @Override
    public abstract int getLayout();

    public abstract void setUp();
}
