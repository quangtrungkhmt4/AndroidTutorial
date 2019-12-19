package com.benjamin.audiobook.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.benjamin.audiobook.InfoActivity;
import com.benjamin.audiobook.MainActivity;
import com.benjamin.audiobook.R;
import com.benjamin.audiobook.adapter.BookHorizontalAdapter;
import com.benjamin.audiobook.adapter.PopularViewPagerAdapter;
import com.benjamin.audiobook.model.Popular;
import com.benjamin.audiobook.util.DatabaseUtil;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements PopularViewPagerAdapter.OnItemClickListener {

    private static final String HORROR_ID = "5def511c64dee93d5e56437f";
    private static final String SHORT_ID = "5def511c64dee93d5e564380";
    private static final String NNN_ID = "5def511c64dee93d5e564389";
    private static final String LONG_ID = "5def511c64dee93d5e564381";
    private static final String BLADE_ID = "5def511c64dee93d5e564385";

    private ImageView imgMenu, imgSearch;
    private ViewPager viewPager;
    private PopularViewPagerAdapter adapter;
    private MainActivity mainActivity;
    private DatabaseUtil databaseUtil;
    private List<Popular> populars;
    private List<Popular> horrors, shorts, NNNs, longs, blades;
    private BookHorizontalAdapter horrorAdapter, shortAdapter, NNNAdapter, longAdapter, bladeAdapter;
    private RecyclerView recyclerHorror, recyclerShort, recyclerNNN, recyclerLong, recyclerBlade;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findId(view);
        initData();
        initViews();
        return view;
    }

    private void initData() {
        databaseUtil = new DatabaseUtil(mainActivity);
        populars = databaseUtil.getListPopular();
        horrors = databaseUtil.getListBooks(HORROR_ID,12,0);
        shorts = databaseUtil.getListBooks(SHORT_ID, 12, 0);
        NNNs = databaseUtil.getListBooks(NNN_ID, 12, 0);
        longs = databaseUtil.getListBooks(LONG_ID, 12, 0);
        blades = databaseUtil.getListBooks(BLADE_ID, 12, 0);
    }

    private void findId(View view) {
        imgMenu = view.findViewById(R.id.imgMenu);
        imgSearch = view.findViewById(R.id.imgSearch);
        viewPager = view.findViewById(R.id.viewPagerPopular);
        recyclerHorror = view.findViewById(R.id.recyclerHorror);
        recyclerShort = view.findViewById(R.id.recyclerShort);
        recyclerNNN = view.findViewById(R.id.recyclerNNN);
        recyclerLong = view.findViewById(R.id.recyclerLong);
        recyclerBlade = view.findViewById(R.id.recyclerBlade);
        mainActivity = (MainActivity) getActivity();
    }

    private void initViews() {

        viewPager.setClipToPadding(false);
        viewPager.setPadding(80, 20, 80, 20);
        viewPager.setPageMargin(30);
        adapter = new PopularViewPagerAdapter(getContext(), R.layout.item_popular, populars);
        viewPager.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        horrorAdapter = new BookHorizontalAdapter(horrors, getContext());
        LinearLayoutManager horizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerHorror.setLayoutManager(horizontalLayout);
        recyclerHorror.setAdapter(horrorAdapter);

        shortAdapter = new BookHorizontalAdapter(shorts, getContext());
        LinearLayoutManager horizontalLayoutShort = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerShort.setLayoutManager(horizontalLayoutShort);
        recyclerShort.setAdapter(shortAdapter);

        NNNAdapter = new BookHorizontalAdapter(NNNs, getContext());
        LinearLayoutManager horizontalLayoutNNN = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerNNN.setLayoutManager(horizontalLayoutNNN);
        recyclerNNN.setAdapter(NNNAdapter);

        longAdapter = new BookHorizontalAdapter(longs, getContext());
        LinearLayoutManager horizontalLayoutLong = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerLong.setLayoutManager(horizontalLayoutLong);
        recyclerLong.setAdapter(longAdapter);

        bladeAdapter = new BookHorizontalAdapter(blades, getContext());
        LinearLayoutManager horizontalLayoutBlade = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerBlade.setLayoutManager(horizontalLayoutBlade);
        recyclerBlade.setAdapter(bladeAdapter);

//        int pagerPadding = 20;
//        viewPager.setClipToPadding(false);
//        viewPager.setPadding(pagerPadding, 0, pagerPadding, 0);

    }

    @Override
    public void onItemClick(View view, int posititon) {
        mainActivity.switchActivityWithData(InfoActivity.class, populars.get(posititon));
    }
}
