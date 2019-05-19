package com.example.notekelompok10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class NoteRepo {
    private DBHelper dbHelper;

    public NoteRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    //STORE FUNCTION
    public int insert(Note student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues ();
        values.put(Note.KEY_value,student.value);
        values.put(Note.KEY_title, student.title);
        values.put(Note.KEY_updated_at, getDateTime());

        // Inserting Row
        long note_Id = db.insert(Note.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) note_Id;
    }

    //DELETE FUNCTION
    public void delete(int note_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Note.TABLE, Note.KEY_ID + "= ?", new String[] { String.valueOf(note_Id) });
        db.close(); // Closing database connection
    }

    //UPDATE FUNCTION
    public void update(Note student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues ();

        values.put(Note.KEY_value,student.value);
        values.put(Note.KEY_title, student.title);
        values.put(Note.KEY_updated_at, getDateTime());

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Note.TABLE, values, Note.KEY_ID + "= ?", new String[] { String.valueOf(student.note_ID) });
        db.close(); // Closing database connection
    }

    //INDEX FUNCTION
    public ArrayList<HashMap<String, String>> getNoteList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Note.KEY_ID + "," +
                Note.KEY_title + "," +
                Note.KEY_value +
                " FROM " + Note.TABLE;

        //Note student = new Note();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>> ();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String> ();
                student.put("id", cursor.getString(cursor.getColumnIndex(Note.KEY_ID)));
                student.put("title", cursor.getString(cursor.getColumnIndex(Note.KEY_title)));
                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }

    //SHOW FUNCTION
    public Note getNoteById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Note.KEY_ID + "," +
                Note.KEY_title + "," +
                Note.KEY_value +
                " FROM " + Note.TABLE
                + " WHERE " +
                Note.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Note student = new Note();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                student.note_ID =cursor.getInt(cursor.getColumnIndex(Note.KEY_ID));
                student.title =cursor.getString(cursor.getColumnIndex(Note.KEY_title));
                student.value  =cursor.getString(cursor.getColumnIndex(Note.KEY_value));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return student;
    }

    //GET CURRENT TIME AND DATE
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat (
        //"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        "dd/MM/yyyy HH:mm", Locale.getDefault());
        Date date = new Date ();
        return dateFormat.format(date);
    }

}