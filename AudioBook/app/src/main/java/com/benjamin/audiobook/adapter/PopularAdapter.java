package com.benjamin.audiobook.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.benjamin.audiobook.R;
import com.benjamin.audiobook.model.Popular;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.prefs.Preferences;

public class PopularAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Popular> arrItem;

    public PopularAdapter(Context context, int layout, List<Popular> arrItem) {
        this.context = context;
        this.layout = layout;
        this.arrItem = arrItem;
    }

    private class ViewHolder{
        ImageView img;
        TextView tvName, tvAuthor;
    }

    @Override
    public int getCount() {
        return arrItem.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewRow = view;
        if(viewRow == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                viewRow = inflater.inflate(layout,viewGroup,false);
            }

            ViewHolder holder = new ViewHolder();
            if (viewRow != null) {
                holder.tvName = viewRow.findViewById(R.id.tvPopularName);
            }
            if (viewRow != null) {
                holder.img = viewRow.findViewById(R.id.imgItemPopular);
            }
//            if (viewRow != null) {
//                holder.tvAuthor = viewRow.findViewById(R.id.tvPopularAuthor);
//            }
            if (viewRow != null) {
                viewRow.setTag(holder);
            }
        }
        final Popular item = arrItem.get(i);
        ViewHolder holder = null;
        if (viewRow != null) {
            holder = (ViewHolder) viewRow.getTag();
        }
        if (holder != null) {
            holder.tvName.setText(item.getName());
        }
//        if (holder != null) {
//            holder.tvAuthor.setText(item.getAuthor());
//        }
        if (holder != null) {
            Glide.with(context).load(item.getImage()).into(holder.img);
        }

        return viewRow;
    }

}