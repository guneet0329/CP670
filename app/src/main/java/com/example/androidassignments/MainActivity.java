package com.example.androidassignments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyApp";
    private static final String ACTIVITY_NAME = "LoginActivity";
    private Spinner citySpinner;
    private String selectedCity;


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
        Button toolbarButton = findViewById(R.id.toolbarButton);
        Button forecastButton = findViewById(R.id.weatherButton);
        citySpinner = findViewById(R.id.citySpinner);

        final String[] cities = {"Ottawa", "Toronto", "Vancouver", "Montreal", "Calgary", "Edmonton", "Winnipeg", "Quebec City", "Hamilton", "Kitchener"};

        toolbarButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TestToolbar.class);
            startActivity(intent);
        });

        mainButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
            startActivityForResult(intent, 10);
        });

        chatButton.setOnClickListener(v -> {
            Log.i(ACTIVITY_NAME, "User clicked Start Chat");
            Intent intent = new Intent(MainActivity.this, ChatWindow.class);
            startActivity(intent);
        });



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = cities[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCity = cities[0];
            }
        

        });

        forecastButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WeatherForecast.class);
            intent.putExtra("city", selectedCity);
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