package com.example.books;

import org.json.JSONArray;

import java.util.ArrayList;

public class Book {
    private String bookName;
    private String publishedDate;
    private String pageCount;

    public Book(String bookName, String publishedDate, String pageCount){
        this.bookName = bookName;
        this.publishedDate = publishedDate;
        this.pageCount = pageCount;

    }

    public String getBookName(){
        return this.bookName;
    }

    public String getPublishedDate(){
        return this.publishedDate;
    }

    public String getPageCount(){
      return this.pageCount;
    }
}
