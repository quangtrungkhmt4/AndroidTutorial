package com.benjamin.audiobook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.benjamin.audiobook.adapter.FragmentAdapter;
import com.benjamin.audiobook.model.Popular;

public class InfoActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private FragmentAdapter fragmentAdapter;
    private ViewPager viewPager;

    private ImageView imgFirstDot;
    private ImageView imgSecondDot;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        findId();
        initViews();
    }

    private void findId() {
        viewPager = findViewById(R.id.viewpagerInfo);
        imgFirstDot = findViewById(R.id.imgFirstDot);
        imgSecondDot = findViewById(R.id.imgSecondDot);
        imgBack = findViewById(R.id.imgBack);
    }

    private void initViews() {
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(this);
        imgBack.setOnClickListener(this);
        imgFirstDot.setImageResource(R.drawable.ic_dot_yellow);
        imgSecondDot.setImageResource(R.drawable.ic_dot);

        Intent intent = getIntent();
        Popular result = (Popular) intent.getSerializableExtra("book_info");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0){
            imgFirstDot.setImageResource(R.drawable.ic_dot_yellow);
            imgSecondDot.setImageResource(R.drawable.ic_dot);
        }else {
            imgSecondDot.setImageResource(R.drawable.ic_dot_yellow);
            imgFirstDot.setImageResource(R.drawable.ic_dot);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBack:
                finish();
                break;
        }
    }
}
