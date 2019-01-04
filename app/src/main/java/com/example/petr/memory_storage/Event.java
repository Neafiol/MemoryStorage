package com.example.petr.memory_storage;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {
    @SerializedName("title")
    @Expose
    private  String title;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("photosurl")
    @Expose
    private String photosurl;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private  Date date ;

    public Event(String title, String text, String photosurl, Integer id, String username) {
        this.id = id;
        this.photosurl = photosurl;
        this.title = title;
        this.text = text;
        this.date = new Date();
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getPhotosurl() {
        return photosurl;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }
    public Integer getId() {
        return id;
    }

    public void setPhotosurl(String photosurl) {
        this.photosurl = photosurl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
