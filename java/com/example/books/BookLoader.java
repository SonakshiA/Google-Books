package com.example.books;
import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>>{

    private String url;
    /**
     * @param context
     * @deprecated
     */
    public BookLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (this.url==null){
            return null;
        } else{
            List<Book> books = QueryUtils.fetchCollection(this.url);
            return books;
        }
    }

}
