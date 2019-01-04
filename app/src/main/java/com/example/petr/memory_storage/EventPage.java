package com.example.petr.memory_storage;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.Query;

public class EventPage extends AppCompatActivity {
    private Animation mEnlargeAnimation;
    Context context;
    ScrollView sv;
    ImageView iv;
    TextView com;
    int id_record_now = 0;
    int id_photo_now = 0;
    int comment_status=0;

    List<Record> records;
    App app;
    Api api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        context = this;
        sv = findViewById(R.id.comment);
        sv.setVisibility(View.INVISIBLE);
        iv = findViewById(R.id.image_record);
        com = findViewById(R.id.coment_record);

        Bundle arguments = getIntent().getExtras();
        Integer eventid = (int) arguments.get("eventid");

        app = new App();
        api = app.getApi();


        Call<List<Record>> r = api.getRecords(eventid);
        r.enqueue(new Callback<List<Record>>() {
            @Override
            public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {

                if (response.isSuccessful()) {
                    records = response.body();
                    //Выставим первое фото из первой записи
                    uploadContent();

                } else {
                    Log.d("response message", response.message());
                    Log.d("response code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Record>> call, Throwable t) {
                Log.d("failure", t.toString());
            }
        });

        //Обработка показа и скрытия коментария
        findViewById(R.id.comentbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comment_status==0){
                    sv.setVisibility(View.VISIBLE);
                    mEnlargeAnimation = AnimationUtils.loadAnimation(context, R.anim.enlarge);
                    sv.startAnimation(mEnlargeAnimation);
                    comment_status=1;
                }
                else {
                    comment_status=0;
                    sv.setVisibility(View.INVISIBLE);
                }
            }
        });
        //Действия при нажатии кнопки назад
        findViewById(R.id.navbtn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_photo_now == 0) {
                    if (id_record_now == 0) {
                        Toast.makeText(getApplicationContext(),
                                "Это первая фотография", Toast.LENGTH_LONG).show();
                    } else {
                        id_record_now--;
                        id_photo_now = records.get(id_record_now).getLastPhoto();
                        uploadContent();
                    }
                } else {
                    id_photo_now--;
                    uploadContent();
                }
            }
        });
        //Действия при нажатии кнопки вперед
        findViewById(R.id.navbtn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_photo_now == records.get(id_record_now).getLastPhoto()) {
                    if (id_record_now == records.size() - 1) {
                        Toast.makeText(getApplicationContext(),
                                "Это последняя фотография", Toast.LENGTH_LONG).show();
                    } else {
                        id_record_now++;
                        id_photo_now = 0;
                        uploadContent();
                    }
                } else {
                    id_photo_now++;
                    uploadContent();
                }
            }
        });

//        Toast.makeText(getApplicationContext(),
//                "Событие: "+eventid.toString(), Toast.LENGTH_LONG).show();
    }

    private void uploadContent() {
        RotateAnimation anim = new RotateAnimation(0f, 50f, 15f, 15f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(1);
        anim.setDuration(600);

        String purl = records.get(id_record_now).getPhoto(id_photo_now);
        String coment = records.get(id_record_now).getComent();
        iv.startAnimation(anim);

        Picasso.with(context)
                .load(purl)
                .error(R.drawable.ic_launcher_background)
                .into(iv);
        com.setText(coment);
    }

}
