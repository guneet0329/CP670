package com.example.androidassignments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "ChatWindow";

    ListView listView;
    public EditText chatInput;
    public Button sendButton;
    public ArrayList<String> chatMessages;
    public ChatAdapter messageAdapter;
    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_window);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.chat_list);
        chatInput = findViewById(R.id.chat_input);
        sendButton = findViewById(R.id.send_button);

        chatMessages = new ArrayList<>();
        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query(ChatDatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        Log.i(ACTIVITY_NAME, "Cursor’s column count = " + cursor.getColumnCount());
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, "Column name: " + cursor.getColumnName(i));
        }
        int messageIndex = cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
        if(messageIndex >=0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
                Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + message);
                chatMessages.add(message);
            }
        }else
            Log.i(ACTIVITY_NAME, "Column " + ChatDatabaseHelper.KEY_MESSAGE + " does not exist in the cursor");
        cursor.close();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chatInput.getText().toString();
                if (!message.isEmpty()) {
                    addMessage(message);
                    //chatMessages.add(message);
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

    public ArrayList<String> getChatMessages() {
        return chatMessages;
    }


    private void addMessage(String message) {
        chatMessages.add(message);
        ContentValues values = new ContentValues();
        values.put(ChatDatabaseHelper.KEY_MESSAGE, message);
        db.insert(ChatDatabaseHelper.TABLE_NAME, null, values);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    protected class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(ChatWindow context) {
            super(context,0, chatMessages);
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