package com.stoe.notes20;

// 2

//Data access object -- abilitatea userului de a accesa data din DB
//E o clasa abstracta sau interfata pt ca doar zice ce operatiuni are DB

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {    //interfata sau clasa abstracta ot ca nu scriem metode in ea

    @Insert
    void insert(Note note);  //nu declaram body-ul ca stie din anotatie ce sa faca

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM NOTE_TABLE ORDER BY ID ASC")  //cum sa fie listed notele in DB
    LiveData<List<Note>> getAllNotes();
    // asta e o fct care returneaza un List<Note> cu toate notitele -- e doar un interface, de aia nu am codul aici
    // LiveData e ca sa arate in recyclerview live ce se intampla in BD.zicem ca e observat de livedata
}
