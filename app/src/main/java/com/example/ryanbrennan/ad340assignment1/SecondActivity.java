package com.example.ryanbrennan.ad340assignment1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.provider.Settings;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.ryanbrennan.ad340assignment1.MatchesContentFragment.ARG_DATA_SET;

public class SecondActivity extends AppCompatActivity implements MatchesContentFragment.OnListFragmentInteractionListener{

    private FirebaseMatchesViewModel viewModel;
    private TextView textView;
    TextView nameTextView;
    TextView occupationTextView;
    TextView descriptionTextView;
    TextView emailTextView;
    ImageView profile;
    private FragmentManager manager;
    private MatchesContentFragment matchesFragment;
    private SettingsContentFragment settingsFragment;
    private Adapter adapter;

    LocationManager locationManager;
    private Location userLocation;
    double longitudeNetwork, latitudeNetwork;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        matchesFragment = new MatchesContentFragment();
        settingsFragment = new SettingsContentFragment();
        manager = getSupportFragmentManager();
        adapter = new Adapter(getSupportFragmentManager());
        viewModel = new FirebaseMatchesViewModel();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(checkLocation()){
            networkUpdates();
        }
    }

    private boolean checkLocation() {
        if(!isLocationEnabled()) {
            showAlert();
        }
        return isLocationEnabled();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.enable_location)
                .setMessage(getString(R.string.location_message))
                .setPositiveButton(R.string.location_settings, (paramDialogInterface, paramInt) -> {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                })
                .setNegativeButton(R.string.location_cancel, (paramDialogInterface, paramInt) -> {});
        dialog.show();
    }

    public void networkUpdates() {
        if(!checkLocation()) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60 * 1000, 1000, locationListenerNetwork);
            userLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            viewModel.getMatches((ArrayList<Match> matches) -> {
                matchesFragment.updateMatches(matches, userLocation);
            });
        }
    }

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeNetwork = location.getLongitude();
            latitudeNetwork = location.getLatitude();
            runOnUiThread(()-> {
                viewModel.getMatches((ArrayList<Match> matches) -> {
                    matchesFragment.updateMatches(matches, location);
                });
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {}

        @Override
        public void onProviderDisabled(String s) {}
    };



    private void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new ProfileContentFragment(), "Profile");
        adapter.addFragment(matchesFragment, "Matches");
        adapter.addFragment(new SettingsContentFragment(), "Settings");
        viewPager.setAdapter(adapter);
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putString(Constants.KEY_NAME, textView.getText().toString());
//    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey(Constants.KEY_NAME)) {
            textView.setText((String)savedInstanceState.get(Constants.KEY_NAME));
        }

    }

    public void onBackPressed(){
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        public void addFragment(Fragment fragment, String title, Bundle bundle){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            fragment.setArguments(bundle);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
    @Override
    public void onListFragmentInteraction(Match item) {
        viewModel.updateMatch(item);
    }

    @Override
    protected void onPause() {
        viewModel.clear();
        super.onPause();
    }


}
