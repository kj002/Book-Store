package com.example.bookstoreapp.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BookViewModel extends AndroidViewModel {
    private BookRepository mReposity;
    private LiveData<List<Book>> allBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);
        mReposity = new BookRepository(application);
        allBooks = mReposity.getAllBooks();
    }

    public LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }

    public void insert(Book book) {
        mReposity.insert(book);
    }

    public void deleteBook(int id) {
        mReposity.deleteBook(id);
    }

    public void deleteAll() {
        mReposity.deleteAll();
    }

    public void deleteUnknownAuthor() {
        mReposity.deleteUnknownAuthor();
    }
}
