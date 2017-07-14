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
import java.util.Date;
import java.util.List;

import br.com.trinopolo.appunittest.notes.Note;
import br.com.trinopolo.appunittest.notes.NoteService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by marconvcm on 14/07/2017.
 */

@RunWith(AndroidJUnit4.class)
public class NoteServiceUnitTest {

    private Context context;
    private SQLiteDatabase database;

    @Before
    public void initialize() {
        context = InstrumentationRegistry.getTargetContext();
        database = new DatabaseHelper(context).getWritableDatabase();
    }

    @After
    public void deinitialize() {
        String path = database.getPath(); // /data/data/br.com.trinopolo.appunittest/database/AppSqlite.db
        (new File(path)).delete();
    }

    @Test
    public void testAddNote() {

        NoteService service = new NoteService(database);

        Note note = new Note(0, "Minha nota", "Anotacao", new Date());

        boolean result = service.add(note);

        assertTrue(result == true);
    }

    @Test
    public void testGetNote() {

        NoteService service = new NoteService(database);

        Note note1 = new Note(0, "Minha nota 1", "Anotacao", new Date());
        service.add(note1);

        Note note2 = new Note(0, "Minha nota 2", "Anotacao", new Date());
        service.add(note2);

        List<Note> notes = service.getAll();

        assertTrue(notes.size() == 2);
    }

    @Test
    public void testUpdateNote() {

        NoteService service = new NoteService(database);

        Note note = new Note(0, "Minha nota", "Anotacao", new Date());

        service.add(note);

        Note note1 = service.getAll().get(0);

        note1.setTitle("Minha super nota");

        service.update(note1);

        note1 = service.getAll().get(0);

        assertEquals(note1.getTitle(), "Minha super nota");
    }

    @Test
    public void testDeleteNote() {

        NoteService service = new NoteService(database);

        Note note1 = new Note(0, "Minha nota 1", "Anotacao", new Date());
        service.add(note1);

        Note note2 = new Note(0, "Minha nota 2", "Anotacao", new Date());
        service.add(note2);

        Note note = service.getAll().get(0);

        service.delete(note);

        List<Note> notes = service.getAll();

        assertTrue(notes.size() == 1);
    }

    @Test
    public void testGetByIdNote() {

        NoteService service = new NoteService(database);

        Note note = new Note(0, "Minha nota", "Anotacao", new Date());

        service.add(note);

        Note note1 = service.getAll().get(0);

        Note note2 = service.get(note1.getId());

        assertEquals(note2.getTitle(), "Minha nota");
    }
}
