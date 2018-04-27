package com.example.ryanbrennan.ad340assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity{

    private TextView textView;
    TextView nameTextView;
    TextView occupationTextView;
    TextView descriptionTextView;
    TextView emailTextView;
    ImageView profile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = findViewById(R.id.textView);
        nameTextView = findViewById(R.id.nameTextView);
        occupationTextView = findViewById(R.id.occupationTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        emailTextView = findViewById(R.id.emailTextView);

        StringBuilder msg = new StringBuilder(getString(R.string.intro_text)+" ");
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        assert b != null;
        if(b.containsKey(Constants.KEY_USERNAME)){
            String username = b.getString(Constants.KEY_USERNAME);
            msg.append(username);
        }
        textView.setText(msg);

        profile = findViewById(R.id.profile_pic);
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
    }

    public void onBackPressed(){
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

}
