package com.example.petr.memory_storage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    Uri photouri;
    Api api;
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = new App();
        api = app.getApi();

        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("hello", "Hello World");
        startActivity(intent);

        findViewById(R.id.testbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }
        });



//        Call<Event> r =api.getTest();
//        r.enqueue(new Callback<Event>() {
//            @Override
//            public void onResponse(Call<Event> call, Response<Event> response) {
//
//                if (response.isSuccessful()) {
//                    Log.d("response", response.body().getTitle() );
//                } else {
//                    Log.d("response message", response.message() );
//                    Log.d("response code", String.valueOf(response.code()) );
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Event> call, Throwable t) {
//                Log.d("failure", t.toString());
//            }
//        });



    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                photouri= data.getData();

                Log.d("FIND URI", photouri.getPath());
                Log.d("FIND URI", photouri.toString());


                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photouri);
                    ImageView user_image = findViewById(R.id.imageView);
                    user_image.setImageBitmap(bitmap);

                    app.uploadFile(photouri);
                } catch (IOException e) {
                    e.printStackTrace();
                }






            }
        }
    }


}
