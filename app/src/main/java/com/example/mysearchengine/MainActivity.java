package com.example.mysearchengine;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.textclassifier.TextLinks;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends Activity {

    EditText searchQueryText;
    TextView results;
    Button btn;
    Button localbtn;
    TextView resultTextView;
    ProgressBar progressBar;
    private ArrayList<Song> mPosts;
    private RecyclerView mRecyclerView;
    private SongListAdapter mAdapter;

    public static String result = null;
    Integer responseCode = null;
    String responseMessage = "";
    String json_string;
    String queryData;

    private static final String TAG = "SearchFragment";
    private static final String BASE_URL = "http://10.0.2.2:9200/songs/";

    //vars
    private ArrayList<Song> mSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // GUI init
        searchQueryText = (EditText) findViewById(R.id.edittext);
        btn = (Button) findViewById(R.id.GoogleButton);
        localbtn = (Button) findViewById(R.id.LocalButton);
        resultTextView = (TextView) findViewById(R.id.textView1);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // Google Search button onClick
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {


                final String searchString = searchQueryText.getText().toString();
                Log.d(TAG, "Searching for : " + searchString);
                resultTextView.setText("Searching for : " + searchString);

                // hide keyboard
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                // looking for
                String searchStringNoSpaces = searchString.replace(" ", "+");

                // Google Search API key
                String key="AIzaSyC1xDN5blvcznnEWAI7whKsqTBQzcSFK7I";;

                //Search Engine ID
                String cx = "011866588680669076246:-98upx2nk0a";

                String urlString = "https://www.googleapis.com/customsearch/v1?q=" + searchStringNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json";
                URL url = null;
                try {
                    url = new URL(urlString);
                } catch (MalformedURLException e) {
                    Log.e(TAG, "ERROR converting String to URL " + e.toString());
                }
                Log.d(TAG, "Url = "+  urlString);

                MainActivity.GoogleSearchAsyncTask searchTask = new MainActivity.GoogleSearchAsyncTask();
                searchTask.execute(url);
            }
        });

        //local search button on click
        localbtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                final String searchQuery = searchQueryText.getText().toString();

                Log.d(TAG, "Searching for : " + searchQuery);
                resultTextView.setText("Searching for : " + searchQuery);

                // hide keyboard
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                /*
                mSongs = new ArrayList<Song>();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ElasticSearchAPI searchAPI = retrofit.create(ElasticSearchAPI.class);

                HashMap<String, String> headerMap = new HashMap<String, String>();

                String searchString = "";

                if(!searchQueryText.equals("")){
                    searchString = searchString + searchQueryText.getText().toString() + "*";
                }

                Call<HitsObject> call = searchAPI.search(headerMap, "AND", searchString);

                call.enqueue(new Callback<HitsObject>() {
                    @Override
                    public void onResponse(Call<HitsObject> call, Response<HitsObject> response) {

                        HitsList hitsList = new HitsList();
                        String jsonResponse = "";

                        try {
                            Log.d(TAG, "onResponse: server response: " + response.toString());
                            if (response.isSuccessful()) {
                                hitsList = response.body().getHits();
                            } else {
                                jsonResponse = response.errorBody().string();
                            }

                            Log.d(TAG, "onResponse: hits: " + hitsList);

                            for (int i = 0; i < hitsList.getSongIndex().size(); i++) {
                                Log.d(TAG, "onResponse: data: " + hitsList.getSongIndex().get(i).getSong().toString());
                                mSongs.add(hitsList.getSongIndex().get(i).getSong());
                            }

                            for (int i = 0; i < mSongs.size(); i++) {
                                Log.d(TAG, "mSongs Data: " + mSongs.get(i).toString());
                            }

                            Log.d(TAG, "onResponse: size: " + mSongs.size());
                            //setup the list of posts

                        } catch (NullPointerException e) {
                            Log.e(TAG, "onResponse: NullPointerException: " + e.getMessage());
                        } catch (IndexOutOfBoundsException e) {
                            Log.e(TAG, "onResponse: IndexOutOfBoundsException: " + e.getMessage());
                        } catch (IOException e) {
                            Log.e(TAG, "onResponse: IOException: " + e.getMessage());
                        }

                        //Intent intent = new Intent(MainActivity.this, DisplaySongInfo.class);
                        //intent.putExtra("query_data", elasticResponse);
                        //startActivity(intent);
                        for(Song song : mSongs){
                            System.out.println(song.rank);
                        }
                    }

                    @Override
                    public void onFailure(Call<HitsObject> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage() );
                    }

                });*/

                // URL objects
                String urlString = "http://10.0.2.2:9200/songs/_search?q=" + searchQuery;
                URL url = null;
                try {
                    url = new URL(urlString);
                } catch (MalformedURLException e) {
                    Log.e(TAG, "ERROR converting String to URL " + e.toString());
                }
                Log.d(TAG, "Url = "+  urlString);


            // get a JSON object from ElasticSearch


                 MainActivity.LocalSearchAsyncTask searchTask = new MainActivity.LocalSearchAsyncTask();
                 searchTask.execute(url);
            }
        });
    }


    class GoogleSearchAsyncTask extends AsyncTask<URL, Integer, String> {

        String JSON_STRING;
        protected void onPreExecute(){
            Log.d(TAG, "AsyncTask - onPreExecute");
            // show progressbar
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(URL... urls) {

            URL url = urls[0];
            Log.d(TAG, "AsyncTask - doInBackground, url=" + url);

            // Http connection
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                Log.e(TAG, "Http connection ERROR " + e.toString());
            }


            try {
                responseCode = conn.getResponseCode();
                responseMessage = conn.getResponseMessage();
            } catch (IOException e) {
                Log.e(TAG, "Http getting response code ERROR " + e.toString());
            }

            Log.d(TAG, "Http response code =" + responseCode + " message=" + responseMessage);

            try {

                if(responseCode == 200) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(JSON_STRING+"\n");
                }

                bufferedReader.close();

                return stringBuilder.toString().trim();

                }else{

                    // response problem
                    String errorMsg = "Http ERROR response " + responseMessage + "\n";
                    Log.e(TAG, errorMsg);
                    result = errorMsg;
                    return result;

                }
            } catch (IOException e) {
                Log.e(TAG, "Http Response ERROR " + e.toString());
            }

            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            Log.d(TAG, "AsyncTask - onProgressUpdate, progress=" + progress);

        }

        protected void onPostExecute(String result) {

            Log.d(TAG, "AsyncTask - onPostExecute, result=" + result);

            // hide progressbar
            progressBar.setVisibility(View.GONE);

            // make TextView scrollable
            resultTextView.setMovementMethod(new ScrollingMovementMethod());

            // show result
            //resultTextView.setText(result);
            json_string = result;
            parseJSON(resultTextView);
        }
    }

    class LocalSearchAsyncTask extends AsyncTask<URL, Integer, String> {

        String JSON_STRING;
        protected void onPreExecute(){
            Log.d(TAG, "AsyncTask - onPreExecute");
            // show progressbar
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(URL... urls) {

            URL url = urls[0];
            Log.d(TAG, "AsyncTask - doInBackground, url=" + url);

            // Http connection
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                Log.e(TAG, "Http connection ERROR " + e.toString());
            }


            try {
                responseCode = conn.getResponseCode();
                responseMessage = conn.getResponseMessage();
            } catch (IOException e) {
                Log.e(TAG, "Http getting response code ERROR " + e.toString());
            }

            Log.d(TAG, "Http response code =" + responseCode + " message=" + responseMessage);

            try {

                if(responseCode == 200) {

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((JSON_STRING = bufferedReader.readLine()) != null)
                    {
                        stringBuilder.append(JSON_STRING+"\n");
                    }

                    bufferedReader.close();

                    return stringBuilder.toString().trim();

                }else{

                    // response problem
                    String errorMsg = "Http ERROR response " + responseMessage + "\n";
                    Log.e(TAG, errorMsg);
                    result = errorMsg;
                    return result;

                }
            } catch (IOException e) {
                Log.e(TAG, "Http Response ERROR " + e.toString());
            }

            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            Log.d(TAG, "AsyncTask - onProgressUpdate, progress=" + progress);

        }

        protected void onPostExecute(String result) {

            Log.d(TAG, "AsyncTask - onPostExecute, result=" + result);

            // hide progressbar
            progressBar.setVisibility(View.GONE);

            // make TextView scrollable
            resultTextView.setMovementMethod(new ScrollingMovementMethod());

            // show result
            //resultTextView.setText(result);
            json_string = result;
            parseElasticJSON(resultTextView);
        }
    }

    public void parseJSON(View view){

        if(json_string == null){

            Toast.makeText(getApplicationContext(), "First", Toast.LENGTH_LONG);
        }
        else{

            Intent intent = new Intent(MainActivity.this, DisplayListActivity.class);
            intent.putExtra("json_data",json_string);
            Log.d(TAG, "json_string:" + json_string);
            startActivity(intent);

        }
    }


    public void parseElasticJSON(View view){

        if(json_string == null){

            Toast.makeText(getApplicationContext(), "First", Toast.LENGTH_LONG);
        }
        else{

            Intent intent = new Intent(MainActivity.this, DisplaySongInfo.class);
            intent.putExtra("query_data",json_string);
            Log.d(TAG, "query_data:" + json_string);
            startActivity(intent);

        }
    }

}
