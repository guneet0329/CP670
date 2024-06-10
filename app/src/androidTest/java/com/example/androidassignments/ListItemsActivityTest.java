package com.example.androidassignments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import android.widget.CheckBox;

import junit.framework.TestCase;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import org.junit.Test;


public class ListItemsActivityTest extends TestCase {

    @Test
    public void testActivityLaunch() {
        // Launch the ListItemsActivity
        ActivityScenario<ListItemsActivity> scenario = ActivityScenario.launch(ListItemsActivity.class);

        // Check if all UI components are displayed
        Espresso.onView(ViewMatchers.withId(R.id.radio_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.check_box))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.switch_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.image_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testRadioButtonSelection() {
        // Launch the ListItemsActivity
        ActivityScenario<ListItemsActivity> scenario = ActivityScenario.launch(ListItemsActivity.class);

        // Perform a click action on the radio button and check if it's selected
        Espresso.onView(ViewMatchers.withId(R.id.radio_button))
                .perform(ViewActions.click())
                .check(ViewAssertions.matches(ViewMatchers.isChecked()));
    }

    @Test
    public void testCheckBoxSelection() {
        // Launch the ListItemsActivity
        ActivityScenario<ListItemsActivity> scenario = ActivityScenario.launch(ListItemsActivity.class);

        // Log the initial state of the checkbox
        Espresso.onView(ViewMatchers.withId(R.id.check_box))
                .check((view, noViewFoundException) -> Log.d(TAG, "Initial CheckBox state: " + ((CheckBox)view).isChecked()));

        // Perform a click action on the check box and check if it's selected
        Espresso.onView(ViewMatchers.withId(R.id.check_box))
                .perform(ViewActions.closeSoftKeyboard(), ViewActions.click());

        // Check if the dialog is displayed
        Espresso.onView(ViewMatchers.withText("Confirmation"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Dismiss the dialog
        Espresso.onView(ViewMatchers.withText("NO"))
                .perform(ViewActions.click());

        // Verify the checkbox state after dismissing the dialog
        Espresso.onView(ViewMatchers.withId(R.id.check_box))
                .check(ViewAssertions.matches(ViewMatchers.isChecked()));

        // Log the final state of the checkbox
        Espresso.onView(ViewMatchers.withId(R.id.check_box))
                .check((view, noViewFoundException) -> Log.d(TAG, "Final CheckBox state: " + ((CheckBox)view).isChecked()));
    }

    @Test
    public void testSwitchToggle() {
        // Launch the ListItemsActivity
        ActivityScenario<ListItemsActivity> scenario = ActivityScenario.launch(ListItemsActivity.class);

        // Perform a click action on the switch and check if it's toggled
        Espresso.onView(ViewMatchers.withId(R.id.switch_button))
                .perform(ViewActions.click())
                .check(ViewAssertions.matches(ViewMatchers.isChecked()));
    }

    @Test
    public void testImageButtonClick() {
        // Launch the ListItemsActivity
        ActivityScenario<ListItemsActivity> scenario = ActivityScenario.launch(ListItemsActivity.class);

        // Perform a click action on the image button
        Espresso.onView(ViewMatchers.withId(R.id.image_button))
                .perform(ViewActions.click());

        // Add an assertion to check the expected behavior after the button click
        // For example, you could check if a specific action was triggered or if a certain view is displayed
    }
}