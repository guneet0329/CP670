package com.example.androidassignments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidassignments.databinding.ActivityTestToolbarBinding;
import com.google.android.material.snackbar.Snackbar;

public class TestToolbar extends AppCompatActivity {

    private ActivityTestToolbarBinding binding;
    private String customMessage = "Hi, this is a Custom Snackbar Message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, customMessage, Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        Log.d("TestToolbar", "Menu inflated");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_one) {
            Log.d("Toolbar", "Option 1 selected");
            Snackbar.make(findViewById(R.id.fab), "You selected item 1", Snackbar.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_two) {
            Log.d("Toolbar", "Option 2 selected");
            showExitConfirmationDialog();
            return true;
        } else if (id == R.id.action_three) {
            Log.d("Toolbar", "Option 3 selected");
            showCustomDialog();
            return true;
        } else if (id == R.id.action_about) {
            Log.d("Toolbar", "About selected");
            Snackbar.make(findViewById(R.id.fab), "Version 1.0, by Guneet Singh", Snackbar.LENGTH_LONG).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title_text);
        builder.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.dialog_negative, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    Object showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_new_message, null);
        builder.setView(dialogView)
                .setTitle(R.string.dialog_title_text)
                .setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editTextNewMessage = dialogView.findViewById(R.id.et_new_message);
                        customMessage = editTextNewMessage.getText().toString();
                        if (customMessage.isEmpty()) {
                            customMessage = "This is a custom Snackbar message!";
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_negative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        return null;
    }
}