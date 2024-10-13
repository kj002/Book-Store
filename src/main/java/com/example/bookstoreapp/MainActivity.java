package com.example.bookstoreapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookstoreapp.provider.Book;
import com.example.bookstoreapp.provider.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    TextView bookID;
    TextView bookTitle;
    TextView bookISBN;
    TextView bookAuthor;
    TextView bookDesc;
    TextView bookPrice;
    String bookIDStr;
    String bookTitleStr;
    String bookISBNstr;
    String bookAuthorStr;
    String bookDescStr;
    String bookPriceStr;
    DrawerLayout drawer;
    private BookViewModel myBookViewModel;
    private int dataSize;
    private int lastAddedId;
    DatabaseReference myRef;
    FrameLayout touch_frame;
    int x_down;
    int y_down;
    int x_up;
    int y_up;
    private GestureDetectorCompat mDetector;
    private ScaleGestureDetector mScaleDetector;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        // Initialising all plain texts
        bookID = findViewById(R.id.bookID);
        bookTitle = findViewById(R.id.bookTitle);
        bookISBN = findViewById(R.id.bookISBN);
        bookAuthor = findViewById(R.id.bookAuthor);
        bookDesc = findViewById(R.id.bookDescription);
        bookPrice = findViewById(R.id.bookPrice);

        // Request permissions to access SMS
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_SMS}, 0);

        // Create and instantiate the local broadcast receiver
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        // Register the broadcast handler with the intent filter
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));

        // TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar); // Get a reference to the toolbar
        setSupportActionBar(toolbar); // Set the toolbar as the app bar for the activity

        // ActionBarDrawerToggle
        drawer = findViewById(R.id.dl);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle); // Set the DrawerLayout's listener to the ActionBarDrawerToggle
        toggle.syncState(); // Synchronize the ActionBarDrawerToggle's state with the DrawerLayout

        // NAVIGATION VIEW
        NavigationView navigationView = findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        // FAB
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> addBook());

        // DATABASE
        myBookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        myBookViewModel.getAllBooks().observe(this, newData -> dataSize = newData.size());

        // FRAGMENT
        getSupportFragmentManager().beginTransaction().add(R.id.frame, new BooksFragment()).addToBackStack("f1").commit();

        // FIREBASE
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://bookstoreapp-4ffe6-default-rtdb.asia-southeast1.firebasedatabase.app");
        myRef = database.getReference("Book/myBook");

        // GESTURE DETECTOR
        mDetector = new GestureDetectorCompat(this, new MyGestureDetector());

        // TOUCH GESTURES
        touch_frame = findViewById(R.id.touch_frame);

        touch_frame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getActionMasked();
                mDetector.onTouchEvent(event);
                return true;

                /*
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        x_down = (int)event.getX();
                        y_down = (int)event.getY();

                         if (x_down <= 20 && y_down <= 20) {
                             bookAuthor.setText(bookAuthor.getText().toString().toUpperCase());
                         }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (Math.abs(y_down - event.getY()) < 20) {
                            if (x_down - event.getX() < -20) {
                                // LEFT TO RIGHT (lower X value first then higher)
                                int price = Integer.parseInt(bookPrice.getText().toString());
                                bookPrice.setText(String.valueOf(price + 1));
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(y_down - event.getY()) < 20) {
                            if (x_down - event.getX() > 0) {
                                // RIGHT TO LEFT (higher X value first then lower)
                                addBook();
                            }
                        } else if (Math.abs(x_down - event.getX()) < 20) {
                            if (y_down - event.getY() > 0) {
                                // BOTTOM TO TOP
                                clearFields();
                            } else if (y_down - event.getY() < 0) {
                                // TOP TO BOTTOM
                                finish();
                            }
                        }
                        return true;
                    default:
                        return false;
                } */
            }
        });


    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            bookISBN.setText(RandomString.generateNewRandomString(5));
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            clearFields();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int price = Integer.parseInt(bookPrice.getText().toString());
            if (distanceX < 0) {
                bookPrice.setText(String.valueOf((int)(price + 1))); // Increment
            } else {
                bookPrice.setText(String.valueOf((int)(price - 1))); // Decrement
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (velocityX > 1000) {
                moveTaskToBack(true);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            loadBook();
        }
    }

    class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Retrieve the message from the intent
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);

            // Parse the message
            StringTokenizer sT = new StringTokenizer(msg, "|");
            String bookIDStr = sT.nextToken();
            String bookTitleStr = sT.nextToken();
            String bookISBNStr = sT.nextToken();
            String bookAuthorStr = sT.nextToken();
            String bookDescStr = sT.nextToken();
            String bookPriceStr = sT.nextToken();
            String boolStr = sT.nextToken();

            boolean bool = Boolean.parseBoolean(boolStr);
            double price = Double.parseDouble(bookPriceStr);

            if (bool) {
                price += 100;
            }
            else {
                price += 5;
            }
            bookPriceStr = String.valueOf(price);

            bookID.setText(bookIDStr);
            bookTitle.setText(bookTitleStr);
            bookISBN.setText(bookISBNStr);
            bookAuthor.setText(bookAuthorStr);
            bookDesc.setText(bookDescStr);
            bookPrice.setText(bookPriceStr);
        }
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId(); // Get the id of the selected item

            if (id == R.id.addBook) {
                addBook();
            } else if (id == R.id.removeLast) {
                myBookViewModel.deleteBook(lastAddedId); // Delete the last added book in the database
            } else if (id == R.id.removeAll) {
                myBookViewModel.deleteAll(); // Delete all books in the database
                myRef.removeValue();
            } else if (id == R.id.listAll) {
                // Go to activity containing recycle view list of all books
                Intent intent = new Intent(MainActivity.this, BooksActivity.class);
                startActivity(intent);
            } else if (id == R.id.close) {
                finish(); // leave app
            }

            drawer.closeDrawers(); // Close the drawer
            return true; // Tell the OS
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId(); // Get users selection

        if (id == R.id.clearFields) {
            clearFields(); // Clear all fields
        } else if (id == R.id.loadData) {
            loadBook(); // Load book data
        } else if (id == R.id.totalBooks) {
            Toast.makeText(this,"Total books: " + dataSize, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.deleteUnknownAuthors) {
            myBookViewModel.deleteUnknownAuthor();
        }
        return true; // tell the OS
    }

    public void clearFields() {
        bookID.setText("");
        bookTitle.setText("");
        bookISBN.setText("");
        bookAuthor.setText("");
        bookDesc.setText("");
        bookPrice.setText("");
    }

    public void addBook() {
        SharedPreferences bookData = getPreferences( 0);
        SharedPreferences.Editor editor = bookData.edit();

        bookIDStr = bookID.getText().toString();
        bookTitleStr = bookTitle.getText().toString();
        bookISBNstr = bookISBN.getText().toString();
        bookAuthorStr = bookAuthor.getText().toString();
        bookDescStr = bookDesc.getText().toString();
        bookPriceStr = bookPrice.getText().toString();

        // Storing the data
        editor.putString("bookID", bookIDStr);
        editor.putString("bookTitle", bookTitleStr);
        editor.putString("bookISBN", bookISBNstr);
        editor.putString("bookAuthor", bookAuthorStr);
        editor.putString("bookDesc", bookDescStr);
        editor.putString("bookPrice", bookPriceStr);
        editor.apply();

        // Creating new book and adding to database
        Book book = new Book(bookIDStr, bookTitleStr, bookISBNstr, bookAuthorStr, bookDescStr, bookPriceStr);
        lastAddedId = book.getId();
        myBookViewModel.insert(book);

        // Adding to Firebase database
        myRef.push().setValue(book);

         // Showing toast confirmation message
        bookTitleStr = bookTitle.getText().toString();
        double price = Double.parseDouble(bookPrice.getText().toString());
        Toast.makeText(this, "Added book (" + bookTitleStr + ")"  + " Price ($" + price + ")",
                Toast.LENGTH_LONG).show();
    }

    public void loadBook() {
        SharedPreferences bookData = getPreferences( 0);

        // Retrieving and using the data appropriately
        bookID.setText(bookData.getString("bookID", ""));
        bookTitle.setText(bookData.getString("bookTitle", ""));
        bookISBN.setText(bookData.getString("bookISBN", ""));
        bookAuthor.setText(bookData.getString("bookAuthor", ""));
        bookDesc.setText(bookData.getString("bookDesc", ""));
        bookPrice.setText(bookData.getString("bookPrice", ""));
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences bookData = getPreferences( 0);

        // Retrieving and using the data appropriately
        bookID.setText(bookData.getString("bookID", ""));
        bookTitle.setText(bookData.getString("bookTitle", ""));
        bookISBN.setText(bookData.getString("bookISBN", ""));
        bookAuthor.setText(bookData.getString("bookAuthor", ""));
        bookDesc.setText(bookData.getString("bookDesc", ""));
        bookPrice.setText(bookData.getString("bookPrice", ""));
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences bookData = getPreferences( 0);
        SharedPreferences.Editor editor = bookData.edit();

        // Storing the data
        editor.putString("bookID", bookID.getText().toString());
        editor.putString("bookTitle", bookTitle.getText().toString());
        editor.putString("bookISBN", bookISBN.getText().toString());
        editor.putString("bookAuthor", bookAuthor.getText().toString());
        editor.putString("bookDesc", bookDesc.getText().toString());
        editor.putString("bookPrice", bookPrice.getText().toString());
        editor.apply();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // Storing the title and ISBN when orientation changes
        outState.putString("bookTitle", bookTitle.getText().toString());
        outState.putString("bookISBN", bookISBN.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // Retrieving the data of the title and ISBN
        bookTitleStr = savedInstanceState.getString("bookTitle");
        bookISBNstr = savedInstanceState.getString("bookISBN");

        // Keeping the title and ISBN the same as before
        bookTitle.setText(bookTitleStr);
        bookISBN.setText(bookISBNstr);
    }
}
