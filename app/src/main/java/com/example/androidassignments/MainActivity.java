package com.example.androidassignments;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyApp";
    private static final String ACTIVITY_NAME = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button mainButton = findViewById(R.id.mainButton);
        Button chatButton = findViewById(R.id.chatButton);
        mainButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
            startActivityForResult(intent, 10);
        });

        chatButton.setOnClickListener(v -> {
            Log.i(ACTIVITY_NAME, "User clicked Start Chat");
            Intent intent = new Intent(MainActivity.this, ChatWindow.class);
            startActivity(intent);
        });
        

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            Log.i(TAG, "Returned to MainActivity.onActivityResult");
            if (resultCode == Activity.RESULT_OK) {
                String messagePassed = data.getStringExtra("Response");
                Toast.makeText(this, getString(R.string.listItemActivity_msg) +" "+ messagePassed, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void print(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "inside onResume") ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "inside onStart") ;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "inside onPause") ;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "inside onStop") ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "inside onDestroy") ;
    }
}