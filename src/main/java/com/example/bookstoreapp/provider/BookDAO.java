package com.example.bookstoreapp.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDAO {
    @Query("select * from books")
    LiveData<List<Book>> getAllBook();

    @Query("select * from books where bookID = :bookID")
    List<Book> getBook(String bookID);

    @Insert
    void addBook(Book book);

    @Query("delete from books where id = :id")
    void deleteBook(int id);

    @Query("delete from books")
    void deleteAllBooks();

    @Query("delete from books where bookAuthor = 'unknown'")
    void deleteUnknownAuthor();
}
