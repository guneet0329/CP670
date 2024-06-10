package com.example.androidassignments;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class ChatWindowUnitTest {

    private ChatWindow chatWindow;
    private EditText messageInput;
    private Button sendButton;
    private ListView chatListView;

    @Before
    public void setUp() {
        chatWindow = Mockito.mock(ChatWindow.class);
        messageInput = mock(EditText.class);
        sendButton = mock(Button.class);
        chatListView = mock(ListView.class);

        chatWindow.chatInput = messageInput;
        chatWindow.sendButton = sendButton;
        chatWindow.listView = chatListView;
        chatWindow.chatMessages = new ArrayList<>();
        chatWindow.messageAdapter = mock(ChatWindow.ChatAdapter.class);

        when(chatWindow.chatInput.getText().toString()).thenReturn("Hello, World!");
    }

    @Test
    public void testSendMessage() {
        chatWindow.sendButton.performClick();

        assertEquals(1, chatWindow.chatMessages.size());
        assertEquals("Hello, World!", chatWindow.chatMessages.get(0));
    }
}