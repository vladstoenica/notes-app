package com.stoe.notes20;

// 4

//Respository-ul face o conexiune intre data sources si restul aplicatiei so the app is not concerned with where the data comes from
//in cazul asta vine doar din DB Room, dar altfel datele pot veni si din alte aplicatii

//Cica operatiunile de BD nu se pot face in main thread asa ca folosim una din 2 optiuni ca sa le ducem in alt thread
//1. Async task unde practic avem insert,delete, update si ruleaza prin doInBackground
//2. ExecutorService care creaza un thread nou

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {

    private NoteDao noteDao;  //am creat un obiect noteDao
    private LiveData<List<Note>> notes;  //an array pf notes

    //clasa pt realizat conexiunile specifice de repository
    ExecutorService executors = Executors.newSingleThreadExecutor();

    public NoteRepository(Application application){  //constructor
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();   //merge pt ca am creat un obiect sus
        notes = noteDao.getAllNotes();
    }

    //metodele sunt pt conexiunea de care am zis sus
    public void insert(Note note){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.insert(note);
            }
        });

    }

    public void update(Note note){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.update(note);
            }
        });
    }

    public void delete(Note note){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.delete(note);
            }
        });
    }

    public LiveData<List<Note>> getAllNotes(){  //BD room automat face operatiunile pt liveData
        return notes;
    }


}
