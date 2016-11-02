package com.grupio.home;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.dao.EventDAO;
import com.grupio.db.EventTable;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    public boolean isHeaderForGridPage() {
        return false;
    }

    @Override
    public void initIds() {
    }

    @Override
    public void setListeners() {
    }

    @Override
    public Object setPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_map;
    }

    @Override
    public void setUp() {
        handleRightBtn(false, "refresh");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void handleRightBtnClick() {

    }

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    public String getBannerName() {
        return null;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Double latitude = Double.valueOf(EventDAO.getInstance(this).getValue(EventTable.LATTITUDE));
        Double longitude = Double.valueOf(EventDAO.getInstance(this).getValue(EventTable.LONGITUDE));

        // Add a marker in Sydney and move the camera
        LatLng eventLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(eventLocation));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 18));
    }
}
