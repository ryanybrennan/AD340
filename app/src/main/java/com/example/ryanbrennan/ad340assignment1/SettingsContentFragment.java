package com.example.ryanbrennan.ad340assignment1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;

public class SettingsContentFragment extends Fragment{

    public TextView email;
    public EditText reminderTime;
    public EditText matchDistance;
    public EditText gender;
    public EditText lowerAgeRange;
    public EditText upperAgeRange;
    public ToggleButton privacy;
    public Button updateDatabase;
    public boolean privacyStatus;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_settings, container, false);

        Intent intent = getActivity().getIntent();
        Bundle b = intent.getExtras();
        assert b != null;

        email = view.findViewById(R.id.email);
        email.setText(b.getString(Constants.KEY_EMAIL));
        reminderTime = view.findViewById(R.id.reminderTime);
        gender = view.findViewById(R.id.gender);
        lowerAgeRange = view.findViewById(R.id.lower_age_range);
        upperAgeRange = view.findViewById(R.id.upper_age_range);
        matchDistance = view.findViewById(R.id.match_distance_search);
        privacy = view.findViewById(R.id.privacy);
        privacy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                privacy.getTextOn();
                privacyStatus = isChecked;
                } else {
                privacy.getTextOff();
                privacyStatus = isChecked;
                }
            }
        });
        updateDatabase = view.findViewById(R.id.update);

        updateDatabase.setOnClickListener((v) -> {
            updateDatabase();
        });



        new GetSettingsTask(this, b.getString(Constants.KEY_EMAIL)).execute();

        return view;
    }

//    @Override
//    public void onPause(){
//        super.onPause();
//        Settings newSettings = new Settings();
//
//        newSettings.setEmail(email.getText().toString());
//        newSettings.setReminderTime(reminderTime.getText().toString());
//        newSettings.setMatchDistanceSearch(Integer.parseInt(matchDistance.getText().toString()));
//        newSettings.setGender(gender.getText().toString());
//        newSettings.setPrivacy(privacyStatus);
//        newSettings.setLowerAgeRange(Integer.parseInt(lowerAgeRange.getText().toString()));
//        newSettings.setUpperAgeRange(Integer.parseInt(upperAgeRange.getText().toString()));
//        new UpdateSettingsTask( this, newSettings).execute();
//    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        Intent intent = getActivity().getIntent();
//        Bundle b = intent.getExtras();
//        assert b != null;
//        new GetSettingsTask(this, b.getString(Constants.KEY_EMAIL)).execute();
//    }

    public void updateDatabase() {
        Settings newSettings = new Settings();

        newSettings.setEmail(email.getText().toString());
        newSettings.setReminderTime(reminderTime.getText().toString());
        newSettings.setMatchDistanceSearch(Integer.parseInt(matchDistance.getText().toString()));
        newSettings.setGender(gender.getText().toString());
        newSettings.setPrivacy(privacyStatus);
        newSettings.setLowerAgeRange(Integer.parseInt(lowerAgeRange.getText().toString()));
        newSettings.setUpperAgeRange(Integer.parseInt(upperAgeRange.getText().toString()));
        new UpdateSettingsTask( this, newSettings).execute();
    }

    private static class GetSettingsTask extends AsyncTask<Void, Void, Settings> {
//        private WeakReference<Activity> weakActivity;
        private WeakReference<SettingsContentFragment> weakFragment;
        private String userEmail;

        public GetSettingsTask(SettingsContentFragment scFrag, String userEmail) {
            weakFragment = new WeakReference<>(scFrag);
            this.userEmail = userEmail;
        }

        @Override
        protected Settings doInBackground(Void... voids) {
            SettingsContentFragment scFrag = weakFragment.get();
            if(scFrag == null) {
                return null;
            }

            AppDatabase db = AppDatabaseSingleton.getDatabase(scFrag.getContext());

            List<Settings> settings = db.settingsDao().findByEmail(userEmail);

            if(settings.size() <= 0 || settings.get(0) == null) {
                return null;
            }
            return settings.get(0);
        }

        @Override
        protected void onPostExecute(Settings settings) {
            SettingsContentFragment scFrag = weakFragment.get();
            if(settings == null || scFrag == null) {
                return;
            }else {
                scFrag.email.setText(settings.getEmail());
                scFrag.reminderTime.setText(settings.getReminderTime());
                scFrag.matchDistance.setText(Integer.toString(settings.getMatchDistanceSearch()));
                scFrag.gender.setText(settings.getGender());
                scFrag.privacy.setChecked(settings.getPrivacy());
                scFrag.lowerAgeRange.setText(Integer.toString(settings.getLowerAgeRange()));
                scFrag.upperAgeRange.setText(Integer.toString(settings.getUpperAgeRange()));
            }

        }
    }

    private static class UpdateSettingsTask extends AsyncTask<Void, Void, Settings> {

        private WeakReference<SettingsContentFragment> weakActivity;
        private Settings settings;

        public UpdateSettingsTask(SettingsContentFragment scFrag, Settings settings) {
            weakActivity = new WeakReference<>(scFrag);
            this.settings = settings;
        }

        @Override
        protected Settings doInBackground(Void... voids) {
            SettingsContentFragment scFrag = weakActivity.get();
            if(scFrag == null) {
                return null;
            }

            AppDatabase db = AppDatabaseSingleton.getDatabase(scFrag.getContext());

            db.settingsDao().insertAll(settings);
            return settings;
        }

        @Override
        protected void onPostExecute(Settings settings) {
            if(settings == null) {
                Toast.makeText(weakActivity.get().getContext(), "Settings not updated", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(weakActivity.get().getContext(), "Settings updated", Toast.LENGTH_SHORT).show();
            }


        }
    }

}
