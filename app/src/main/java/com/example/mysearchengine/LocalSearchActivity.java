package com.example.mysearchengine;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LocalSearchActivity extends AppCompatActivity {

    EditText eText;
    Button btn;
    TextView resultTextView;
    ProgressBar progressBar;
    EditText mSearchField;


    private static final String TAG = "SearchEngineApp";
    private static final String BASE_URL = "http://10.0.2.2:8080/SearchApp/";
    public static String result = null;
    Integer responseCode = null;
    String responseMessage = "";

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


        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String searchText = mSearchField.getText().toString();

                final String searchString = eText.getText().toString();
                Log.d(TAG, "Searching for : " + searchString);
                resultTextView.setText("Searching for : " + searchString);

                // hide keyboard
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                // looking for
                String searchStringNoSpaces = searchString.replace(" ", "+");

                String urlString = BASE_URL + "fetch_data.php";

                Intent i = new Intent(LocalSearchActivity.this,LocalSearchActivity.class);
                i.putExtra("url",urlString);
                startActivity(i);
            }
        });

    }
}