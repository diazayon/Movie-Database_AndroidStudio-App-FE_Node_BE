package com.example.hw9_final;

public class reviewData {
    private String author;
    private String rating;
    private String content;

    public reviewData() {

    }

    public reviewData(String author, String rating, String content) {
        this.author = author;
        this.rating = rating;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
