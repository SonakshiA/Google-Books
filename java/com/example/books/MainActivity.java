package com.example.books;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {


    private static final int HISTORY_LOADER_ID = 1;
    private BooksAdapter adapter;
    private static final String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?key=AIzaSyDCIb5QBPCpCCSsXTbDbtZMuKjQdR8Nq9A&q=android&maxResults=25";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new BooksAdapter(this, new ArrayList<Book>());
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0,null,this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent bookDescription = new Intent(MainActivity.this, BookActivity.class);

                    // passing data to an Intent
                    bookDescription.putExtra("index",String.valueOf(position));
                    bookDescription.putExtra("REQUEST_URL",REQUEST_URL);
                    startActivity(bookDescription);
            }
        });

    }

    public String getRequestUrl(){
        return REQUEST_URL;
    }
    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, @Nullable Bundle args) {
        return new BookLoader(this,REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> data) {
        adapter.clear();

        if(data!=null && !data.isEmpty()){
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {
        adapter.clear();
    }
}
