package com.piko.note.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.piko.note.model.Note;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //if note exist, replace it
    void insertNote(Note note);
    @Delete
    void deleteNote(Note note);
    @Update
    void updateNote(Note note);

    @Query("SELECT * FROM notes") //list all notes from db
    List<Note> getNotes();
    @Query("SELECT * FROM notes WHERE id = :noteID") //get note by id
    Note getNoteByID(int noteID);
    @Query("DELETE FROM notes WHERE id = :noteID") //delete note by id
    void deleteNoteByID(int noteID);
}
