package com.example.notekelompok10;

/**
 * Created by IMMANUEL on 10/23/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class ListDetail  extends AppCompatActivity {

    EditText editTextValue;
    //EditText editTextStatus;
    private int _List_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);

        editTextValue = (EditText) findViewById(R.id.editTextValue);
        //editTextStatus = (EditText) findViewById(R.id.editTextStatus);

        _List_Id =0;
        Intent intent = getIntent();
        _List_Id =intent.getIntExtra("listIdParam", 0);//THIS listIdParam ARE RECIEVE FROM MAIN ACTIVITY, TO GET THE CLICKED id FROM LIST ITEM
        ListRepo repo = new ListRepo(this);
        List student = new List();
        student = repo.getListById(_List_Id);

        editTextValue.setText(student.value);
        //editTextStatus.setText(student.status);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crud, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.btnSaveNote:
            ListRepo repo = new ListRepo(this);
            List student = new List();
            student.value=editTextValue.getText().toString();
            student.list_ID=_List_Id;

            if (_List_Id==0){
                _List_Id = repo.insert(student);

                Toast.makeText(this,"New List Insert", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                repo.update(student);
                Toast.makeText(this,"List Record updated", Toast.LENGTH_SHORT).show();
                finish();
            }

            return(true);
        case R.id.btnDeleteNote:
            ListRepo repo2 = new ListRepo(this);
            repo2.delete(_List_Id);
            Toast.makeText(this, "List Record Deleted", Toast.LENGTH_SHORT).show();
            finish();
            return(true);
        case R.id.exit:
            finish();
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

}
