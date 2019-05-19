package com.example.notekelompok10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    //YOU CANT DOWNGRADE THE DATABASE VERSION
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "mynote.db";

    // TABLE NAME
    public static final String TABLE_NAME="Note";
    //these are the lit of fields in the table
    public static final String note_id="id";
    public static final String note_title="title";
    public static final String note_updated_at="updated_at";

    // TABLE NAME
    public static final String TABLE_NAME_2="List";
    //these are the lit of fields in the table
    public static final String list_id="id";
    public static final String list_value="value";
    public static final String list_status="status";//THIS COLUMN CANT BE NULL, IF NULL THE APP WILL ERROR
    public static final String list_updated_at="updated_at";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        String CREATE_TABLE_STUDENT = "CREATE TABLE " + Note.TABLE  + "("
                + Note.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Note.KEY_title + " TEXT, "
                + Note.KEY_value + " TEXT,"
                + Note.KEY_updated_at + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_TABLE_STUDENT);

        String CREATE_TABLE_LIST = "CREATE TABLE " + List.TABLE  + "("
                + List.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + List.KEY_value + " TEXT ,"
                + List.KEY_status  + " INTEGER ,"
                + List.KEY_updated_at + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_TABLE_LIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Method is called during an upgrade(new changes) of the database, e.g. if you increase
        // the database version
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + List.TABLE);

        // Create tables again
        onCreate(db);

    }

}