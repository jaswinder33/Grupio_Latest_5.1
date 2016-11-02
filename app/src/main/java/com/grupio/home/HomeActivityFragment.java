package com.grupio.home;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.fragments.BaseFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeActivityFragment extends BaseFragment<HomePresenter> implements HomeView, View.OnClickListener {

    private static HomeActivityFragment mHomeActivityFragment;

    private ImageView webLogo, staticMap;
    private TextView eventDate, eventName;
    private TextView venueTxt, addressTxt;
    private TextView descriptionTxt;
    private LinearLayout descriptionlayout;
    private Button getDirectionsBtn, eventWebsiteBtn;

    public static HomeActivityFragment getInstance(Bundle mBundle) {
        mHomeActivityFragment = new HomeActivityFragment();
        if (mBundle != null) {
            mHomeActivityFragment.setArguments(mBundle);
        }
        return mHomeActivityFragment;
    }

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    public String getBannerName() {
        return "";
    }

    @Override
    public HomePresenter setPresenter() {
        return new HomePresenter(getActivity(), this);
    }


    @Override
    public void initIds() {
        webLogo = (ImageView) view.findViewById(R.id.webLogo);
        staticMap = (ImageView) view.findViewById(R.id.staticMap);
        eventDate = (TextView) view.findViewById(R.id.eventDate);
        eventName = (TextView) view.findViewById(R.id.eventName);
        venueTxt = (TextView) view.findViewById(R.id.venue);
        addressTxt = (TextView) view.findViewById(R.id.address);
        descriptionTxt = (TextView) view.findViewById(R.id.description);
        descriptionlayout = (LinearLayout) view.findViewById(R.id.descriptionLay);
        getDirectionsBtn = (Button) view.findViewById(R.id.getDirection);
        eventWebsiteBtn = (Button) view.findViewById(R.id.eventWebsite);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void setUp() {

    }

    @Override
    public void setListeners() {
        getDirectionsBtn.setOnClickListener(this);
        eventWebsiteBtn.setOnClickListener(this);
        staticMap.setOnClickListener(this);
    }

    @Override
    public void setWebLogo(String url) {
        ImageLoader.getInstance().displayImage(url, webLogo);
    }

    @Override
    public void setEventDate(String date) {
        eventDate.setText(date);
    }

    @Override
    public void setEventName(String name) {
        eventName.setText(name);
    }

    @Override
    public void setVenue(String venue) {
        venueTxt.setText(venue);
    }

    @Override
    public void setAddress(String address) {
        addressTxt.setText(address);
    }

    @Override
    public void setStaticMap(String map) {
        ImageLoader.getInstance().displayImage(map, staticMap);
    }

    @Override
    public void setDescrption(String descrption) {
        descriptionTxt.setText(Html.fromHtml(descrption));
    }

    @Override
    public void hideDescription() {
        descriptionlayout.setVisibility(View.GONE);
    }

    @Override
    public void hideEventWebsite() {
        eventWebsiteBtn.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.staticMap:
                getPresenter().openMap(getActivity());
                break;

            case R.id.getDirection:
                getPresenter().getDirections(getActivity());
                break;

            case R.id.eventWebsite:
                getPresenter().openEventWebsite(getActivity());
                break;

        }
    }
}
