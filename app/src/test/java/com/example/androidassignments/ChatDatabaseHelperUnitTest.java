package com.example.androidassignments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, sdk = 30)
public class ChatDatabaseHelperUnitTest extends TestCase {

    private Context context;
    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Before
    public void setUp() {
        context = Robolectric.setupActivity(MainActivity.class);
        dbHelper = new ChatDatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @Test
    public void testDatabaseCreation() {
        assertTrue(db.isOpen());
        assertEquals(1, dbHelper.getReadableDatabase().getVersion());
    }

    @Test
    public void testTableCreation() {
        dbHelper.onCreate(db);
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + ChatDatabaseHelper.TABLE_NAME + "';", null);
        assertTrue(cursor.getCount() > 0);
        cursor.close();
    }
}
