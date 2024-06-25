package com.example.androidassignments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class ListItemsActivity extends AppCompatActivity {

    private static final String TAG = "MyApp";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_CAMERA_PERMISSION = 200;
    ImageButton imageButton;
    private int lastResultCode = Activity.RESULT_CANCELED;
    private Intent lastResultData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_items);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.i(TAG, "inside onCreate") ;

        imageButton = findViewById(R.id.image_button);
        imageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ListItemsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListItemsActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    openCamera();
                }
            }
        });

        Switch switchButton = findViewById(R.id.switch_button);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String message = isChecked ? getString(R.string.switch_on) : getString(R.string.switch_off);
                int duration = isChecked ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG;
                Toast.makeText(ListItemsActivity.this, message, duration).show();
            }
        });

        CheckBox checkBox = findViewById(R.id.check_box);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                    builder.setMessage(R.string.dialog_message)
                            .setTitle(R.string.dialog_title)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("Response", getString(R.string.sent_response));
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish();
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }
        });
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) Objects.requireNonNull(extras).get("data");
            imageButton.setImageBitmap(imageBitmap);
        }
        this.lastResultCode = resultCode;
        this.lastResultData = data;
    }

    public int getResultCode() {
        return lastResultCode;
    }
    public Intent getResultData() {
        return lastResultData;
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


