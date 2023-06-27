package com.example.miniproject_03.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Quote.class} , version = 1 , exportSchema = true)
public abstract class MyQuotesDB extends RoomDatabase {
    public abstract MyDB myDB();
}
