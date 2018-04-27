package com.example.ryanbrennan.ad340assignment1;


import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.DatePicker;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withTagKey;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    public static void setDate(int datePickerLaunchViewId, int year, int monthOfYear, int dayOfMonth) {
        onView(withId(datePickerLaunchViewId)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));
        onView(withId(android.R.id.button1)).perform(click());
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void canEnterInfoAndGoToSecondActivity() {
        onView(withId(R.id.nameEditText)).perform(typeText("Test"));
        onView(withId(R.id.emailAddress)).perform(typeText("test@test.com"));
        onView(withId(R.id.username)).perform(typeText("tester"));
        setDate(R.id.birthday, 1999, 6, 30);
        onView(withId(R.id.description)).perform(typeText("Stuff and more stuff"));
        onView(withId(R.id.occupation)).perform((typeText("Student")));

        Intents.init();
        onView(withId(R.id.loginBtn)).perform(ViewActions.scrollTo()).perform(click());
        intended(hasComponent(SecondActivity.class.getName()));
        intended(hasExtra(Constants.KEY_USERNAME, "tester"));
        intended(hasExtra(Constants.KEY_NAME, "Test"));
        intended(hasExtra(Constants.KEY_AGE, "19"));
        intended(hasExtra(Constants.KEY_EMAIL, "test@test.com"));
        intended(hasExtra(Constants.KEY_DESCRIPTION, "Stuff and more stuff"));
        intended(hasExtra(Constants.KEY_OCCUPATION, "Student"));
        Intents.release();

        onView(withId(R.id.textView))
                .check(matches(withText("Thanks for signing up tester")));
        onView(withId(R.id.profile_pic)).check(matches(withContentDescription("Profile Picture")));
        onView(withId(R.id.nameTextView)).check(matches(withText("Test, 19")));
        onView(withId(R.id.occupationTextView)).check(matches(withText("Student")));
        onView(withId(R.id.emailTextView)).check(matches(withText("test@test.com")));
        onView(withId(R.id.descriptionTextView)).check(matches(withText("Stuff and more stuff")));

        Intents.init();
        onView(withId(R.id.backBtn)).perform(click());
        Intents.release();

//        TestUtils.rotateScreen(activityTestRule.getActivity());
    }

    @Test
    public void failedDate() {
        onView(withId(R.id.nameEditText)).perform(typeText("Test"));
        onView(withId(R.id.emailAddress)).perform(typeText("test@test.com"));
        onView(withId(R.id.username)).perform(typeText("tester"));
        setDate(R.id.birthday, 2004, 6, 30);
        onView(withId(R.id.description)).perform(typeText("Stuff and more stuff"));
        onView(withId(R.id.occupation)).perform((typeText("Student")));

        Intents.init();
        onView(withId(R.id.loginBtn)).perform(ViewActions.scrollTo()).perform(click());
        onView(withText("Must be 18 or older to proceed")).check(matches(isDisplayed()));


    }



}
