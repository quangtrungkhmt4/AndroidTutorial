package com.benjamin.audiobook.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.benjamin.audiobook.model.Popular;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private static final String PATH = Environment.getDataDirectory().getPath()
            + "/data/com.benjamin.audiobook/databases/";

    private static final String DB_NAME = "audio_book.sqlite";
    private static final String TABLE_ACCENT = "accent";
    private static final String TABLE_AUTHOR = "author";
    private static final String TABLE_BOOK = "book";
    private static final String TABLE_CATEGORY = "category";
    private static final String TABLE_FAVORITE = "favorite";
    private static final String TABLE_HISTORY = "history";
    private static final String TABLE_PART = "part";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String AUTHOR = "author";
    private static final String ACCENT = "accent";
    private static final String IMAGE = "image";
    private static final String DURATION = "duration";
    private static final String NUMBER_PART = "number_part";
    private static final String VIEWS = "views";
    private static final String CATEGORY = "category";
    private static final String CREATED_AT = "created_at";
    private static final String RELATED = "related";
    private static final String LINK = "link";
    private static final String BOOK = "book";
    private static final String URL = "url";

    private Context context;
    private SQLiteDatabase database;

    public DatabaseUtil(Context context) {
        this.context = context;
        copyFileToDevice();
    }

    private void copyFileToDevice() {
        File file = new File(PATH + DB_NAME);
        if (!file.exists()) {
            File parent = file.getParentFile();
            parent.mkdirs();
            try {
                InputStream inputStream = context.getAssets().open(DB_NAME);
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int count = inputStream.read(b);
                while (count != -1) {
                    outputStream.write(b, 0, count);
                    count = inputStream.read(b);
                }
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openDatabase() {
        database = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
    }

    private void closeDatabase() {
        database.close();
    }

    public List<Popular> getListPopular(){
        List<Popular> populars = new ArrayList<>();
        openDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.query(TABLE_BOOK, null, null
                , null, null, null, "views DESC", "8,6");
        cursor.moveToFirst();

        int indexId = cursor.getColumnIndex(ID);
        int indexName = cursor.getColumnIndex(NAME);
        int indexAuthor = cursor.getColumnIndex(AUTHOR);
        int indexAccent = cursor.getColumnIndex(ACCENT);
        int indexImage = cursor.getColumnIndex(IMAGE);
        int indexDuration = cursor.getColumnIndex(DURATION);
        int indexPart = cursor.getColumnIndex(NUMBER_PART);
        int indexViews = cursor.getColumnIndex(VIEWS);
        int indexCategory = cursor.getColumnIndex(CATEGORY);
        int indexCreatedAt = cursor.getColumnIndex(CREATED_AT);
        int indexRelated = cursor.getColumnIndex(RELATED);

        while (!cursor.isAfterLast()) {
            String id = cursor.getString(indexId);
            String name = cursor.getString(indexName);
            String accent = cursor.getString(indexAccent);
            String author = cursor.getString(indexAuthor);
            String image = cursor.getString(indexImage);
            String duration = cursor.getString(indexDuration);
            int part = cursor.getInt(indexPart);
            long views = cursor.getLong(indexViews);
            Popular book = new Popular(id,image,name,author,part,duration, views);
            populars.add(book);
            cursor.moveToNext();
        }
        closeDatabase();
        return populars;
    }

    public List<Popular> getListBooks(String category, int limit, int offset){
        List<Popular> populars = new ArrayList<>();
        openDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.query(TABLE_BOOK, null, "category = ?"
                , new String[]{category}, null, null, "views DESC", offset + "," + limit);
        cursor.moveToFirst();

        int indexId = cursor.getColumnIndex(ID);
        int indexName = cursor.getColumnIndex(NAME);
        int indexAuthor = cursor.getColumnIndex(AUTHOR);
        int indexAccent = cursor.getColumnIndex(ACCENT);
        int indexImage = cursor.getColumnIndex(IMAGE);
        int indexDuration = cursor.getColumnIndex(DURATION);
        int indexPart = cursor.getColumnIndex(NUMBER_PART);
        int indexViews = cursor.getColumnIndex(VIEWS);
        int indexCategory = cursor.getColumnIndex(CATEGORY);
        int indexCreatedAt = cursor.getColumnIndex(CREATED_AT);
        int indexRelated = cursor.getColumnIndex(RELATED);

        while (!cursor.isAfterLast()) {
            String id = cursor.getString(indexId);
            String name = cursor.getString(indexName);
            String accent = cursor.getString(indexAccent);
            String author = cursor.getString(indexAuthor);
            String image = cursor.getString(indexImage);
            String duration = cursor.getString(indexDuration);
            int part = cursor.getInt(indexPart);
            long views = cursor.getLong(indexViews);
            Popular book = new Popular(id,image,name,author,part,duration, views);
            populars.add(book);
            cursor.moveToNext();
        }
        closeDatabase();
        return populars;
    }

}
