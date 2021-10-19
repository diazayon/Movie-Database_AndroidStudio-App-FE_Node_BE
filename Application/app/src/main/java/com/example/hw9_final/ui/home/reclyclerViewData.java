package com.example.hw9_final.ui.home;

public class reclyclerViewData {

    private String id;
    private String url;
    private String contenttype;
    private String rating;
    private String name;
    private String date;

    public reclyclerViewData(){

    }

    public reclyclerViewData(String id, String url, String contenttype, String rating, String name, String date) {
        this.id = id;
        this.url = url;
        this.contenttype = contenttype;
        this.rating = rating;
        this.name = name;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
       this.contenttype = contenttype;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
