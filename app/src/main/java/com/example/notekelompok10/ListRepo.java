package com.example.notekelompok10;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ListRepo {

    private DBHelper dbHelper;

    public ListRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(List student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues ();
        values.put(List.KEY_value,student.value);
        values.put(List.KEY_status,0);

        // Inserting Row
        long List_Id = db.insert(List.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) List_Id;
    }

    public void delete(int List_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(List.TABLE, List.KEY_ID + "= ?", new String[] { String.valueOf(List_Id) });
        db.close(); // Closing database connection
    }

    public void update(List student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues ();

        values.put(List.KEY_value,student.value);
        //values.put(List.KEY_title, student.title);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(List.TABLE, values, List.KEY_ID + "= ?", new String[] { String.valueOf(student.list_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getListList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                List.KEY_ID + "," +
                List.KEY_status + "," +
                List.KEY_value +
                " FROM " + List.TABLE;

        //List student = new List();
        ArrayList<HashMap<String, String>> listList = new ArrayList<HashMap<String, String>> ();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String> ();
                student.put("id", cursor.getString(cursor.getColumnIndex(List.KEY_ID)));
                student.put("title", cursor.getString(cursor.getColumnIndex(List.KEY_value)));
                listList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listList;

    }

    public List getListById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                List.KEY_ID + "," +
                List.KEY_value + "," +
                List.KEY_status +
                " FROM " + List.TABLE
                + " WHERE " +
                List.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        List student = new List();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                student.list_ID =cursor.getInt(cursor.getColumnIndex(List.KEY_ID));
                student.status =cursor.getString(cursor.getColumnIndex(List.KEY_status));
                student.value  =cursor.getString(cursor.getColumnIndex(List.KEY_value));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return student;
    }

}
