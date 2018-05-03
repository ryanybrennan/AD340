package com.example.ryanbrennan.ad340assignment1;

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

public class SecondActivity extends AppCompatActivity{

    private TextView textView;
    TextView nameTextView;
    TextView occupationTextView;
    TextView descriptionTextView;
    TextView emailTextView;
    ImageView profile;
    private FragmentManager manager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        manager = getSupportFragmentManager();
//        Intent intent = getIntent();
//        Bundle b = intent.getExtras();
//        Fragment fProf = new ProfileContentFragment();
//        fProf.setArguments(b);
//
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.add(R.id.main_content,fProf,"fragProf");
//        transaction.commit();

    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ProfileContentFragment(), "Profile");
        adapter.addFragment(new MatchesContentFragment(), "Tile");
        adapter.addFragment(new SettingsContentFragment(), "Card");
        viewPager.setAdapter(adapter);
    }

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

    public void onBack(View view) {
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    public void onBackPressed(){
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    static class Adapter extends FragmentPagerAdapter {
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

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
