package com.example.bookstoreapp.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "bookID")
    private String bookID;
    @ColumnInfo(name = "bookTitle")
    private String title;
    @ColumnInfo(name = "bookISBN")
    private String ISBN;
    @ColumnInfo(name = "bookAuthor")
    private String author;
    @ColumnInfo(name = "bookDescription")
    private String description;
    @ColumnInfo(name = "bookPrice")
    private String price;

    public Book() {
        // Empty constructor required by Room.
    }

    public Book(String bookID, String bookTitle, String bookISBN, String bookAuthor, String bookDescription, String bookPrice) {
        this.bookID = bookID;
        this.title = bookTitle;
        this.ISBN = bookISBN;
        this.author = bookAuthor;
        this.description = bookDescription;
        this.price = bookPrice;
    }

    public int getId() {
        return id;
    }

    public String getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setBookID(String ID) {
        this.bookID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
