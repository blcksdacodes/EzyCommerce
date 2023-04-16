package com.application.ezycommerce.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.application.ezycommerce.models.BookModel;

public class DBProductHelper extends SQLiteOpenHelper {
    public static final String database_name = "EzyCommerce2.db";
    public static final String table_name = "Product";
    public static final String col1 = "id";
    public static final String col2 = "name";
    public static final String col3 = "description";
    public static final String col4 = "author";
    public static final String col5 = "type";
    public static final String col6 = "img";
    public static final String col7 = "inCart";
    public static final String col8 = "category";
    public static final String col9 = "price";


    public DBProductHelper(Context context) {
        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name + " (ID STRING PRIMARY KEY,name STRING,description STRING,author STRING,type STRING,img STRING,inCart BOOLEAN,category STRING,price REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }

    public boolean insertdata(BookModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, model.getId());
        contentValues.put(col2, model.getName());
        contentValues.put(col3, model.getDescription());
        contentValues.put(col4, model.getAuthor());
        contentValues.put(col5, model.getType());
        contentValues.put(col6, model.getImg());
        contentValues.put(col7, model.getInCart());
        contentValues.put(col8, model.getCategory());
        contentValues.put(col9, model.getPrice());
        long result = db.insert(table_name, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor resultset = db.rawQuery("select * from " + table_name, null);
        return resultset;
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + table_name);
    }
}
