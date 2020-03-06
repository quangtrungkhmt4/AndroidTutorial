package com.benjamin.adachimitsurumanga.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.benjamin.adachimitsurumanga.R;
import com.benjamin.adachimitsurumanga.model.Manga;
import com.bumptech.glide.Glide;

import java.util.List;

public class PopularViewPagerAdapter extends PagerAdapter {

    private Context context;
    private int layout;
    private List<Manga> arrItem;
    private static OnItemClickListener clickListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int posititon);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        clickListener = listener;
    }

    public PopularViewPagerAdapter(Context context, int layout, List<Manga> arrItem) {
        this.context = context;
        this.layout = layout;
        this.arrItem = arrItem;
    }

    @Override
    public int getCount() {
        return arrItem.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        Manga item = arrItem.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(layout, container, false);
        ImageView img = view.findViewById(R.id.imgItemPopular);
        TextView tvPart = view.findViewById(R.id.tvPart);
        TextView tvListen = view.findViewById(R.id.tvViews);
        TextView tvName = view.findViewById(R.id.tvPopularName);

        Glide.with(context).load(item.getImageUrl()).into(img);
        tvPart.setText(item.getChaps().size() + " chap");
        tvListen.setText(item.getViews() + " " + context.getString(R.string.listen));
        tvName.setText(item.getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null){
                    clickListener.onItemClick(view, position);
                }
            }
        });

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // container chính là ViewPager, còn Object chính là return của instantiateItem ứng với position
        container.removeView((View) object);
    }
}