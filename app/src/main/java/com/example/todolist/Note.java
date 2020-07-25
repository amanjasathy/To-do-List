package com.example.todolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//table note
@Entity(tableName = "note_table")
public class Note {
    //room will generate columns for these fields
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private int priority;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    //only value that is not in constructor
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
