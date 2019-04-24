package com.example.mysearchengine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SongListAdapter extends ArrayAdapter{


        List list = new ArrayList();

        public SongListAdapter(Context context, int resource){
            super(context,resource);
        }

        public void add(Song object){
            super.add(object);
            list.add(object);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row;
            row = convertView;
            SongHolder songHolder;

            if(row == null){
                LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.row_layout, parent, false);
                songHolder = new SongHolder();
                songHolder.tx_rank = (TextView)row.findViewById(R.id.tx_rank);
                songHolder.tx_title = (TextView) row.findViewById(R.id.tx_title);
                songHolder.tx_artist = (TextView)row.findViewById(R.id.tx_artist);
                songHolder.tx_year = (TextView)row.findViewById(R.id.tx_year);
                songHolder.tx_lyrics = (TextView)row.findViewById(R.id.tx_lyrics);
                row.setTag(songHolder);


            }
            else{
                songHolder = (SongHolder) row.getTag();
            }
            Song song = (Song) this.getItem(position);

            songHolder.tx_rank.setText(String.valueOf(song.getRank()));
            songHolder.tx_title.setText(song.getTitle());
            songHolder.tx_artist.setText(song.getArtist());
            songHolder.tx_lyrics.setText(song.getLyrics());
            songHolder.tx_year.setText(String.valueOf(song.getYear()));
            return row;
        }

        static class SongHolder{
            TextView tx_rank, tx_title, tx_artist, tx_year, tx_lyrics;
        }

}
