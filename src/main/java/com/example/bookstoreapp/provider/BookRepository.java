package com.example.bookstoreapp.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BookRepository {
    private BookDAO mBookDao;
    private LiveData<List<Book>> allBooks;

    BookRepository(Application application) {
        BookDatabase db = BookDatabase.getDatabase(application);
        mBookDao = db.bookDao();
        allBooks = mBookDao.getAllBook();
    }

    LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }

    void insert(Book book) {
        BookDatabase.databaseWriteExecutor.execute(() -> mBookDao.addBook(book));
    }

    void deleteBook(int id) {
        BookDatabase.databaseWriteExecutor.execute(() -> {
            mBookDao.deleteBook(id);
        });
    }

    void deleteAll() {
        BookDatabase.databaseWriteExecutor.execute(() -> {
            mBookDao.deleteAllBooks();
        });
    }

    void deleteUnknownAuthor() {
        BookDatabase.databaseWriteExecutor.execute(() -> {
            mBookDao.deleteUnknownAuthor();
        });
    }
}
