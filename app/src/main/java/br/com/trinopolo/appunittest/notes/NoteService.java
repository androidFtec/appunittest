package br.com.trinopolo.appunittest.notes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by marconvcm on 14/07/2017.
 */

public class NoteService {

    private SQLiteDatabase database;

    public NoteService(SQLiteDatabase database) {

        this.database = database;
    }

    public boolean add(Note note) {
        try {

            ContentValues values = new ContentValues();

            values.put("title", note.getTitle());
            values.put("body", note.getBody());
            values.put("created_at", note.getCreateAt().getTime());
            values.put("status", note.getStatus());

            return database.insert("Notes", "", values) > 0;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<Note> getAll() {

        try {

            List<Note> notes = new ArrayList<>();

            String sql = "SELECT * FROM Notes";
            Cursor cursor = database.rawQuery(sql, new String[] {});

            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {

                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String body = cursor.getString(2);
                Date date = new Date(cursor.getLong(3));

                Note note = new Note(id, title, body, date);

                notes.add(note);

                cursor.moveToNext();
            }

            return notes;

        } catch (Exception ex) {
            return  null;
        }
    }

    public boolean update(Note note) {

        try {

            ContentValues values = new ContentValues();

            values.put("title", note.getTitle());
            values.put("body", note.getBody());
            values.put("created_at", note.getCreateAt().getTime());

            String where = "id = " + note.getId();

            return database.update("Notes", values, where, new  String[]{}) > 0;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean delete(Note note) {

        try {
            String where = "id = " + note.getId();
            return database.delete("Notes", where, new  String[]{}) > 0;
        } catch (Exception ex) {
            return false;
        }
    }

    public Note get(int id) {

        List<Note> all = this.getAll();

        for (Note note : all) {
            if(note.getId() == id) {
                return note;
            }
        }

        return null;
    }
}
