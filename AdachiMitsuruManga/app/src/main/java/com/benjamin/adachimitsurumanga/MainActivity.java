package com.benjamin.adachimitsurumanga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.benjamin.adachimitsurumanga.adapter.MangaAdapter;
import com.benjamin.adachimitsurumanga.adapter.PopularViewPagerAdapter;
import com.benjamin.adachimitsurumanga.model.Manga;
import com.benjamin.adachimitsurumanga.network.DataClient;
import com.benjamin.adachimitsurumanga.network.RetrofitInstance;
import com.benjamin.adachimitsurumanga.response.ResponseEntity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PopularViewPagerAdapter.OnItemClickListener {

    private List<Manga> topFive;
    private List<Manga> mangas;
    private ViewPager viewPager;
    private PopularViewPagerAdapter adapter;
    private ImageView imgMenu, imgSearch;
    private RecyclerView recyclerViewAll;
    private MangaAdapter mangaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findId();
        initData();
        initViews();
    }

    private void findId() {
        imgMenu = findViewById(R.id.imgMenu);
        imgSearch = findViewById(R.id.imgSearch);
        viewPager = findViewById(R.id.viewPagerPopular);
        recyclerViewAll = findViewById(R.id.recyclerAll);
    }

    @SuppressLint("NewApi")
    private void initData() {
        Intent intent = getIntent();
        ResponseEntity entity = (ResponseEntity) intent.getSerializableExtra("mangas");
        entity.getData().sort(Comparator.comparing(Manga::getViews).reversed());
        topFive = entity.getData().subList(0, 4);
        mangas = entity.getData().subList(4, entity.getData().size());
    }

    private void initViews() {
        viewPager.setClipToPadding(false);
        viewPager.setPadding(80, 20, 80, 20);
        viewPager.setPageMargin(30);
        adapter = new PopularViewPagerAdapter(this, R.layout.item_popular, topFive);
        viewPager.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        mangaAdapter = new MangaAdapter(mangas, this);
//        LinearLayoutManager horizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAll.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewAll.setAdapter(mangaAdapter);
    }

    @Override
    public void onItemClick(View view, int posititon) {

    }
}
