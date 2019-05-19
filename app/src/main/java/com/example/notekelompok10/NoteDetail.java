package com.example.notekelompok10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteDetail extends AppCompatActivity {

    EditText editTextTitle;
    EditText editTextValue;
    private int _Note_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextValue = (EditText) findViewById(R.id.editTextValue);

        _Note_Id =0;
        Intent intent = getIntent();
        _Note_Id =intent.getIntExtra("noteIdParam", 0);//THIS noteIdParam ARE RECIEVE FROM MAIN ACTIVITY, TO GET THE CLICKED id FROM LIST ITEM
        NoteRepo repo = new NoteRepo(this);
        Note student = new Note();
        student = repo.getNoteById(_Note_Id);

        editTextTitle.setText(student.title);
        editTextValue.setText(student.value);
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
            NoteRepo repo = new NoteRepo(this);
            Note student = new Note();
            student.title=editTextTitle.getText().toString();
            student.value=editTextValue.getText().toString();
            student.note_ID=_Note_Id;

            if (_Note_Id==0){
                _Note_Id = repo.insert(student);

                Toast.makeText(this,"New Note Insert", Toast.LENGTH_SHORT).show();
                finish();
            }else{

                repo.update(student);
                Toast.makeText(this,"Note Record updated", Toast.LENGTH_SHORT).show();
                finish();
            }

            return(true);
        case R.id.btnDeleteNote:
            NoteRepo repo2 = new NoteRepo(this);
            repo2.delete(_Note_Id);
            Toast.makeText(this, "Note Record Deleted", Toast.LENGTH_SHORT).show();
            finish();
            return(true);
        case R.id.exit:
            finish();
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

}