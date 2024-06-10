package com.example.androidassignments;

import junit.framework.TestCase;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Test;
import org.junit.runner.RunWith;
public class LoginActivityTest extends TestCase {
    @Test
    public void testActivityLaunch() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.login_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testLogin() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.login_email))
                .perform(ViewActions.typeText("testuser"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("password"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_button))
                .perform(ViewActions.click());

        // Verify that MainActivity is started after login
        Espresso.onView(ViewMatchers.withId(R.id.mainButton))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}