package com.example.mysearchengine;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocalSearchActivity extends AppCompatActivity {

    EditText eText;
    Button btn;
    TextView resultTextView;
    ProgressBar progressBar;
    EditText mSearchField;
    //widgets
    private EditText mSearchText;

    private static final String TAG = "SearchFragment";
    private static final String BASE_URL = "http://localhost:9200/songs";

    //vars
    private ArrayList<Song> mSongs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_search);

        // GUI init
        eText = (EditText) findViewById(R.id.edittext);
        btn = (Button) findViewById(R.id.GoogleButton);
        resultTextView = (TextView) findViewById(R.id.textView1);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mSearchField = (EditText) findViewById(R.id.edittext);

        mSearchText = (EditText) findViewById(R.id.edittext);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mSongs = new ArrayList<Song>();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ElasticSearchAPI searchAPI = retrofit.create(ElasticSearchAPI.class);

                HashMap<String, String> headerMap = new HashMap<String, String>();

                String searchString = "";

                if(!mSearchText.equals("")){
                    searchString = searchString + mSearchText.getText().toString() + "*";
                }

                Call<HitsObject> call = searchAPI.search(headerMap, "AND", searchString);

                call.enqueue(new Callback<HitsObject>() {
                    @Override
                    public void onResponse(Call<HitsObject> call, Response<HitsObject> response) {

                        HitsList hitsList = new HitsList();
                        String jsonResponse = "";
                        try{
                            Log.d(TAG, "onResponse: server response: " + response.toString());

                            if(response.isSuccessful()){
                                hitsList = response.body().getHits();
                            }else{
                                jsonResponse = response.errorBody().string();
                            }

                            Log.d(TAG, "onResponse: hits: " + hitsList);

                            for(int i = 0; i < hitsList.getSongIndex().size(); i++){
                                Log.d(TAG, "onResponse: data: " + hitsList.getSongIndex().get(i).getSong().toString());
                                mSongs.add(hitsList.getSongIndex().get(i).getSong());
                            }

                            Log.d(TAG, "onResponse: size: " + mSongs.size());
                            //setup the list of posts

                        }catch (NullPointerException e){
                            Log.e(TAG, "onResponse: NullPointerException: " + e.getMessage() );
                        }
                        catch (IndexOutOfBoundsException e){
                            Log.e(TAG, "onResponse: IndexOutOfBoundsException: " + e.getMessage() );
                        }
                        catch (IOException e){
                            Log.e(TAG, "onResponse: IOException: " + e.getMessage() );
                        }
                    }

                    @Override
                    public void onFailure(Call<HitsObject> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage() );
                    }
                });


            }
        });


    }




}
