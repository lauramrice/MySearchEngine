package com.example.mysearchengine;

public class Song {
    String title,artist;
    int year, rank;
    String lyrics;

    public Song(int rank, String title, String artist, int year, String lyrics) {
        this.rank = rank;
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.lyrics = lyrics;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}
