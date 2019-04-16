package com.example.mysearchengine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends Activity {

    EditText eText;
    Button btn;
    Button localbtn;
    TextView resultTextView;
    ProgressBar progressBar;

    private static final String TAG = "SearchEngineApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // GUI init
        eText = (EditText) findViewById(R.id.edittext);
        btn = (Button) findViewById(R.id.GoogleButton);
        localbtn = (Button) findViewById(R.id.LocalButton);
        resultTextView = (TextView) findViewById(R.id.textView1);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // Google Search button onClick
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {


                final String searchString = eText.getText().toString();
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

                Intent i = new Intent(MainActivity.this,ResultsActivity.class);
                i.putExtra("url",urlString);
                startActivity(i);
            }
        });

        //local search button on click
        localbtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                final String searchString = eText.getText().toString();
                Log.d(TAG, "Searching for : " + searchString);
                resultTextView.setText("Searching for : " + searchString);

                // hide keyboard
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                // looking for
                //String searchStringNoSpaces = searchString.replace(" ", "+");

                String urlString = "http://192.168.253.112/MyApi/Api.php";

                Intent i = new Intent(MainActivity.this,LocalSearchActivity.class);
                i.putExtra("url",urlString);
                startActivity(i);
            }
        });
    }
}
