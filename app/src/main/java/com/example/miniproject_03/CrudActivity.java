package com.example.miniproject_03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.miniproject_03.RecyclerAdapter.QuotesRecyclerAdapter;
import com.example.miniproject_03.WorkManager.MyWorker;
import com.example.miniproject_03.databinding.ActivityCrudBinding;
import com.example.miniproject_03.db.MyQuotesDB;
import com.example.miniproject_03.db.Quote;

import java.util.ArrayList;
import java.util.List;

public class CrudActivity extends AppCompatActivity {
    ActivityCrudBinding crudBindingViews;
    View root;
    MyQuotesDB quotesDB;
    QuotesRecyclerAdapter quotesAdapter;
    ArrayList<Quote> listOfQuotes;
    ContentValues values;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crudBindingViews = ActivityCrudBinding.inflate(getLayoutInflater());
        root = crudBindingViews.getRoot();
        setContentView(root);

        quotesDB = Room.databaseBuilder(this , MyQuotesDB.class , Quote.DATABASE_NAME)
                .allowMainThreadQueries().build();

//        String quote4 = "We can't solve problems by using the same kind of thinking we used when we created them";
//        String author4 = "Albert Einstein" ;

////        it works it insert the data to db Room
//        values = new ContentValues();
//        values.put(Quote.COLUMN_NAME_QUOTES , quote4);
//        values.put(Quote.COLUMN_NAME_AUTHORS , author4);
//        Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI , values);
//        Toast.makeText(this, "quote inserted !", Toast.LENGTH_SHORT).show();


        listOfQuotes = new ArrayList<>(Quote.getArrayListOfQuotes(quotesDB.myDB().getAllQuotes()));
        quotesAdapter = new QuotesRecyclerAdapter(listOfQuotes);
        crudBindingViews.recyclerShowQuotes.setLayoutManager(new LinearLayoutManager(this));
        crudBindingViews.recyclerShowQuotes.setAdapter(quotesAdapter);

        addBtn = findViewById(R.id.add_form_btn);
        addBtn.setOnClickListener(v -> {
            addQuoteToDb();
        });

        crudBindingViews.saveIv.setOnClickListener(v -> {
            doTaskUsingWorkManager();
        });
    }

    private void addQuoteToDb() {
        EditText quoteEt = findViewById(R.id.quote_form_et);
        EditText authorEt = findViewById(R.id.author_form_et);

        quotesDB.myDB().addQuote(new Quote(quoteEt.getText().toString() , authorEt.getText().toString()));

        listOfQuotes = new ArrayList<>(Quote.getArrayListOfQuotes(quotesDB.myDB().getAllQuotes()));
        quotesAdapter.setAndNotifyData(listOfQuotes);

        quoteEt.setText("");
        quoteEt.requestFocus(View.FOCUS_FORWARD);
        authorEt.setText("");
    }

    private void doTaskUsingWorkManager() {

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).build();
        WorkManager.getInstance(this).enqueue(request);
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.getId())
                .observe(this, workInfo -> {
                    String status = workInfo.getState().name();
                    Log.e("TAG", "doTaskUsingWorkManager: " +  status);
        });
    }
}