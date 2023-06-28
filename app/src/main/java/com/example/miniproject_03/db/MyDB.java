package com.example.miniproject_03.db;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyDB {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addQuote(Quote quote);

    @Query("select * from " + Quote.TABLE_NAME)
    Cursor getAllQuotes();

    @Delete
    void deleteQuote(Quote quote);

    @Update
    void updateQuote(Quote quote);
}
