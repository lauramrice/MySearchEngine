package com.example.mysearchengine;

public class ResultsModel {

    String title;
    String snippet;
    String link;

    public ResultsModel(String title, String snippet, String link){
        this.setTitle(title);
        this.setSnippet(snippet);
        this.setLink(link);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
