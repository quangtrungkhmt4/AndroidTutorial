package com.benjamin.adachimitsurumanga.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.benjamin.adachimitsurumanga.R;
import com.benjamin.adachimitsurumanga.model.Manga;
import com.bumptech.glide.Glide;

import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.PopularViewHolder>{
    private List<Manga> populars;
    Context context;

    public MangaAdapter(List<Manga> populars, Context context){
        this.populars = populars;
        this.context = context;
    }

    @Override
    public PopularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        PopularViewHolder gvh = new PopularViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(PopularViewHolder holder, final int position) {
        Glide.with(context).load(populars.get(position).getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return populars.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public PopularViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.imgItemBook);
        }
    }
}