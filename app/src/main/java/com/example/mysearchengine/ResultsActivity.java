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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // GUI init
        eText = (EditText) findViewById(R.id.edittext);
        btn = (Button) findViewById(R.id.GoogleButton);
        resultTextView = (TextView) findViewById(R.id.textView1);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

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
                        String snippet = finalJO.getString("snippet");
                        String link = finalJO.getString("link");
                        finalBufferedData.append(title + "\n" + snippet + "\n" + link + "\n\n");
                    }

                    Log.d(TAG, "result=" + result);

                    return finalBufferedData.toString();

                }else{

                    // response problem
                    String errorMsg = "Http ERROR response " + responseMessage + "\n" + "Make sure to replace in code your own Google API key and Search Engine ID";
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
        }
    }
}
