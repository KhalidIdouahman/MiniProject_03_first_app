package com.example.miniproject_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.miniproject_03.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBindingViews;
    View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBindingViews = ActivityMainBinding.inflate(getLayoutInflater());
        root = mainBindingViews.getRoot();
        setContentView(root);


    }
}