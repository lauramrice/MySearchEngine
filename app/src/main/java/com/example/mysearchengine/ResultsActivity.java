package com.example.mysearchengine;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ResultsActivity extends AppCompatActivity {

    EditText eText;
    Button btn;
    TextView resultTextView;
    ProgressBar progressBar;

    private static final String TAG = "SearchEngineApp";
    public static String result = null;
    Integer responseCode = null;
    String responseMessage = "";
    String json_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // GUI init
        eText = (EditText) findViewById(R.id.edittext);
        btn = (Button) findViewById(R.id.GoogleButton);
        resultTextView = (TextView) findViewById(R.id.textView1);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        btn.setOnClickListener(new View.OnClickListener() {
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

                Intent i = new Intent(ResultsActivity.this,ResultsActivity.class);
                i.putExtra("url",urlString);
                startActivity(i);
            }
        });

        Intent i = getIntent();
        String urlString = i.getStringExtra("url");

        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(TAG, "ERROR converting String to URL " + e.toString());
        }
        Log.d(TAG, "Url = "+  urlString);

        ResultsActivity.GoogleSearchAsyncTask searchTask = new ResultsActivity.GoogleSearchAsyncTask();
        searchTask.execute(url);

    }

    class GoogleSearchAsyncTask extends AsyncTask<URL, Integer, String> {

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
                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = bufferedReader.readLine()) != null){
                        buffer.append(line);
                    }

                    String finalJson = buffer.toString();

                    JSONObject JO = new JSONObject(finalJson);
                    JSONArray JA = JO.getJSONArray("items");

                    StringBuffer finalBufferedData = new StringBuffer();
                    for(int i = 0; i < JA.length();i++){
                        JSONObject finalJO = JA.getJSONObject(i);

                        String title = finalJO.getString("title");
                        String snippet = finalJO.getString("htmlSnippet");
                        String link = finalJO.getString("link");
                        finalBufferedData.append(title + "\n" + link + "\n" + snippet + "\n\n");
                    }

                    Log.d(TAG, "result=" + result);

                    return finalBufferedData.toString();

                }else{

                    // response problem
                    String errorMsg = "Http ERROR response " + responseMessage + "\n";
                    Log.e(TAG, errorMsg);
                    result = errorMsg;
                    return result;

                }
            } catch (IOException e) {
                Log.e(TAG, "Http Response ERROR " + e.toString());
            } catch (JSONException e) {
                e.printStackTrace();
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
            resultTextView.setText(result);
            json_string = result;
            parseJSON(resultTextView);
        }
    }

    public void parseJSON(View view){

        if(json_string == null){

        }
        else{

            Intent intent = new Intent(this, DisplayListActivity.class);
            intent.putExtra("json_data",json_string);
            startActivity(intent);

        }
    }
}