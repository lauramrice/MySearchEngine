package com.example.mysearchengine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DisplaySongInfo extends AppCompatActivity {

    String query;
    SongListAdapter songListAdapter;
    ListView listView;
    ProgressBar progressBar;

    Button googleBtn;
    Button localBtn;
    TextView eText;
    TextView resultTextView;

    private static final String TAG = "SearchEngineApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_song_info);
        listView = (ListView) findViewById(R.id.songlistview);

        songListAdapter = new SongListAdapter(this, R.layout.song_row_layout);
        listView.setAdapter(songListAdapter);

        // GUI init
        eText = (EditText) findViewById(R.id.edittext);
        googleBtn = (Button) findViewById(R.id.GoogleButton);
        localBtn = (Button) findViewById(R.id.LocalButton);
        resultTextView = (TextView) findViewById(R.id.textView1);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        resultTextView = (TextView) findViewById(R.id.textView2);


        // The input stream from the JSON response
        BufferedInputStream buffer = null;

        // URL objects
        String url = "";
        URL urlObject = null;
        URLConnection con = null;
        String response = getIntent().getExtras().getString("query_data");

        // JSON objects
        JSONArray hitsArray = null;
        JSONObject hits = null;
        JSONObject source = null;
        JSONObject json = null;
        String artist, title, lyrics;
        int year, rank;

        try {
            /*
            // get a JSON object from ElasticSearch
            url = "http://10.0.2.2:9200/songs/_search?q=" + query;

            // configure the URL request
            urlObject = new URL(url);
            con = urlObject.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            buffer = new BufferedInputStream(con.getInputStream());

            while (buffer.available()>0) {
                response += (char)buffer.read();
            }

            buffer.close();*/

            // parse the JSON response
            json = new JSONObject(response);
            hits = json.getJSONObject("hits");
            hitsArray = hits.getJSONArray("hits");

            StringBuffer finalBufferedData = new StringBuffer();
            for (int i=0; i<hitsArray.length(); i++) {
                JSONObject h = hitsArray.getJSONObject(i);
                source = h.getJSONObject("_source");
                rank = (source.getInt("Rank"));
                title = (source.getString("Song"));
                artist = (source.getString("Artist"));
                year = (source.getInt("Year"));
                lyrics = (source.getString("Lyrics"));



                finalBufferedData.append(title + " - " + artist + "\n");
                //string object = (source.getString("the string you want to get"));
                //final Song song = new Song(rank,title,artist,year,lyrics);
               //songListAdapter.add(song);
            }

            Log.d(TAG, "finalBufferdData" + finalBufferedData.toString());



            resultTextView.setText(finalBufferedData.toString());
        } catch(Exception e) {
            e.printStackTrace();
        }



    }
}
