package com.piko.note.callback;

import com.piko.note.model.Note;

public interface NoteEventListener {

    
    void onNoteClick(Note note);
    void  onNoteLongClick(Note note);
}
