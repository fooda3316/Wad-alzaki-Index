package com.fooda.wadalzaki.more;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.fooda.wadalzaki.R;

import java.util.List;

public class MoreAppsAdapter extends RecyclerView.Adapter<MoreAppsAdapter.MyViewHolder> {

    List<MoreApps> apps;
    MoreAppsListener listener;

    public MoreAppsAdapter(List<MoreApps> apps, MoreAppsListener listener) {
        this.apps = apps;
        this.listener = listener;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_more_apps, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        MoreApps example = apps.get(position);
        holder.title.setText(example.getTitle());
        holder.imageView.setBackgroundResource(example.getImage());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.examplesOnClick(example);
            }
        });


    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.more_apps_img);
            title = itemView.findViewById(R.id.more_apps_text);
            cardView = itemView.findViewById(R.id.more_apps_cv);
        }
    }
    public interface MoreAppsListener{
        void examplesOnClick(MoreApps example);
    }
}
