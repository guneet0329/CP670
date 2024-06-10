package com.example.androidassignments;


import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import junit.framework.TestCase;

import org.junit.Test;

public class ChatWindowTest extends TestCase {

    @Test
    public void testActivityLaunch() {
        ActivityScenario<ChatWindow> scenario = ActivityScenario.launch(ChatWindow.class);
        Espresso.onView(ViewMatchers.withId(R.id.chat_list))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.chat_input))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.send_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSendMessage() {
        ActivityScenario<ChatWindow> scenario = ActivityScenario.launch(ChatWindow.class);

        String testMessage = "Hello, World!";

        Espresso.onView(ViewMatchers.withId(R.id.chat_input))
                .perform(ViewActions.typeText(testMessage));
        Espresso.onView(ViewMatchers.withId(R.id.send_button))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.chat_input))
                .check(ViewAssertions.matches(ViewMatchers.withText("")));
        Espresso.onView(ViewMatchers.withId(R.id.chat_list))
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(testMessage))));
    }

    @Test
    public void testBackButton() {
        ActivityScenario<ChatWindow> scenario = ActivityScenario.launch(ChatWindow.class);
        Espresso.onView(ViewMatchers.withContentDescription("Navigate up")).perform(ViewActions.click());
        // Add assertions to check if the activity has finished or navigated back
    }

    @Test
    public void testEmptyMessage() {
        ActivityScenario<ChatWindow> scenario = ActivityScenario.launch(ChatWindow.class);
        Espresso.onView(ViewMatchers.withId(R.id.send_button))
                .perform(ViewActions.click());
        // Ensure no message is added and the input remains empty
        Espresso.onView(ViewMatchers.withId(R.id.chat_input))
                .check(ViewAssertions.matches(ViewMatchers.withText("")));
    }

}