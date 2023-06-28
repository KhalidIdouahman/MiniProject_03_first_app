package com.example.miniproject_03.WorkManager;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.miniproject_03.CrudActivity;
import com.example.miniproject_03.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Data data = getInputData();
        writeDataIntoFile(data);
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

    @SuppressLint("RestrictedApi")
    private void writeDataIntoFile(Data data) {
            String[] quotesListRecieved = data.getStringArray(CrudActivity.KEY_TO_SEND_DATA_QUOTE);
            String[] authorsListRecieved = data.getStringArray(CrudActivity.KEY_TO_SEND_DATA_AUTHOR);


            String quotesFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/quotes.txt";
            File quotesFile = new File(quotesFilePath);

            try  {
//                if (quotesFile.exists()) {
//                    quotesFile.delete(); // Delete the file if it already exists
//                }
//                quotesFile.createNewFile(); // Create a new empty file

                PrintWriter writer = new PrintWriter(new FileWriter(quotesFile , true));
                for (int i = 0 ; i < CrudActivity.QUOTES_NUMBER ; i++) {
                    Log.e("TAG", "writeDataIntoFile: the quotes : " + quotesListRecieved[i]);
                    writer.println("#" + (i + 1) + " " + quotesListRecieved[i] + "\n -->" + authorsListRecieved[i] + "\n");
                }
                writer.close();
            } catch (IOException e) {
                // Handle exception
                Log.e("TAG", "writeDataIntoFile: there is an error");
                e.printStackTrace();
            }

        Log.e("TAG", "writeDataIntoFile: the size of the data is : " + data.size());
    }

}
