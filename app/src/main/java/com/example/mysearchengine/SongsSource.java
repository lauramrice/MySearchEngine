package com.example.mysearchengine;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@IgnoreExtraProperties
public class SongsSource {

    @SerializedName("_source")
    @Expose

    private Song song;

    public Song getSong(){
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
