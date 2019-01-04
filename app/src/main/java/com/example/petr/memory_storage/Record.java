package com.example.petr.memory_storage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Record {
    @SerializedName("photos")
    @Expose
    private List<String> photos;
    @SerializedName("coment")
    @Expose
    private String coment;
    @SerializedName("autor")
    @Expose
    private String autor;

    public Record(List<String> photos,String coment,String autor){
        this.autor=autor;
        this.coment = coment;
        this.photos = photos;
    }
    public void addPhoto(String url){
        photos.add(url);
    }

    public String getPhoto(int id) {
        return photos.get(id);
    }

    public String getAutor() {
        return autor;
    }

    public String getComent() {
        return coment;
    }

    public int getLastPhoto(){
        return photos.size()-1;
    }
}
