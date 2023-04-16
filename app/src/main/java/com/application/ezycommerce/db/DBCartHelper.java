package com.application.ezycommerce.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBCartHelper extends SQLiteOpenHelper {
    public static final String database_name = "EzyCommerce.db";
    public static final String table_name = "Cart";
    public static final String col1 = "id";
    public static final String col2 = "name";
    public static final String col3 = "price";
    public static final String col4 = "amount";
    public static final String col5 = "image";


    public DBCartHelper(Context context) {
        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name + " (ID STRING PRIMARY KEY,name STRING,price REAL,amount INTEGER,image STRING)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }

    public boolean insertdata(String ID, String name, double price, int amount, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, ID);
        contentValues.put(col2, name);
        contentValues.put(col3, price);
        contentValues.put(col4, amount);
        contentValues.put(col5, image);
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

    public void setAmount(String id, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col4, amount);
        db.update(table_name, contentValues, "id = ?", new String[]{id});
    }
}
