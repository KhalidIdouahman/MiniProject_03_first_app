package com.example.miniproject_03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.room.RoomMasterTable;

import android.os.Bundle;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crudBindingViews = ActivityCrudBinding.inflate(getLayoutInflater());
        root = crudBindingViews.getRoot();
        setContentView(root);

        quotesDB = Room.databaseBuilder(this , MyQuotesDB.class , "Quotesdb")
                .allowMainThreadQueries().build();

        String quote4 = "To be yourself in a world that is constantly trying to make you something else is the greatest accomplishment";
        String author4 = "Ralph Waldo Emerson";

        for (int i = 1; i < 5; i++) {
            quotesDB.myDB().addQuote(new Quote(i , quote4 , author4));
        }


//        to convert the list returned by the database to arrayList .
        listOfQuotes = new ArrayList<>(quotesDB.myDB().getAllQuotes());
        quotesAdapter = new QuotesRecyclerAdapter(listOfQuotes);
        crudBindingViews.recyclerShowQuotes.setLayoutManager(new LinearLayoutManager(this));
        crudBindingViews.recyclerShowQuotes.setAdapter(quotesAdapter);

    }
}