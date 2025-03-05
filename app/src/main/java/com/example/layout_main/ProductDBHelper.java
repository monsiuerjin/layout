package com.example.layout_main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = Utils.DATABASE_NAME;
    private static final int DATABASE_VERSION = 1;

    public ProductDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS " + Utils.TABLE_PRODUCT + " ("
                + Utils.COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Utils.COLUMN_PRODUCT_NAME + " TEXT, "
                + Utils.COLUMN_PRODUCT_PRICE + " REAL, "
                + Utils.COLUMN_PRODUCT_OLD_PRICE + " REAL, "
                + Utils.COLUMN_PRODUCT_DESCRIPTION + " TEXT, "
                + Utils.COLUMN_PRODUCT_IMAGE + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_PRODUCT);
        onCreate(sqLiteDatabase);
    }
}
