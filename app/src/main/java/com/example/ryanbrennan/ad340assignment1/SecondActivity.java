package com.example.ryanbrennan.ad340assignment1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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
    private Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewModel = new FirebaseMatchesViewModel();
        manager = getSupportFragmentManager();
        Bundle matchesBundle = new Bundle();
        viewModel.getMatches((ArrayList<Match> matches) -> {
            System.out.println("NAME IS HERE: " +  matches.get(0).name);
            System.out.println("ID IS THIS: " + matches.get(0).uid);
            matchesBundle.putParcelableArrayList(ARG_DATA_SET, matches);
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            adapter = new Adapter(this, getSupportFragmentManager(), matchesBundle);
            viewPager.setAdapter(adapter);
            TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
            tabs.setupWithViewPager(viewPager);
        });

    }

//    private void setupViewPager(ViewPager viewPager) {
//        Adapter adapter = new Adapter(this, getSupportFragmentManager(), bundle);
//        adapter.addFragment(new ProfileContentFragment(), "Profile");
//        adapter.addFragment(new MatchesContentFragment(), "Matches");
//        adapter.addFragment(new SettingsContentFragment(), "Settings");
//        viewPager.setAdapter(adapter);
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Constants.KEY_NAME, textView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey(Constants.KEY_NAME)) {
            textView.setText((String)savedInstanceState.get(Constants.KEY_NAME));
        }

    }

//    public void onBack(View view) {
//        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
//        startActivity(intent);
//        super.onBackPressed();
//    }

    public void onBackPressed(){
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(Context context, FragmentManager manager, Bundle bundle) {
            super(manager);
            addFragment(new ProfileContentFragment(), "Profile");
            addFragment(new MatchesContentFragment(), "Matches", bundle);
            addFragment(new SettingsContentFragment(), "Settings");

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
