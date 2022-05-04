package com.example.books;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static URL createURL(String stringURL){
        URL url = null;
        try{
            url = new URL(stringURL);
        }catch(MalformedURLException e){
            Log.e("URL not formed",e.getMessage());
        }
        return url;
    }

    private static String MakeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        }catch(IOException e){
            Log.e("Error fetching JSON",e.getMessage());
        }finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            } if (inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream!=null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Book> extractBooks(String json){
        if (TextUtils.isEmpty(json)){
            return null;
        } else{

            List<Book> books = new ArrayList<>();
            try{
                JSONObject baseJsonObject = new JSONObject(json);
                JSONArray itemsArray = baseJsonObject.optJSONArray("items");

                for(int i=0; i<itemsArray.length();i++){
                    JSONObject jsonObject =  itemsArray.optJSONObject(i);
                    JSONObject jsonObject2 = jsonObject.optJSONObject("volumeInfo");
                    String bookName = jsonObject2.optString("title");
                    String publishedDate = jsonObject2.optString("publishedDate");
                    String pageCount = jsonObject2.optString("pageCount");

                    Book book = new Book(bookName,publishedDate,pageCount);
                    books.add(book);
                }
            }catch(JSONException e){
                Log.e("JSON not fetched",e.getMessage());
            }
            return books;
        }
    }

    public static List<Book> fetchCollection(String requestURL){
        URL url = createURL(requestURL);
        String jsonResponse = null;
        try{
            jsonResponse = MakeHttpRequest(url);
        }catch(IOException e){
            Log.e("Couldn't fetch JSON", e.getMessage());
        }
        List<Book> books = extractBooks(jsonResponse);
        return books;
    }

    public static String getDescription(String requestURL, int index){
        String jsonResponse = "";
        String bookDescription = "";
        URL url = createURL(requestURL);
        try{
            jsonResponse = MakeHttpRequest(url);

        }catch(IOException e){
            Log.e("Couldn't fetch JSON", e.getMessage());
        }try{
            JSONObject baseJsonObject = new JSONObject(jsonResponse);
            JSONArray itemsArray = baseJsonObject.optJSONArray("items");
            JSONObject jsonObject =  itemsArray.optJSONObject(index);
            JSONObject jsonObject2 = jsonObject.optJSONObject("volumeInfo");
            bookDescription = jsonObject2.optString("description");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return bookDescription;
    }
}
