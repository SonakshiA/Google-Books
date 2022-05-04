package com.example.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.net.URL;

public class BookActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book1);

        Intent intent = getIntent();
        String REQUEST_URL = intent.getStringExtra("REQUEST_URL");
        BookDescription task = new BookDescription();
        task.execute(REQUEST_URL);
    }

    private class BookDescription extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            Intent intent = getIntent();
            // Passing Data to intent
            String index = intent.getStringExtra("index");
            String bookDescription = QueryUtils.getDescription(strings[0],Integer.parseInt(index));
            return bookDescription;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s==null){
                return;
            }else{
                TextView bookDescription = (TextView) findViewById(R.id.description);
                bookDescription.setText(s);
            }
        }
    }
}