package com.piko.note;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.piko.note.adapters.NotesAdapter;
import com.piko.note.callback.NoteEventListener;
import com.piko.note.db.NotesDB;
import com.piko.note.db.NotesDao;
import com.piko.note.model.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.piko.note.EditNoteActivity.NOTE_EXTRA_Key;

public class NoteActivity extends AppCompatActivity implements NoteEventListener {
    private static final String TAG = "NoteActivity";
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    private NotesAdapter adapter;
    private NotesDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO : 13/05/2018 add new note
                onAddNewNote();
            }
        });
        dao = NotesDB.getInstance(this).notesDao();
    }

    private void loadNotes() {
        this.notes = new ArrayList<>();
        List<Note> list = dao.getNotes(); // get all notes from db
        this.notes.addAll(list);
        this.adapter = new NotesAdapter(this,notes);

        this.adapter.setListener(this);

        this.recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }

    private void onAddNewNote() {
        // TODO: 27/11/2018 start EditNoteActivity
        startActivity(new Intent(this,EditNoteActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    @Override
    public void onNoteClick(Note note) {
        //Log.d(TAG,"onNoteLongClick: "+note.toString());
        Intent edit = new Intent(this,EditNoteActivity.class);
        edit.putExtra(NOTE_EXTRA_Key,note.getId());
        startActivity(edit);
    }

    @Override
    public void onNoteLongClick(final Note note) {
        //Log.d(TAG,"onNoteLongClick: "+note.getId());
        new AlertDialog.Builder(this).setTitle(R.string.app_name).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dao.deleteNote(note);
                loadNotes();
            }
        }).create().show();
    }
}
