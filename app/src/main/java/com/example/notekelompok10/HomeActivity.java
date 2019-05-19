package com.example.notekelompok10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class HomeActivity  extends AppCompatActivity {

    //DECLARE ATTRIBUTE
    //private BottomBar bottomBar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);// GET THE XML FILE FOR VIEW
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        /* ALL RESPOND FROM BUTTON AT MENU BAR ARE HERE */
            case R.id.about:
                Toast.makeText(this,"Kelompok 10", Toast.LENGTH_LONG).show();
                return (true);
            case R.id.exit:
                finish();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

}
