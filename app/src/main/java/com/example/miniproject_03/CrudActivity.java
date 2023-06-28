package com.example.miniproject_03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.room.RoomMasterTable;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.miniproject_03.ContentProvider.MyContentProvider;
import com.example.miniproject_03.RecyclerAdapter.QuotesRecyclerAdapter;
import com.example.miniproject_03.databinding.ActivityCrudBinding;
import com.example.miniproject_03.db.MyQuotesDB;
import com.example.miniproject_03.db.Quote;

import java.util.ArrayList;

public class CrudActivity extends AppCompatActivity {
    ActivityCrudBinding crudBindingViews;
    View root;
    MyQuotesDB quotesDB;
    QuotesRecyclerAdapter quotesAdapter;
    ArrayList<Quote> listOfQuotes;
    ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crudBindingViews = ActivityCrudBinding.inflate(getLayoutInflater());
        root = crudBindingViews.getRoot();
        setContentView(root);

        quotesDB = Room.databaseBuilder(this , MyQuotesDB.class , Quote.DATABASE_NAME)
                .allowMainThreadQueries().build();

        String quote4 = "We can't solve problems by using the same kind of thinking we used when we created them";
        String author4 = "Albert Einstein" ;

        values = new ContentValues();
        values.put(Quote.COLUMN_NAME_QUOTES , quote4);
        values.put(Quote.COLUMN_NAME_AUTHORS , author4);
        Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI , values);
        Toast.makeText(this, "quote inserted !", Toast.LENGTH_SHORT).show();

        for (int i = 1; i < 5; i++) {
            quotesDB.myDB().addQuote(new Quote( quote4 , author4));
        }


//        to convert the list returned by the database to arrayList .
        listOfQuotes = new ArrayList<>(quotesDB.myDB().getAllQuotes());
        quotesAdapter = new QuotesRecyclerAdapter(listOfQuotes);
        crudBindingViews.recyclerShowQuotes.setLayoutManager(new LinearLayoutManager(this));
        crudBindingViews.recyclerShowQuotes.setAdapter(quotesAdapter);

    }
}