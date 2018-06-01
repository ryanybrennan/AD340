package com.example.ryanbrennan.ad340assignment1;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.intent.Intents;
import android.widget.DatePicker;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
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

    public static RecyclerViewHelper withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewHelper(recyclerViewId);
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void canEnterInfoAndGoToSecondActivity() throws InterruptedException{
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

        Thread.sleep(10000);

        onView(withText("Cancel")).perform(click());

        onView(withText("Profile")).perform(click());

        onView(withId(R.id.profile_pic)).check(matches(withContentDescription("Profile Picture")));
        onView(withId(R.id.nameTextView)).check(matches(withText("Test, 19")));
        onView(withId(R.id.occupationTextView)).check(matches(withText("Student")));
        onView(withId(R.id.emailTextView)).check(matches(withText("test@test.com")));
        onView(withId(R.id.descriptionTextView)).check(matches(withText("Stuff and more stuff")));

        onView(withText("Settings")).perform(click());

        onView(withId(R.id.email)).perform(typeText("test@test.com"));
        onView(withId(R.id.reminderTime)).perform(typeText("12:00"));
        onView(withId(R.id.match_distance_search)).perform(typeText("5"));
        onView(withId(R.id.gender)).perform(typeText("female"));
        onView(withId(R.id.privacy)).perform(click());
        onView(withId(R.id.lower_age_range)).perform(typeText("18"));
        onView(withId(R.id.upper_age_range)).perform(typeText("29"));

        onView(withId(R.id.update)).perform(click());

        onView(withText("Matches")).perform(click());

        onView(withRecyclerView(R.id.my_recycler_view).atPositionOnView(0, R.id.card_title))
                .check(matches(withText("Cool Guy Mike")));
        onView(withRecyclerView(R.id.my_recycler_view).atPositionOnView(0, R.id.card_text))
                .check(matches(withText("Cool Guy Mike")));
        onView(withRecyclerView(R.id.my_recycler_view)
                .atPositionOnView(0, R.id.favorite_button))
                .perform(click());


        Intents.init();
        pressBack();
        Intents.release();

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

        Thread.sleep(10000);

        onView(withText("Settings")).perform(click());
        onView(withId(R.id.email)).check(matches(withText("test@test.com")));
        onView(withId(R.id.reminderTime)).check(matches(withText("12:00")));
        onView(withId(R.id.match_distance_search)).check(matches(withText("5")));
        onView(withId(R.id.gender)).check(matches(withText("female")));
        onView(withId(R.id.privacy)).check(matches(withText("Privacy")));
        onView(withId(R.id.lower_age_range)).check(matches(withText("18")));
        onView(withId(R.id.upper_age_range)).check(matches(withText("29")));

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
