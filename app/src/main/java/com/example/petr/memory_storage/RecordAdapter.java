package com.example.petr.memory_storage;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView text;
        TextView autor;
        ImageView recordPhoto;
        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            title = (TextView)itemView.findViewById(R.id.record_title);
            text = (TextView)itemView.findViewById(R.id.record_text);
            recordPhoto = (ImageView)itemView.findViewById(R.id.baseimage);
            autor = itemView.findViewById(R.id.record_autor);
        }
    }

    private List<Event> events;
    Context context;
    Activity activity;

    RecordAdapter(List<Event> events, Activity activity) {

        this.events = events;
        this.activity = activity;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.record_item, viewGroup, false);
        context = viewGroup.getContext();

        return new ViewHolder(view);

    }
    Event r;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        r = events.get(i);

        Picasso.with(context)
                .load(r.getPhotosurl())
                .error(R.drawable.ic_launcher_background)
                .into(viewHolder.recordPhoto);
        viewHolder.title.setText(r.getTitle());
        viewHolder.text.setText(r.getText());
        viewHolder.autor.setText(r.getUsername());

        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventPage.class);
                intent.putExtra("eventid", r.getId());
                context.startActivity(intent, ActivityOptions.makeCustomAnimation(context,R.anim.ani1,R.anim.ani2).toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
