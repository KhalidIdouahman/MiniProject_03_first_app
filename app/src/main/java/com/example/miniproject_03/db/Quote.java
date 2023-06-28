package com.example.miniproject_03.db;

import android.content.ContentValues;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Quote.TABLE_NAME)
public class Quote {
    public static final String DATABASE_NAME = "Quotesdb";
    public static final String TABLE_NAME = "Quotes";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_NAME_QUOTES = "Quotes";
    public static final String COLUMN_NAME_AUTHORS = "Authors";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true , name = COLUMN_ID)
    private int id;
    @ColumnInfo(name = COLUMN_NAME_QUOTES)
    private String quote;
    @ColumnInfo(name = COLUMN_NAME_AUTHORS)
    private String author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Quote(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }

    public static Quote fromContentValues(ContentValues values) {
        if (values.containsKey(COLUMN_NAME_QUOTES) && values.containsKey(COLUMN_NAME_QUOTES)) {
            Quote quote = new Quote(values.getAsString(COLUMN_NAME_QUOTES) , values.getAsString(COLUMN_NAME_AUTHORS));
            return quote;
        }
        return null;
    }
}
