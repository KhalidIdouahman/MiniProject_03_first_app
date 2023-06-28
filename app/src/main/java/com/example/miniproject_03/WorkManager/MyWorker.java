package com.example.miniproject_03.WorkManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.miniproject_03.R;


public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        displayNotification("Save to Download folder" , "Done");
        return Result.success();
    }

    private void displayNotification(String task , String desc) {
        final String NOTIFICATIONS = "notification";
        NotificationManager notifsManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notifsChannel = new NotificationChannel( NOTIFICATIONS, NOTIFICATIONS ,
                    NotificationManager.IMPORTANCE_DEFAULT);
            notifsManager.createNotificationChannel(notifsChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext() , NOTIFICATIONS )
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher);

        notifsManager.notify(1 , builder.build());
    }

}
