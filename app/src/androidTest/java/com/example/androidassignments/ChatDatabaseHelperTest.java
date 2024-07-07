package com.example.androidassignments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ChatDatabaseHelperTest {

    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dbHelper = new ChatDatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @After
    public void tearDown() {
        dbHelper.close();
        db.close();
    }

    @Test
    public void testDatabaseCreation() {
        assertTrue(db.isOpen());
        assertEquals(ChatDatabaseHelper.VERSION_NUM, db.getVersion());
    }

    @Test
    public void testTableCreation() {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + ChatDatabaseHelper.TABLE_NAME + "';", null);
        assertTrue(cursor.getCount() > 0);
        cursor.close();
    }

    @Test
    public void testInsertMessage() {
        ContentValues values = new ContentValues();
        values.put(ChatDatabaseHelper.KEY_MESSAGE, "Hello, World!");
        long rowId = db.insert(ChatDatabaseHelper.TABLE_NAME, null, values);
        assertTrue(rowId != -1);

        Cursor cursor = db.query(ChatDatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("Hello, World!", cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
        cursor.close();
    }

    @Test
    public void testUpgradeDatabase() {
        // Simulate database upgrade by changing the version number
        dbHelper.onUpgrade(db, 1, 2);

        // Verify the table is recreated after upgrade
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + ChatDatabaseHelper.TABLE_NAME + "';", null);
        assertTrue(cursor.getCount() > 0);
        cursor.close();
    }
}
