package com.stoe.notes20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateNoteActivity extends AppCompatActivity {

    EditText title, description;
    Button cancel, save;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit Note");
        setContentView(R.layout.activity_update_note);

        title = findViewById(R.id.editTextTitleUpdate);
        description = findViewById(R.id.editTextDescriptionUpdate);
        cancel = findViewById(R.id.buttonCancelUpdate);
        save = findViewById(R.id.buttonSaveUpdate);

        getData();  //in on create ca sa se faca transferul de date atunci cand e deschisa activitatea

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
                updateNote();
            }
        });

    }

    private void updateNote(){
        String updatedTitle = title.getText().toString();
        String updatedDescription = description.getText().toString();
        Intent intent = new Intent();  //trimitem datele updatate inapoi in main
        intent.putExtra("updatedTitle", updatedTitle);
        intent.putExtra("updatedDescription", updatedDescription);
        if (noteId != -1){   //im not gonna send the id if its -1
            intent.putExtra("noteId", noteId);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    //ca sa primim data pe care o are deja notita trimisa din main
    public void getData(){
        Intent i = getIntent();
        noteId = i.getIntExtra("id", -1);  //-1 e daca e o problema atunci cand e transmis id-ul
        String noteTitle = i.getStringExtra("title");
        String noteDescription = i.getStringExtra("description");

        title.setText(noteTitle);
        description.setText(noteDescription);
    }
}