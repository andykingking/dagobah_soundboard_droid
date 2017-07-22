package com.andrewsking.dagobahsoundboard;

import android.support.test.espresso.DataInteraction;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import com.andrewsking.dagobahsoundboard.ui_controllers.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;


@RunWith(AndroidJUnit4.class)
public class ClickSoundTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickSound() throws Exception {
        TextViewColorAssertion beforeColorAssertion = new TextViewColorAssertion(R.color.colorPrimaryDark);
        TextViewColorAssertion afterColorAssertion = new TextViewColorAssertion(R.color.colorAccent);

        DataInteraction soundListItem = onData(hasToString(containsString("Ambience")))
                .inAdapterView(withId(R.id.soundListView))
                .onChildView(withId(R.id.label));

        soundListItem.check(beforeColorAssertion);
        soundListItem.perform(click());
        soundListItem.check(afterColorAssertion);
    }
}
