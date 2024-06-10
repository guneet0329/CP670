package com.example.androidassignments;

import junit.framework.TestCase;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import org.junit.Test;

public class MainActivityTest extends TestCase {
    @Test
    public void testActivityLaunch() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.mainButton))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.chatButton))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testStartChatButton() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.chatButton))
                .perform(ViewActions.click());

        // Verify that the ChatWindow activity is started
        Espresso.onView(ViewMatchers.withId(R.id.chat_list))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}