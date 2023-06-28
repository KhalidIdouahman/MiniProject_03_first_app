package com.example.miniproject_03.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;

import com.example.miniproject_03.db.MyQuotesDB;
import com.example.miniproject_03.db.Quote;

import java.util.regex.Matcher;

public class MyContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.my.content.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + Quote.TABLE_NAME);

    public static int TABLE_NAME_CODE = 1;
    public static int TABLE_NAME_ID_CODE = 2;

    static UriMatcher myUri = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        myUri.addURI(AUTHORITY , Quote.TABLE_NAME , TABLE_NAME_CODE);
        myUri.addURI(AUTHORITY , Quote.TABLE_NAME + "/#", TABLE_NAME_ID_CODE);
    }

    MyQuotesDB quotesDB;

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        quotesDB = Room.databaseBuilder(getContext() , MyQuotesDB.class , Quote.DATABASE_NAME)
                .allowMainThreadQueries().build();

        return quotesDB.isOpen();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int code = myUri.match(uri);
        if (code == TABLE_NAME_CODE || code == TABLE_NAME_ID_CODE) {
            Context context = getContext();
            if (context == null) return null;

            long id = quotesDB.myDB().addQuote(Quote.fromContentValues(values));
            context.getContentResolver().notifyChange(uri , null);
            return ContentUris.withAppendedId(uri , id);
        } else {
            throw new IllegalArgumentException("Unknown URI : " + uri);
        }
//        switch (myUri.match(uri)) {
//            case TABLE_NAME_CODE:
//                return "vnd.android.cursor.dir/" + AUTHORITY + "." + Quote.TABLE_NAME;
//            case TABLE_NAME_ID_CODE:
//                return "vnd.android.cursor.item/" + AUTHORITY + "." + Quote.TABLE_NAME;
//            default:
//                throw new IllegalArgumentException("Unknown URI : " + uri);
//        }
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int code = myUri.match(uri);
        if (code == TABLE_NAME_CODE || code == TABLE_NAME_ID_CODE) {
            Context context = getContext();
            if (context == null) return null;
            Cursor cursor = (Cursor) quotesDB.myDB().getAllQuotes();
            cursor.setNotificationUri(context.getContentResolver() , uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI : " + uri);
        }
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
//        switch (myUri.match(uri)) {
//            case TABLE_NAME_CODE:
//                return "vnd.android.cursor.dir/" + AUTHORITY + "." + Quote.TABLE_NAME;
//            case TABLE_NAME_ID_CODE:
//                return "vnd.android.cursor.item/" + AUTHORITY + "." + Quote.TABLE_NAME;
//            default:
//                throw new IllegalArgumentException("Unknown URI : " + uri);
//        }
        return "vnd.android.cursor.dir/" + AUTHORITY + "." + Quote.TABLE_NAME;
    }



    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}