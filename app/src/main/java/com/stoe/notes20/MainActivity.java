package com.stoe.notes20;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    ActivityResultLauncher<Intent> activityResultLauncherForAddNote;  //alternativa mai fancy la startActivity(), trebuie declarata si registered in onCreate
    //ca sa fie clean, fac asta in metoda registerActivityForAddNote si doar o chem in onCreate
    ActivityResultLauncher<Intent> activityResultLauncherForUpdateNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);   //adauga meniul in action bar
        setContentView(R.layout.activity_main);

        //astea trebuiesc called aici ca sa trimitem data catre activitatile unde trimit
        registerActivityForAddNote();
        registerActivityForUpdateNote();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));  //cod standard

        NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);     //leaga adapterul la recycler

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);  //update the recycler
            }
        });  //This is the LiveData method and it will observe changes in the DB


        //folosite pt press and drag(onMove) si swipe
        ////////////////////
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {    //slide to delete | 0 e pt drag and drop (move) ca nu folosim
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNotes(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        /////////////////////

        //pt click pe recyclerView (pt update)
        adapter.setOnItemCLickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {  //ia note ca argument ca asa am definit noi in NoteAdapter
                Intent intent = new Intent(MainActivity.this, UpdateNoteActivity.class);
                //trimitem datele pe care le are deja note-ul in Update Activity
                intent.putExtra("id", note.getId());
                intent.putExtra("title", note.getTitle());
                intent.putExtra("description", note.getDescription());
                //in loc de startActivity folosim activityResultLauncher, care trebuie iar declarat si registered
                activityResultLauncherForUpdateNote.launch(intent);
            }
        });

    }



    //pt meniu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {        //pt punctele de meniu
        getMenuInflater().inflate(R.menu.new_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {     //tot pt meniu, in fct de care optiune o apesi sa deschida activity-ul respectiv
        switch (item.getItemId()){     //detects which element i click from the menu
            case R.id.top_menu:
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                //startActivityForResult(intent, 1);   //la fel ca startActivity(), result code 1 nu e ft relevant
                activityResultLauncherForAddNote.launch(intent);   //tot ca startActivity()
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //pt activityResultLauncherForAddNote.launch(intent) (echivalentul de startActivity)
    public void registerActivityForAddNote(){
        activityResultLauncherForAddNote = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        Intent data = result.getData();
                        if (resultCode == RESULT_OK && data != null){    //RESULT_OK din AddNoteActivity, aka daca a fost apasat save acolo
                            String title = data.getStringExtra("noteTitle");
                            String description = data.getStringExtra("noteDescription");

                            Note note = new Note(title, description);
                            noteViewModel.insert(note);
                        }
                    }
                });
    }

    //same shit dar pt update
    public void registerActivityForUpdateNote(){
        activityResultLauncherForUpdateNote = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                    }
                });
    }
}