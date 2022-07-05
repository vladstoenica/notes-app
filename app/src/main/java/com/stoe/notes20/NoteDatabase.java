package com.stoe.notes20;

// 3

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1)  //link the Note table to the database, daca am mai multe tabele le pun cu virgula in acolade || version e pt SQLite
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;  //static deci poate fi accesat de oriunde din aplicatie pt ca avem mult nevoie de el  ,stie singur ca e o instanta de obiect din BD

    public abstract NoteDao noteDao();   //metoda, room database will handle it

    //required method for the DB
    public static synchronized NoteDatabase getInstance(Context context){
        if (instance == null){   //just a check, do this if instance is null
            instance = Room.databaseBuilder(context.getApplicationContext()
                    ,NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        //if not null
        return instance;
    }

    //punem chestii in bd

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            NoteDao noteDao = instance.noteDao();  //the insert operation belongs to this class
            ExecutorService executorService = Executors.newSingleThreadExecutor();

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    noteDao.insert(new Note("Title 1", "Description 1"));
                    noteDao.insert(new Note("Title 2", "Description 2"));
                    noteDao.insert(new Note("Title 3", "Description 3"));
                    noteDao.insert(new Note("Title 4", "Description 4"));
                    noteDao.insert(new Note("Title 21", "Description 5"));
                }
            });
        }
    };



}
