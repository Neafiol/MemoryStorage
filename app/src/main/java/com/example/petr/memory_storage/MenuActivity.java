package com.example.petr.memory_storage;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {
    List<Event> events;
    RecyclerView rv;
    Context context;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        rv = (RecyclerView)findViewById(R.id.rv);
        events = new ArrayList<>();
        context=this;
        activity = this;

        App api = new App();
        Api event = api.getApi();
        Call<List<Event>> r = event.getAll();
        r.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {

                if (response.isSuccessful()) {
                    events = response.body();
                    Log.d("response 2", response.body().get(0).getTitle() );

                    RecordAdapter adapter = new RecordAdapter(events, activity);
                    rv.setAdapter(adapter);
                    rv.setLayoutManager(new LinearLayoutManager(context));

                    Log.d("Size : ",String.valueOf(events.size()) );
                } else {
                    Log.d("menu activity", response.message() );
                    Toast.makeText(getApplicationContext(),
                            "Ошибка при подключении к серверу", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d("failure 2", t.toString());
            }
        });



    }
}
