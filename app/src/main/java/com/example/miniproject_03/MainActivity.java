package com.example.miniproject_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.miniproject_03.CheckIntenet.InternetChecker;
import com.example.miniproject_03.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBindingViews;
    View root;
    BroadcastReceiver broadcastReceiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBindingViews = ActivityMainBinding.inflate(getLayoutInflater());
        root = mainBindingViews.getRoot();
        setContentView(root);

        broadcastReceiver = new InternetReceiver();
        registerReceiver(broadcastReceiver , new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        mainBindingViews.goBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this , CrudActivity.class);
            startActivity(intent);
        });
    }

    public void LoadImages(boolean isConnected) {
        String[] links = getResources().getStringArray(R.array.links_list);
        int randomNum = new Random().nextInt(links.length);
        if (isConnected) {
        Glide.with(this)
            .load(links[randomNum])
            .into(mainBindingViews.containerIv);
        } else  {
        Glide.with(this)
            .load(R.drawable.image_not_available)
            .into(mainBindingViews.containerIv);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }


    public class InternetReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean status = InternetChecker.getNetworkInfos(context);

            LoadImages(status);

            if (status) {
                Toast.makeText(context, "connected", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "disconnected", Toast.LENGTH_LONG).show();
            }
        }
    }
}