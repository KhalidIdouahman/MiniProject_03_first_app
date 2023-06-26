package com.example.miniproject_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.miniproject_03.databinding.ActivityMainBinding;
import com.example.miniproject_03.databinding.ActivityViewPagerBinding;

import java.util.ArrayList;

public class ViewPagerActivity extends AppCompatActivity {
    ActivityViewPagerBinding viewBinding;
    View root;
    ViewPagerAdapter viewPagerAdapter;
    ArrayList<ViewPagerModel> content;
    SharedPreferences firstTimeToViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityViewPagerBinding.inflate(getLayoutInflater());
        root = viewBinding.getRoot();
        setContentView(root);

        firstTimeToViewPager = getSharedPreferences("first_time", MODE_PRIVATE);

        boolean isFirstTime = firstTimeToViewPager.getBoolean("is_first_time", true);

        Intent intent = new Intent(this, MainActivity.class);
        if (isFirstTime) {
            content = new ArrayList<>();
            content.add(new ViewPagerModel("Hello"));
            content.add(new ViewPagerModel("This App let you manage your quotes"));
            content.add(new ViewPagerModel("Enjoy ..."));

            viewPagerAdapter = new ViewPagerAdapter(content, this);

            viewBinding.viewPager.setAdapter(viewPagerAdapter);
            viewBinding.viewPager.setClipToPadding(false);
            viewBinding.viewPager.setClipChildren(false);
            viewBinding.viewPager.setOffscreenPageLimit(2);
            viewBinding.viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
            viewBinding.skipBtn.setOnClickListener(v -> {
                startActivity(intent);
                finish();
            });

            SharedPreferences.Editor edit = firstTimeToViewPager.edit();
            edit.putBoolean("is_first_time" , false);
            edit.apply();

        } else {
            startActivity(intent);
            finish();
        }

    }
}