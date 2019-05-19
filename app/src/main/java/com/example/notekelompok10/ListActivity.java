package com.example.notekelompok10;

/**
 * Created by IMMANUEL on 10/23/2017.
 */

/* BELOW IS IMPORTED CLASS */

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    /* DECLARE ALL ATTRIBUT */
    TextView list_IdView;
    ImageButton listBottomBar;//YOUR BOTTOM NAVIGATION ATTRIBUTE
    TextView listBottomText;//YOUR BOTTOM NAVIGATION ATTRIBUTE
    //CheckBox list_IdCheckbox;
    // ATTRIBUTE TO FETCH DATA FROM DATABASE
    private DBHelper mHelper;
    private SQLiteDatabase dataBase;

    //variables to hold staff records
    private ArrayList<String> list_id_arrayList = new ArrayList<String> ();
    private ArrayList<String> list_value_arrayList = new ArrayList<String> ();
    private ArrayList<String> list_status_arrayList = new ArrayList<String> ();

    private ListView listList;
    private AlertDialog.Builder build;
    // END OF ATTRIBUTE TO FETCH DATA FROM DATABASE

    private ProgressDialog progressDialog;//DECLARE CLASS FOR LOADING ANIMATION

    // DRAWER NAVIGATION ATTRIBUTE
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    // END OF DRAWER NAVIGATION ATTRIBUTE

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");//LOADING MESSAGE
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();//SHOW THE LOADING PROGRESS*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listList = (ListView) findViewById(R.id.listListView);// SET THE ListView ID FROM VIEW / XML FILE
        //instantiate the SQLiteHelper class every time you open the database
        mHelper = new DBHelper(this);

        // PREPARE VIEW AND FUNCTION FOR DRAWER NAVIGATION
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // END OF PREPARE VIEW AND FUNCTION FOR DRAWER NAVIGATION
    }

    // DRAWER NAVIGATION FUNCTION
    private void addDrawerItems() {
        String[] menuSideArray = { "Note", "List" };
        mAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, menuSideArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    /* ALL RESPOND FROM BUTTON AT SIDE MENU ARE HERE */
                    case 0:
                        // Intent code for open new activity through intent.
                        // SET NEW INTENT, AND OPEN NEW VIEW ON ANOTHER ACTIVITY
                        Intent intentNote = new Intent (ListActivity.this, MainActivity.class);
                        startActivity(intentNote);
                        break;
                    case 1:
                        //Intent intentList = new Intent(MainActivity.this, ListActivity.class);
                        //startActivity(intentList);
                        break;
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle (this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Main Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    // END OF DRAWER NAVIGATION FUNCTION

    @Override
    protected void onResume() {
        //refresh data for screen is invoked/displayed
        displayData();
        super.onResume();
        //progressDialog.hide();//HIDE THE LOADING PROGRESS
    }

    /**
     * displays data from SQLite
     */
    private void displayData() {
        dataBase = mHelper.getWritableDatabase();
        //the SQL command to fetched all records from the table
        final Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
                + DBHelper.TABLE_NAME_2, null);

        //reset variables
        list_id_arrayList.clear();
        list_value_arrayList.clear();
        list_status_arrayList.clear();

        //fetch each record
        if (mCursor.moveToFirst()) {
            do {
                //get data from field
                list_id_arrayList.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.list_id)));
                list_value_arrayList.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.list_value)));
                list_status_arrayList.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.list_status)));

            } while (mCursor.moveToNext());
            //do above till data exhausted
        }

        //display to screen, SEND THIS ARRAY VALUE TO ListAdapter
        ListAdapter disadpt = new ListAdapter(ListActivity.this, list_id_arrayList, list_value_arrayList, list_status_arrayList);
        listList.setAdapter(disadpt);

        //ONCLICK EVENT ON LIST ITEM
        listList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long list_id_arrayList) {
                list_IdView = (TextView) view.findViewById(R.id.list_IdView);
                String listIdParam = list_IdView.getText().toString();
                Intent objIndent = new Intent (getApplicationContext(),ListDetail.class);
                objIndent.putExtra("listIdParam", Integer.parseInt( listIdParam));
                startActivity(objIndent);
                //Log.d("Clicked item id", " "+ listIdParam);
            }
        });

        mCursor.close();
    }//end displayData

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);// GET THE XML FILE FOR VIEW
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        /* ALL RESPOND FROM BUTTON AT MENU BAR ARE HERE */
            case R.id.add:
                // Intent code for open new activity through intent.
                // SET NEW INTENT, AND OPEN NEW VIEW ON ANOTHER ACTIVITY
                Intent intent = new Intent (ListActivity.this, ListDetail.class);
                startActivity(intent);
                return (true);
            case R.id.about:
                Toast.makeText(this, R.string.about_toast, Toast.LENGTH_LONG).show();
                return (true);
            case R.id.exit:
                finish();
                return (true);

        }

        // DRAWER NAVIGATION CLICK EVENT ON MAIN MENU
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // END OF DRAWER NAVIGATION CLICK EVENT ON MAIN MENU

        return (super.onOptionsItemSelected(item));

    }

    //START NOTE ACTIVITY
    public void goToActivityNote (View view){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

}
