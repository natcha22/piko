package com.piko.note.db;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import com.piko.note.model.Note;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class NotesDao_Impl implements NotesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfNote;

  private final SharedSQLiteStatement __preparedStmtOfDeleteNoteByID;

  public NotesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNote = new EntityInsertionAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `notes`(`id`,`text`,`date`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.id);
        if (value.getNoteText() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNoteText());
        }
        stmt.bindLong(3, value.getNoteDate());
      }
    };
    this.__deletionAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `notes` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__updateAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `notes` SET `id` = ?,`text` = ?,`date` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.id);
        if (value.getNoteText() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNoteText());
        }
        stmt.bindLong(3, value.getNoteDate());
        stmt.bindLong(4, value.id);
      }
    };
    this.__preparedStmtOfDeleteNoteByID = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM notes WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertNote(Note note) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfNote.insert(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteNote(Note note) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfNote.handle(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateNote(Note note) {
    __db.beginTransaction();
    try {
      __updateAdapterOfNote.handle(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteNoteByID(int noteID) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteNoteByID.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      _stmt.bindLong(_argIndex, noteID);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteNoteByID.release(_stmt);
    }
  }

  @Override
  public List<Note> getNotes() {
    final String _sql = "SELECT * FROM notes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfNoteText = _cursor.getColumnIndexOrThrow("text");
      final int _cursorIndexOfNoteDate = _cursor.getColumnIndexOrThrow("date");
      final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Note _item;
        _item = new Note();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        final String _tmpNoteText;
        _tmpNoteText = _cursor.getString(_cursorIndexOfNoteText);
        _item.setNoteText(_tmpNoteText);
        final long _tmpNoteDate;
        _tmpNoteDate = _cursor.getLong(_cursorIndexOfNoteDate);
        _item.setNoteDate(_tmpNoteDate);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Note getNoteByID(int noteID) {
    final String _sql = "SELECT * FROM notes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, noteID);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfNoteText = _cursor.getColumnIndexOrThrow("text");
      final int _cursorIndexOfNoteDate = _cursor.getColumnIndexOrThrow("date");
      final Note _result;
      if(_cursor.moveToFirst()) {
        _result = new Note();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        final String _tmpNoteText;
        _tmpNoteText = _cursor.getString(_cursorIndexOfNoteText);
        _result.setNoteText(_tmpNoteText);
        final long _tmpNoteDate;
        _tmpNoteDate = _cursor.getLong(_cursorIndexOfNoteDate);
        _result.setNoteDate(_tmpNoteDate);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
