package com.grupio.search;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.fragments.BaseFragment;
import com.grupio.interfaces.Person;

import java.util.List;

/**
 * Created by mani on 7/12/16.
 */

public class SearchFragment extends BaseFragment<SearchPresenter> implements SearchContract.IView {
    AdapterView.OnItemClickListener mListner = (adapterView, view1, i, l) -> {
    };
    TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().length() > 0) {
                getPresenter().fetchData(getActivity(), charSequence.toString());
            } else {
                getPresenter().fetchData(getActivity(), null);
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
    private ListView mListview;
    private TextView noDataAvailable;

    @Override
    public int getLayout() {
        return R.layout.activity_logistics;
    }

    @Override
    public void initIds() {
        mListview = (ListView) view.findViewById(R.id.logisticsList);
        noDataAvailable = (TextView) view.findViewById(R.id.noDataAvailable);
        mListview.setEmptyView(noDataAvailable);
    }

    @Override
    public void setListeners() {
        mListview.setOnItemClickListener(mListner);
    }

    @Override
    public void setUp() {
        setupSearchBar(true, "Search Attendee,Sessions, Speaker, Sponsors, Exhibitors");
        addFilter(mWatcher);
    }

    @Override
    public SearchPresenter setPresenter() {
        return new SearchPresenter(this);
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
    public void showList(List<Person> mPerson) {
        SearchAdapter mAdapter = new SearchAdapter(getActivity());
    }
}
