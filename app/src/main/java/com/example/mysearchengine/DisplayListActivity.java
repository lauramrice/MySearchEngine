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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class DisplayListActivity extends AppCompatActivity {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ResultsAdapter resultsAdapter;
    ListView listView;

    ProgressBar progressBar;
    public static String result = null;
    Integer responseCode = null;
    String responseMessage = "";

    Button googleBtn;
    Button localBtn;
    TextView eText;
    TextView resultTextView;

    private static final String TAG = "SearchEngineApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_listview_layout);
        listView = (ListView) findViewById(R.id.listview);

        resultsAdapter = new ResultsAdapter(this, R.layout.row_layout);
        listView.setAdapter(resultsAdapter);
        json_string = getIntent().getExtras().getString("json_data");


        // GUI init
        eText = (EditText) findViewById(R.id.edittext);
        googleBtn = (Button) findViewById(R.id.GoogleButton);
        localBtn = (Button) findViewById(R.id.LocalButton);
        resultTextView = (TextView) findViewById(R.id.textView1);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("items");
            int count = 0;
            String title, link, snippet;

            while (count < jsonArray.length()) {

                JSONObject JO = jsonArray.getJSONObject(count);
                title = JO.getString("title");
                snippet = JO.getString("snippet");
                link = JO.getString("link");

                final ResultsModel resultsModel = new ResultsModel(title, snippet, link);
                resultsAdapter.add(resultsModel);
                count++;

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,View view,int position,long id)
                    {
                        String textview =((TextView)view.findViewById(R.id.tx_link)).getText().toString();
                    }
                });

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}