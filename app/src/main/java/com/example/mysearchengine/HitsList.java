package com.example.mysearchengine;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@IgnoreExtraProperties
public class HitsList {

    @SerializedName("hits")
    @Expose
    private List<SongsSource> songIndex;

    public List<SongsSource> getSongIndex() {
        return songIndex;
    }

    public void setSongIndex(List<SongsSource> songIndex) {
        this.songIndex = songIndex;
    }
}
