package com.example.bookstoreapp.provider;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class BookDAO_Impl implements BookDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Book> __insertionAdapterOfBook;

  private final SharedSQLiteStatement __preparedStmtOfDeleteBook;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllBooks;

  private final SharedSQLiteStatement __preparedStmtOfDeleteUnknownAuthor;

  public BookDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBook = new EntityInsertionAdapter<Book>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `books` (`id`,`bookID`,`bookTitle`,`bookISBN`,`bookAuthor`,`bookDescription`,`bookPrice`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Book value) {
        stmt.bindLong(1, value.getId());
        if (value.getBookID() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getBookID());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTitle());
        }
        if (value.getISBN() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getISBN());
        }
        if (value.getAuthor() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAuthor());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDescription());
        }
        if (value.getPrice() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getPrice());
        }
      }
    };
    this.__preparedStmtOfDeleteBook = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from books where id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllBooks = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from books";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteUnknownAuthor = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from books where bookAuthor = 'unknown'";
        return _query;
      }
    };
  }

  @Override
  public void addBook(final Book book) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfBook.insert(book);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteBook(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteBook.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteBook.release(_stmt);
    }
  }

  @Override
  public void deleteAllBooks() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllBooks.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllBooks.release(_stmt);
    }
  }

  @Override
  public void deleteUnknownAuthor() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteUnknownAuthor.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteUnknownAuthor.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Book>> getAllBook() {
    final String _sql = "select * from books";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"books"}, false, new Callable<List<Book>>() {
      @Override
      public List<Book> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookID = CursorUtil.getColumnIndexOrThrow(_cursor, "bookID");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "bookTitle");
          final int _cursorIndexOfISBN = CursorUtil.getColumnIndexOrThrow(_cursor, "bookISBN");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "bookAuthor");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "bookDescription");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "bookPrice");
          final List<Book> _result = new ArrayList<Book>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Book _item;
            _item = new Book();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpBookID;
            if (_cursor.isNull(_cursorIndexOfBookID)) {
              _tmpBookID = null;
            } else {
              _tmpBookID = _cursor.getString(_cursorIndexOfBookID);
            }
            _item.setBookID(_tmpBookID);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpISBN;
            if (_cursor.isNull(_cursorIndexOfISBN)) {
              _tmpISBN = null;
            } else {
              _tmpISBN = _cursor.getString(_cursorIndexOfISBN);
            }
            _item.setISBN(_tmpISBN);
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            _item.setAuthor(_tmpAuthor);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final String _tmpPrice;
            if (_cursor.isNull(_cursorIndexOfPrice)) {
              _tmpPrice = null;
            } else {
              _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
            }
            _item.setPrice(_tmpPrice);
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
  public List<Book> getBook(final String bookID) {
    final String _sql = "select * from books where bookID = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (bookID == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, bookID);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBookID = CursorUtil.getColumnIndexOrThrow(_cursor, "bookID");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "bookTitle");
      final int _cursorIndexOfISBN = CursorUtil.getColumnIndexOrThrow(_cursor, "bookISBN");
      final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "bookAuthor");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "bookDescription");
      final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "bookPrice");
      final List<Book> _result = new ArrayList<Book>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Book _item;
        _item = new Book();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpBookID;
        if (_cursor.isNull(_cursorIndexOfBookID)) {
          _tmpBookID = null;
        } else {
          _tmpBookID = _cursor.getString(_cursorIndexOfBookID);
        }
        _item.setBookID(_tmpBookID);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _item.setTitle(_tmpTitle);
        final String _tmpISBN;
        if (_cursor.isNull(_cursorIndexOfISBN)) {
          _tmpISBN = null;
        } else {
          _tmpISBN = _cursor.getString(_cursorIndexOfISBN);
        }
        _item.setISBN(_tmpISBN);
        final String _tmpAuthor;
        if (_cursor.isNull(_cursorIndexOfAuthor)) {
          _tmpAuthor = null;
        } else {
          _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
        }
        _item.setAuthor(_tmpAuthor);
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        _item.setDescription(_tmpDescription);
        final String _tmpPrice;
        if (_cursor.isNull(_cursorIndexOfPrice)) {
          _tmpPrice = null;
        } else {
          _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
        }
        _item.setPrice(_tmpPrice);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
