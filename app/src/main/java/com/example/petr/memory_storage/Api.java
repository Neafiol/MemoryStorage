package com.example.petr.memory_storage;

import java.util.List;
import java.util.Queue;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {
    @POST("/test")
    Call<Event> getTest();

    @POST("/getall")
    Call<List<Event>> getAll();

    @POST("/getrecords")
    Call<List<Record>> getRecords(@Query("id") int id);

    @Multipart
    @POST("/add")
    Call<ResponseBody> upload(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );
}
