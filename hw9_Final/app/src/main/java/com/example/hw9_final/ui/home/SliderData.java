package com.example.hw9_final.ui.home;

public class SliderData {
    // image url is used to
    // store the url of image
    private String imgUrl;
    private String id;
    private String contenttype;

    public SliderData(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    // Constructor method.
    public SliderData(String imgUrl, String id, String contenttype) {

        this.imgUrl = imgUrl;
        this.id = id;
        this.contenttype = contenttype;
    }

    // Getter method
    public String getImgUrl() {
        return imgUrl;
    }

    // Setter method
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
