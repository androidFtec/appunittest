package br.com.trinopolo.appunittest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private SQLiteDatabase database;
    private Context context;

    @Before
    public void initialize() {
        context = InstrumentationRegistry.getTargetContext();
        database = new DatabaseHelper(context).getWritableDatabase();
    }

    @After
    public void deinitialize() {
        (new File(database.getPath())).delete();
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("br.com.trinopolo.appunittest", appContext.getPackageName());
    }
}
