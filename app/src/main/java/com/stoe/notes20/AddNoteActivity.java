package com.stoe.notes20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    EditText title, description;
    Button cancel, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Note");  //schimbam numele activitatii in tab bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);  //adaugam meniul in tab bar
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.editTextTitle);
        description = findViewById(R.id.editTextDescription);
        cancel = findViewById(R.id.buttonCancel);
        save = findViewById(R.id.buttonSave);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Note not saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

    }

    public void saveNote(){   //trimitem data catre Main ca sa bagam intrarile in BD de acolo (din main)
        String noteTitle = title.getText().toString();
        String noteDescription = description.getText().toString();

        //luam titlul si descriptionul si le trimitem catre main
        Intent i = new Intent();
        i.putExtra("noteTitle", noteTitle);
        i.putExtra("noteDescription", noteDescription);
        setResult(RESULT_OK,i);
        finish();
    }
}