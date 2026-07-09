package com.example.moodjournal.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class JournalDao_Impl implements JournalDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<JournalEntry> __insertionAdapterOfJournalEntry;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<JournalEntry> __deletionAdapterOfJournalEntry;

  private final EntityDeletionOrUpdateAdapter<JournalEntry> __updateAdapterOfJournalEntry;

  public JournalDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfJournalEntry = new EntityInsertionAdapter<JournalEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `journal_entries` (`id`,`text`,`timestamp`,`moodScore`,`primaryEmotion`,`themes`,`reflection`,`concernFlag`,`status`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final JournalEntry entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getText() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getText());
        }
        statement.bindLong(3, entity.getTimestamp());
        if (entity.getMoodScore() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getMoodScore());
        }
        if (entity.getPrimaryEmotion() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPrimaryEmotion());
        }
        if (entity.getThemes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getThemes());
        }
        if (entity.getReflection() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getReflection());
        }
        final int _tmp = entity.getConcernFlag() ? 1 : 0;
        statement.bindLong(8, _tmp);
        final String _tmp_1 = __converters.fromStatus(entity.getStatus());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_1);
        }
      }
    };
    this.__deletionAdapterOfJournalEntry = new EntityDeletionOrUpdateAdapter<JournalEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `journal_entries` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final JournalEntry entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfJournalEntry = new EntityDeletionOrUpdateAdapter<JournalEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `journal_entries` SET `id` = ?,`text` = ?,`timestamp` = ?,`moodScore` = ?,`primaryEmotion` = ?,`themes` = ?,`reflection` = ?,`concernFlag` = ?,`status` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final JournalEntry entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getText() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getText());
        }
        statement.bindLong(3, entity.getTimestamp());
        if (entity.getMoodScore() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getMoodScore());
        }
        if (entity.getPrimaryEmotion() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPrimaryEmotion());
        }
        if (entity.getThemes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getThemes());
        }
        if (entity.getReflection() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getReflection());
        }
        final int _tmp = entity.getConcernFlag() ? 1 : 0;
        statement.bindLong(8, _tmp);
        final String _tmp_1 = __converters.fromStatus(entity.getStatus());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_1);
        }
        statement.bindLong(10, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final JournalEntry entry, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfJournalEntry.insertAndReturnId(entry);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final JournalEntry entry, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfJournalEntry.handle(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final JournalEntry entry, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfJournalEntry.handle(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<JournalEntry>> getAll() {
    final String _sql = "SELECT * FROM journal_entries ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"journal_entries"}, new Callable<List<JournalEntry>>() {
      @Override
      @NonNull
      public List<JournalEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfMoodScore = CursorUtil.getColumnIndexOrThrow(_cursor, "moodScore");
          final int _cursorIndexOfPrimaryEmotion = CursorUtil.getColumnIndexOrThrow(_cursor, "primaryEmotion");
          final int _cursorIndexOfThemes = CursorUtil.getColumnIndexOrThrow(_cursor, "themes");
          final int _cursorIndexOfReflection = CursorUtil.getColumnIndexOrThrow(_cursor, "reflection");
          final int _cursorIndexOfConcernFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "concernFlag");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<JournalEntry> _result = new ArrayList<JournalEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final JournalEntry _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final Integer _tmpMoodScore;
            if (_cursor.isNull(_cursorIndexOfMoodScore)) {
              _tmpMoodScore = null;
            } else {
              _tmpMoodScore = _cursor.getInt(_cursorIndexOfMoodScore);
            }
            final String _tmpPrimaryEmotion;
            if (_cursor.isNull(_cursorIndexOfPrimaryEmotion)) {
              _tmpPrimaryEmotion = null;
            } else {
              _tmpPrimaryEmotion = _cursor.getString(_cursorIndexOfPrimaryEmotion);
            }
            final String _tmpThemes;
            if (_cursor.isNull(_cursorIndexOfThemes)) {
              _tmpThemes = null;
            } else {
              _tmpThemes = _cursor.getString(_cursorIndexOfThemes);
            }
            final String _tmpReflection;
            if (_cursor.isNull(_cursorIndexOfReflection)) {
              _tmpReflection = null;
            } else {
              _tmpReflection = _cursor.getString(_cursorIndexOfReflection);
            }
            final boolean _tmpConcernFlag;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfConcernFlag);
            _tmpConcernFlag = _tmp != 0;
            final AnalysisStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __converters.toStatus(_tmp_1);
            _item = new JournalEntry(_tmpId,_tmpText,_tmpTimestamp,_tmpMoodScore,_tmpPrimaryEmotion,_tmpThemes,_tmpReflection,_tmpConcernFlag,_tmpStatus);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getById(final long id, final Continuation<? super JournalEntry> $completion) {
    final String _sql = "SELECT * FROM journal_entries WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<JournalEntry>() {
      @Override
      @Nullable
      public JournalEntry call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfMoodScore = CursorUtil.getColumnIndexOrThrow(_cursor, "moodScore");
          final int _cursorIndexOfPrimaryEmotion = CursorUtil.getColumnIndexOrThrow(_cursor, "primaryEmotion");
          final int _cursorIndexOfThemes = CursorUtil.getColumnIndexOrThrow(_cursor, "themes");
          final int _cursorIndexOfReflection = CursorUtil.getColumnIndexOrThrow(_cursor, "reflection");
          final int _cursorIndexOfConcernFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "concernFlag");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final JournalEntry _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final Integer _tmpMoodScore;
            if (_cursor.isNull(_cursorIndexOfMoodScore)) {
              _tmpMoodScore = null;
            } else {
              _tmpMoodScore = _cursor.getInt(_cursorIndexOfMoodScore);
            }
            final String _tmpPrimaryEmotion;
            if (_cursor.isNull(_cursorIndexOfPrimaryEmotion)) {
              _tmpPrimaryEmotion = null;
            } else {
              _tmpPrimaryEmotion = _cursor.getString(_cursorIndexOfPrimaryEmotion);
            }
            final String _tmpThemes;
            if (_cursor.isNull(_cursorIndexOfThemes)) {
              _tmpThemes = null;
            } else {
              _tmpThemes = _cursor.getString(_cursorIndexOfThemes);
            }
            final String _tmpReflection;
            if (_cursor.isNull(_cursorIndexOfReflection)) {
              _tmpReflection = null;
            } else {
              _tmpReflection = _cursor.getString(_cursorIndexOfReflection);
            }
            final boolean _tmpConcernFlag;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfConcernFlag);
            _tmpConcernFlag = _tmp != 0;
            final AnalysisStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __converters.toStatus(_tmp_1);
            _result = new JournalEntry(_tmpId,_tmpText,_tmpTimestamp,_tmpMoodScore,_tmpPrimaryEmotion,_tmpThemes,_tmpReflection,_tmpConcernFlag,_tmpStatus);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getSince(final long since,
      final Continuation<? super List<JournalEntry>> $completion) {
    final String _sql = "SELECT * FROM journal_entries WHERE timestamp >= ? ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, since);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<JournalEntry>>() {
      @Override
      @NonNull
      public List<JournalEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfMoodScore = CursorUtil.getColumnIndexOrThrow(_cursor, "moodScore");
          final int _cursorIndexOfPrimaryEmotion = CursorUtil.getColumnIndexOrThrow(_cursor, "primaryEmotion");
          final int _cursorIndexOfThemes = CursorUtil.getColumnIndexOrThrow(_cursor, "themes");
          final int _cursorIndexOfReflection = CursorUtil.getColumnIndexOrThrow(_cursor, "reflection");
          final int _cursorIndexOfConcernFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "concernFlag");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<JournalEntry> _result = new ArrayList<JournalEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final JournalEntry _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final Integer _tmpMoodScore;
            if (_cursor.isNull(_cursorIndexOfMoodScore)) {
              _tmpMoodScore = null;
            } else {
              _tmpMoodScore = _cursor.getInt(_cursorIndexOfMoodScore);
            }
            final String _tmpPrimaryEmotion;
            if (_cursor.isNull(_cursorIndexOfPrimaryEmotion)) {
              _tmpPrimaryEmotion = null;
            } else {
              _tmpPrimaryEmotion = _cursor.getString(_cursorIndexOfPrimaryEmotion);
            }
            final String _tmpThemes;
            if (_cursor.isNull(_cursorIndexOfThemes)) {
              _tmpThemes = null;
            } else {
              _tmpThemes = _cursor.getString(_cursorIndexOfThemes);
            }
            final String _tmpReflection;
            if (_cursor.isNull(_cursorIndexOfReflection)) {
              _tmpReflection = null;
            } else {
              _tmpReflection = _cursor.getString(_cursorIndexOfReflection);
            }
            final boolean _tmpConcernFlag;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfConcernFlag);
            _tmpConcernFlag = _tmp != 0;
            final AnalysisStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __converters.toStatus(_tmp_1);
            _item = new JournalEntry(_tmpId,_tmpText,_tmpTimestamp,_tmpMoodScore,_tmpPrimaryEmotion,_tmpThemes,_tmpReflection,_tmpConcernFlag,_tmpStatus);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
