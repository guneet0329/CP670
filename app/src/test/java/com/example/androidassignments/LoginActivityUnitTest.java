package com.example.androidassignments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class LoginActivityUnitTest {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Before
    public void setUp() {
        sharedPreferences = mock(SharedPreferences.class);
        editor = mock(SharedPreferences.Editor.class);
    }

    @Test
    public void testLogin() {
        try (ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class)) {
            scenario.onActivity(activity -> {
                // Mocking SharedPreferences
                when(activity.getSharedPreferences("MyPrefs", MODE_PRIVATE)).thenReturn(sharedPreferences);
                when(sharedPreferences.edit()).thenReturn(editor);
                when(sharedPreferences.getString("DefaultEmail", "email@domain.com")).thenReturn("test@example.com");

                EditText loginEmail = activity.findViewById(R.id.login_email);
                Button loginButton = activity.findViewById(R.id.login_button);

                // Verify if the email is set correctly
                assertEquals("test@example.com", loginEmail.getText().toString());

                // Simulate user input
                loginEmail.setText("new@example.com");

                // Perform click on login button
                loginButton.performClick();

                // Verify SharedPreferences is updated
                Mockito.verify(editor).putString("DefaultEmail", "new@example.com");
                Mockito.verify(editor).apply();

                // Verify the intent to start MainActivity
                Intent expectedIntent = new Intent(activity, MainActivity.class);
                Intent actual = shadowOf(activity).getNextStartedActivity();
                assertEquals(expectedIntent.getComponent(), actual.getComponent());
            });
        }
    }
}
