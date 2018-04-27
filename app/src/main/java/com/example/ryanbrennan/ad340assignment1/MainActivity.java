package com.example.ryanbrennan.ad340assignment1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private TextView helloWorldText;
    private EditText nameText;
    private EditText emailText;
    private EditText userText;
    private TextView ageText;
    private EditText birthText;
    private EditText descriptionText;
    private EditText occupationText;
    Calendar myCalendar = Calendar.getInstance();
    Date todayDate = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = findViewById(R.id.nameEditText);
        emailText = findViewById(R.id.emailAddress);
        userText = findViewById(R.id.username);
        ageText = findViewById(R.id.age);
        birthText = findViewById(R.id.birthday);
        descriptionText = findViewById(R.id.description);
        occupationText = findViewById(R.id.occupation);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        birthText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.content_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            this.helloWorldText.setText(R.string.menu_click);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    public void onLogin(View view){
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra(Constants.KEY_NAME, nameText.getText().toString());
        intent.putExtra(Constants.KEY_AGE, ageText.getText());
        intent.putExtra(Constants.KEY_EMAIL, emailText.getText().toString());
        intent.putExtra(Constants.KEY_USERNAME, userText.getText().toString());
        intent.putExtra(Constants.KEY_DESCRIPTION, descriptionText.getText().toString());
        intent.putExtra(Constants.KEY_OCCUPATION, occupationText.getText().toString());
        if(ageText.getText().toString().compareTo(getString(R.string.age_limit)) > 0){
            startActivity(intent);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.error_message);
            builder.setNegativeButton(R.string.alert_close, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create();
            builder.show();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.KEY_AGE, ageText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.containsKey(Constants.KEY_AGE)){
            ageText.setText((String)savedInstanceState.get(Constants.KEY_AGE));
        }

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthText.setText(sdf.format(myCalendar.getTime()));
        int age = todayDate.getYear() - myCalendar.getTime().getYear();
        String ageString = String.valueOf(age);
        ageText.setText(ageString);
    }


}
