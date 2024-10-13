package com.example.bookstoreapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstoreapp.provider.Book;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Book> data;

    public RecyclerViewAdapter () {
    }

    public void setData(List<Book> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CardView inflated as RecyclerView list item
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        String id = "ID: " + data.get(position).getBookID();
        String title = data.get(position).getTitle();
        String ISBN = "ISBN: " + data.get(position).getISBN();
        String author = "Author: " + data.get(position).getAuthor();
        String desc = data.get(position).getDescription();
        String price = "$" + data.get(position).getPrice();
        String bookPos = "No " + String.valueOf(position);

        holder.bookID.setText(id);
        holder.bookTitle.setText(title);
        holder.bookISBN.setText(ISBN);
        holder.bookAuthor.setText(author);
        holder.bookDesc.setText(desc);
        holder.bookPrice.setText(price);
        holder.bookPosition.setText(bookPos);
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        else
            return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView bookID;
        public TextView bookTitle;
        public TextView bookDesc;
        public TextView bookAuthor;
        public TextView bookISBN;
        public TextView bookPrice;
        public TextView bookPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            bookID = itemView.findViewById(R.id.cardID);
            bookTitle = itemView.findViewById(R.id.cardTitle);
            bookDesc = itemView.findViewById(R.id.cardDesc);
            bookAuthor = itemView.findViewById(R.id.cardAuthor);
            bookISBN = itemView.findViewById(R.id.cardISBN);
            bookPrice = itemView.findViewById(R.id.cardPrice);
            bookPosition = itemView.findViewById(R.id.cardPos);
        }
    }
}