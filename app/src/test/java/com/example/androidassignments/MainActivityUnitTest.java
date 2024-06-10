package com.example.androidassignments;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static android.app.Activity.RESULT_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityUnitTest {

    private ActivityScenario<MainActivity> scenario;

    @Before
    public void setUp() {
        scenario = ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void testStartListItemsActivity() {
        scenario.onActivity(activity -> {
            Button mainButton = activity.findViewById(R.id.mainButton);
            mainButton.performClick();

            Intent expectedIntent = new Intent(activity, ListItemsActivity.class);
            Intent actual = shadowOf(activity).getNextStartedActivity();
            assertEquals(expectedIntent.getComponent(), actual.getComponent());
        });
    }

    @Test
    public void testStartChatWindow() {
        scenario.onActivity(activity -> {
            Button chatButton = activity.findViewById(R.id.chatButton);
            chatButton.performClick();

            Intent expectedIntent = new Intent(activity, ChatWindow.class);
            Intent actual = shadowOf(activity).getNextStartedActivity();
            assertEquals(expectedIntent.getComponent(), actual.getComponent());
        });
    }

    @Test
    public void testOnActivityResult() {
        scenario.onActivity(activity -> {
            Intent data = new Intent();
            data.putExtra("Response", "Here is my response");
            activity.onActivityResult(10, RESULT_OK, data);

            ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
            verify(activity).print(argumentCaptor.capture());
            assertEquals(activity.getString(R.string.listItemActivity_msg) + " Here is my response", argumentCaptor.getValue());
        });
    }
}
