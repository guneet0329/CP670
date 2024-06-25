package com.example.androidassignments;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestToolbarTest {

    @Rule
    public ActivityScenarioRule<TestToolbar> activityRule = new ActivityScenarioRule<>(TestToolbar.class);
    @Test
    public void testSnackbarAction() {
        onView(withId(R.id.fab)).perform(click());
        onView(withText("Hi, this is a Custom Snackbar Message")).check(matches(isDisplayed()));
    }

    @Test
    public void testOptionAbout() {
        // Open the overflow menu
        activityRule.getScenario().onActivity(Espresso::openActionBarOverflowOrOptionsMenu);

        // Now you can perform your click on the menu item
        onView(withId(R.id.action_about)).perform(click());

        // Check if the Snackbar with the version information is displayed
        onView(withText("Version 1.0, by Guneet Singh")).check(matches(isDisplayed()));
    }
}
