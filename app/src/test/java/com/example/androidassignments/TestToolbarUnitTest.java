package com.example.androidassignments;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestToolbarUnitTest {

    @Mock
    private LayoutInflater mockLayoutInflater;
    private TestToolbar testToolbar;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Create a mock context and set up the necessary components
        Context context = mock(Context.class);
        testToolbar = new TestToolbar();

        // Mocking LayoutInflater as it's commonly required in view operations
        when(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).thenReturn(mockLayoutInflater);
        when(mockLayoutInflater.inflate(anyInt(), any(ViewGroup.class))).thenReturn(mock(View.class));
    }

    @Test
    public void testCustomMessageInitialValue() {
        // Assuming you have a method or a way to fetch the message
        assertEquals("Hi, this is a Custom Snackbar Message", testToolbar.showCustomDialog());
    }
}
