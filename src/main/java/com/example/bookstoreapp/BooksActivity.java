package com.example.bookstoreapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        getSupportFragmentManager().beginTransaction().add(R.id.frame2, new BooksFragment()).addToBackStack("f1").commit();
    }
}