package com.example.books;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BooksAdapter extends ArrayAdapter<Book> {
    public BooksAdapter(@NonNull Context context, @NonNull List<Book> objects) {
        super(context, 0, objects);
    }

    private String pageNumberFormat(String pageNo){
        return pageNo + " pages";
    }

    private String publishedDateFormat(String publishedDate){
        String[] elements = publishedDate.split("-");
        String yearOfPublishing = elements[0];
        return yearOfPublishing;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Book currentBook = getItem(position);
        TextView bookNameView = (TextView) listItemView.findViewById(R.id.book_name);
        bookNameView.setText(currentBook.getBookName());

        TextView publishedDateView = (TextView) listItemView.findViewById(R.id.published_date);
        publishedDateView.setText(publishedDateFormat(currentBook.getPublishedDate()));

        if (currentBook.getPageCount()!=null || currentBook.getPageCount()!=" ") {
            TextView pageCountView = (TextView) listItemView.findViewById(R.id.page_count);
            pageCountView.setText(pageNumberFormat(currentBook.getPageCount()));
        } else{
            TextView pageCountView = (TextView) listItemView.findViewById(R.id.page_count);
            pageCountView.setVisibility(View.GONE);
        }

        return listItemView;
    }
}
