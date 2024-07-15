package com.example.androidassignments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    private ImageView weatherImageView;
    private TextView currentTemperatureTextView;
    private TextView minTemperatureTextView;
    private TextView maxTemperatureTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather_forecast);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progressBar);
        weatherImageView = findViewById(R.id.weatherImageView);
        currentTemperatureTextView = findViewById(R.id.currentTemperatureTextView);
        minTemperatureTextView = findViewById(R.id.minTemperatureTextView);
        maxTemperatureTextView = findViewById(R.id.maxTemperatureTextView);

        String selectedCity = getIntent().getStringExtra("city");
        progressBar.setVisibility(View.VISIBLE);
        new ForecastQuery().execute(selectedCity);
    }


    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String minTemp;
        private String maxTemp;
        private String currentTemp;
        private Bitmap weatherIcon;

        @Override
        protected String doInBackground(String... args) {
            String city = args[0];
            String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q="+city+",ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&mode=xml&units=metric";
            Log.i("WeatherForecast", "Weather URL: " + weatherUrl);
            try {
                URL url = new URL(weatherUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(inputStream, null);

                int eventType = parser.getEventType();
                Log.i("WeatherForecast", "Initial eventType: " + eventType);
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        Log.i("WeatherForecast", "Tag: " + parser.getName());
                        if (parser.getName().equals("temperature")) {
                            currentTemp = parser.getAttributeValue(null, "value");
                            minTemp = parser.getAttributeValue(null, "min");
                            maxTemp = parser.getAttributeValue(null, "max");
                            Log.i("WeatherForecast", "Current Temp: " + currentTemp);
                            Log.i("WeatherForecast", "Min Temp: " + minTemp);
                            Log.i("WeatherForecast", "Max Temp: " + maxTemp);
                            publishProgress(10,20,30,40,50,60,70,75);
                        }
                        if (parser.getName().equals("weather")) {
                            String icon = parser.getAttributeValue(null, "icon");
                            String iconName = icon + ".png";
                            Log.i("WeatherForecast", "icon name : " +iconName);
                            if (fileExists(iconName)) {
                                weatherIcon = loadBitmapFromLocalStorage(iconName);

                                Log.i("WeatherForecast", "Image found locally: " + iconName);
                            } else {
                                weatherIcon = getBitmapFromURL("https://openweathermap.org/img/w/" + icon + ".png");
                                if(weatherIcon!= null){
                                    saveBitmapToLocalStorage(iconName, weatherIcon);
                                    Log.i("WeatherForecast", "Downloaded and saved image: " + iconName);
                                } else {
                                    Log.e("WeatherForecast", "Failed to download image: " + iconName);
                                }

                            }
                            publishProgress(100);
                        }
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                Log.e("WeatherForecast", "Error in doInBackground", e);
            }
            return null;
        }

        private Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                if (bitmap == null) {
                    Log.e("WeatherForecast", "Bitmap is null after decoding stream.");
                } else {
                    Log.i("WeatherForecast", "Bitmap successfully decoded from URL.");
                }
                return bitmap;
            } catch (Exception e) {
                Log.e("WeatherForecast", "Error in getBitmapFromURL", e);
                return null;
            }
        }

        private void saveBitmapToLocalStorage(String fileName, Bitmap bitmap) {
            try {
                FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private boolean fileExists(String fileName) {
            File file = getBaseContext().getFileStreamPath(fileName);
            return file.exists();
        }

        private Bitmap loadBitmapFromLocalStorage(String fileName) {
            try {
                FileInputStream fis = openFileInput(fileName);
                return BitmapFactory.decodeStream(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            currentTemperatureTextView.setText("Current Temperature: " + currentTemp);
            minTemperatureTextView.setText("Min Temperature: " + minTemp);
            maxTemperatureTextView.setText("Max Temperature: " + maxTemp);
            weatherImageView.setImageBitmap(weatherIcon);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}