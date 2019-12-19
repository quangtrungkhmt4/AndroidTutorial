package com.benjamin.audiobook.adapter;

import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.benjamin.audiobook.R;
import com.benjamin.audiobook.fragment.BookInfoFragment;
import com.benjamin.audiobook.fragment.PlayFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;

    public FragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return BookInfoFragment.newInstance();
            case 1:
                return PlayFragment.newInstance();
            default:
                return null;
        }
    }

}
