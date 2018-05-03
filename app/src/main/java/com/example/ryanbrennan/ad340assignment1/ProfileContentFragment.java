package com.example.ryanbrennan.ad340assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.LogPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileContentFragment extends Fragment{

//    private TextView textView;
    TextView nameTextView;
    TextView occupationTextView;
    TextView descriptionTextView;
    TextView emailTextView;
    ImageView profile;
    private FragmentManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_profile, container, false);

//        textView = view.findViewById(R.id.textView);
        nameTextView = view.findViewById(R.id.nameTextView);
        occupationTextView = view.findViewById(R.id.occupationTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        emailTextView = view.findViewById(R.id.emailTextView);

        Intent intent = getActivity().getIntent();
        Bundle b = intent.getExtras();
        assert b != null;

//        StringBuilder msg = new StringBuilder(getString(R.string.intro_text)+" ");
//        if(b.containsKey(Constants.KEY_USERNAME)){
//            String username = b.getString(Constants.KEY_USERNAME);
//            msg.append(username);
//        }
//        textView.setText(msg);

        profile = view.findViewById(R.id.profile_pic);
        profile.setImageResource(R.drawable.dancing);
        profile.setAdjustViewBounds(true);

        StringBuilder mainInfo = new StringBuilder();
        if(b.containsKey(Constants.KEY_NAME)){
            String name = b.getString(Constants.KEY_NAME);
            mainInfo.append(name);
        }
        if(b.containsKey(Constants.KEY_AGE)){
            String age = b.getString(Constants.KEY_AGE);
            mainInfo.append(", ").append(age);
        }
        nameTextView.setText(mainInfo);

        StringBuilder occupation = new StringBuilder();
        if(b.containsKey(Constants.KEY_OCCUPATION)){
            String occ = b.getString(Constants.KEY_OCCUPATION);
            occupation.append(occ);
        }
        occupationTextView.setText(occupation);

        StringBuilder description = new StringBuilder();
        if(b.containsKey(Constants.KEY_DESCRIPTION)){
            String desc = b.getString(Constants.KEY_DESCRIPTION);
            description.append(desc);
        }
        descriptionTextView.setText(description);

        StringBuilder email = new StringBuilder();
        if(b.containsKey(Constants.KEY_EMAIL)){
            String e = b.getString(Constants.KEY_EMAIL);
            email.append(e);
        }
        emailTextView.setText(email);

        return view;
    }
}
