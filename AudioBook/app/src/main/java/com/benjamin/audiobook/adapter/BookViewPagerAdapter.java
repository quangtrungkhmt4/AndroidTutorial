package com.benjamin.audiobook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.benjamin.audiobook.R;
import com.benjamin.audiobook.model.Popular;
import com.bumptech.glide.Glide;

import java.util.List;

public class BookViewPagerAdapter extends PagerAdapter {

    private List<Popular> books;
    private int layout;
    private Context context;

    public BookViewPagerAdapter(Context context, int layout, List<Popular> books) {
        this.books = books;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Popular item = books.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, container, false);
        ImageView img = view.findViewById(R.id.imgItemBook);

        Glide.with(context).load(item.getImage()).into(img);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // container chính là ViewPager, còn Object chính là return của instantiateItem ứng với position
        container.removeView((View) object);
    }
}