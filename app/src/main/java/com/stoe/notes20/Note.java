package com.stoe.notes20;

// 1
//This is the entity stored in the DB

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")      //this allows the room database to run in the background
//more tables, more classes like this
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;

    public String description;

    //constructorul clasei
    public Note(String title, String description) {  //fara id ca se genereaza automat
        this.title = title;
        this.description = description;
    }

    //getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    //setter pt id (folosit doar in caz de erori sa le dam intrarilor id -1)
    public void setId(int id) {
        this.id = id;
    }
}
