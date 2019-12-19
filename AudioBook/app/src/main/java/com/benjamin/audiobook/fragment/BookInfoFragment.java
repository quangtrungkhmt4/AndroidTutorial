package com.benjamin.audiobook.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benjamin.audiobook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookInfoFragment extends Fragment {


    public BookInfoFragment() {
        // Required empty public constructor
    }

    public static BookInfoFragment newInstance() {
        return new BookInfoFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_info, container, false);
    }

}
