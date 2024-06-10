package com.example.androidassignments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Objects;

public class ChatWindow extends AppCompatActivity {

    private ListView listView;
    private EditText chatInput;
    private Button sendButton;
    private ArrayList<String> chatMessages;
    private ChatAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_window);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.chat_list);
        chatInput = findViewById(R.id.chat_input);
        sendButton = findViewById(R.id.send_button);

        chatMessages = new ArrayList<>();
        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chatInput.getText().toString();
                if (!message.isEmpty()) {
                    chatMessages.add(message);
                    messageAdapter.notifyDataSetChanged();
                    chatInput.setText("");
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Handle the action for the back button
        return true;
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(ChatWindow context) {
            super(context, 0, chatMessages);
        }

        @Override
        public int getCount() {
            return chatMessages.size();
        }

        @Override
        public String getItem(int position) {
            return chatMessages.get(position);
        }


        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View result = null;
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            TextView messageText = result.findViewById(R.id.message_text);
            messageText.setText(getItem(position));
            return result;
        }
    }
}