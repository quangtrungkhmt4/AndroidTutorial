package com.benjamin.adachimitsurumanga;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.benjamin.adachimitsurumanga.model.Manga;
import com.benjamin.adachimitsurumanga.network.DataClient;
import com.benjamin.adachimitsurumanga.network.RetrofitInstance;
import com.benjamin.adachimitsurumanga.response.ResponseEntity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private static final int FIRST_PAGE = 0;
    private static final int NUMBER_MANGA = 24;

    private LinearLayout lnSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        findId();
        initData();
    }

    private void findId() {
        lnSplash = findViewById(R.id.lnSplash);
    }

    private void initData() {
        DataClient dataClient = RetrofitInstance.getInstance().create(DataClient.class);
        Call<ResponseEntity> call = dataClient.getMangas(FIRST_PAGE, NUMBER_MANGA);
        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                ResponseEntity responseEntity = response.body();
                if (responseEntity == null || responseEntity.getCode() == 1) {
                    retryData("Phát sinh lỗi không xác định, thử lại!");
                }

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("mangas", responseEntity);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                retryData("Kết nối mạng không ổn định, thử lại!");
            }
        });
    }

    private void retryData(String message) {
        Snackbar snackbar = Snackbar
                .make(lnSplash, message, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initData();
                    }
                });
        snackbar.setActionTextColor(Color.GRAY);
        snackbar.show();
    }
}
