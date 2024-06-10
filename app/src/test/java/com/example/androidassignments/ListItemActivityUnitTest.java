package com.example.androidassignments;

import static android.app.Activity.RESULT_OK;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34)
public class ListItemsActivityUnitTest {

    private ListItemsActivity listItemsActivity;
    private ImageButton imageButton;
    private Switch aSwitch;
    private CheckBox checkBox;

    @Before
    public void setUp() {
        listItemsActivity = Robolectric.buildActivity(ListItemsActivity.class).create().get();
        imageButton = listItemsActivity.findViewById(R.id.image_button);
        aSwitch = listItemsActivity.findViewById(R.id.switch_button);
        checkBox = listItemsActivity.findViewById(R.id.check_box);
    }

    @Test
    public void testSwitchToggle() {
        aSwitch.setChecked(true);
        assertEquals(listItemsActivity.getString(R.string.switch_on), ShadowApplication.getInstance().getLatestToast().getText());

        aSwitch.setChecked(false);
        assertEquals(listItemsActivity.getString(R.string.switch_off), ShadowApplication.getInstance().getLatestToast().getText());
    }

    @Test
    public void testTakePicture() {
        Intent takePictureIntent = new Intent(ACTION_IMAGE_CAPTURE);
        Bitmap bitmap = BitmapFactory.decodeResource(listItemsActivity.getResources(), R.drawable.ic_launcher_foreground); // Placeholder
        takePictureIntent.putExtra("data", bitmap);

        listItemsActivity.startActivityForResult(takePictureIntent, ListItemsActivity.REQUEST_IMAGE_CAPTURE);

        Intent resultData = new Intent();
        resultData.putExtra("data", bitmap);
        listItemsActivity.onActivityResult(ListItemsActivity.REQUEST_IMAGE_CAPTURE, RESULT_OK, resultData);

        assertEquals(bitmap, imageButton.getDrawable());
    }

    @Test
    public void testCheckboxChecked() {
        AlertDialog.Builder builder = mock(AlertDialog.Builder.class);
        when(builder.setMessage(any(CharSequence.class))).thenReturn(builder);
        when(builder.setTitle(any(CharSequence.class))).thenReturn(builder);
        when(builder.setPositiveButton(any(CharSequence.class), any(DialogInterface.OnClickListener.class))).thenReturn(builder);
        when(builder.setNegativeButton(any(CharSequence.class), any(DialogInterface.OnClickListener.class))).thenReturn(builder);
        when(builder.show()).thenAnswer(new Answer<AlertDialog>() {
            @Override
            public AlertDialog answer(InvocationOnMock invocation) {
                return mock(AlertDialog.class);
            }
        });

        checkBox.setChecked(true);

        ArgumentCaptor<DialogInterface.OnClickListener> positiveCaptor = ArgumentCaptor.forClass(DialogInterface.OnClickListener.class);
        verify(builder).setPositiveButton(any(CharSequence.class), positiveCaptor.capture());
        positiveCaptor.getValue().onClick(null, DialogInterface.BUTTON_POSITIVE);

        Intent resultData = new Intent();
        resultData.putExtra("Response", listItemsActivity.getString(R.string.sent_response));
        assertEquals(Activity.RESULT_OK, listItemsActivity.getResultCode());
        assertEquals(resultData.getStringExtra("Response"), listItemsActivity.getResultData().getStringExtra("Response"));
    }

    @Test
    public void testOnRequestPermissionsResultGranted() {
        listItemsActivity.onRequestPermissionsResult(ListItemsActivity.REQUEST_CAMERA_PERMISSION, new String[]{Manifest.permission.CAMERA}, new int[]{PERMISSION_GRANTED});
        assertTrue(ContextCompat.checkSelfPermission(listItemsActivity, Manifest.permission.CAMERA) == PERMISSION_GRANTED);
    }

    @Test
    public void testOnRequestPermissionsResultDenied() {
        listItemsActivity.onRequestPermissionsResult(ListItemsActivity.REQUEST_CAMERA_PERMISSION, new String[]{Manifest.permission.CAMERA}, new int[]{PackageManager.PERMISSION_DENIED});
        assertTrue(ContextCompat.checkSelfPermission(listItemsActivity, Manifest.permission.CAMERA) != PERMISSION_GRANTED);
    }
}
