package com.example.layout_main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ProductDataQuery {

    // Thêm sản phẩm vào database
    public static long insert(Context context, Product product) {
        ProductDBHelper dbHelper = new ProductDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Utils.COLUMN_PRODUCT_NAME, product.getName());
        values.put(Utils.COLUMN_PRODUCT_PRICE, product.getPrice());
        values.put(Utils.COLUMN_PRODUCT_OLD_PRICE, product.getOldPrice());
        values.put(Utils.COLUMN_PRODUCT_DESCRIPTION, product.getDescription());
        values.put(Utils.COLUMN_PRODUCT_IMAGE, product.getImagePath()); // Thay imageResourceId thành imagePath (String)

        long result = db.insert(Utils.TABLE_PRODUCT, null, values);
        db.close();
        return result;
    }

    // Lấy tất cả sản phẩm từ database
    public static ArrayList<Product> getAll(Context context) {
        ArrayList<Product> productList = new ArrayList<>();
        ProductDBHelper dbHelper = new ProductDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utils.TABLE_PRODUCT, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                double price = cursor.getDouble(2);
                double oldPrice = cursor.getDouble(3);
                String description = cursor.getString(4);
                String imagePath = cursor.getString(5); // Đọc imagePath thay vì int

                Product product = new Product(id, name, price, oldPrice, description, imagePath);
                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;
    }

    // Xóa sản phẩm theo ID
    public static boolean delete(Context context, int id) {
        ProductDBHelper dbHelper = new ProductDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(Utils.TABLE_PRODUCT, Utils.COLUMN_PRODUCT_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return (result > 0);
    }

    // Cập nhật sản phẩm
    public static int update(Context context, Product product) {
        ProductDBHelper dbHelper = new ProductDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Utils.COLUMN_PRODUCT_NAME, product.getName());
        values.put(Utils.COLUMN_PRODUCT_PRICE, product.getPrice());
        values.put(Utils.COLUMN_PRODUCT_OLD_PRICE, product.getOldPrice());
        values.put(Utils.COLUMN_PRODUCT_DESCRIPTION, product.getDescription());
        values.put(Utils.COLUMN_PRODUCT_IMAGE, product.getImagePath()); // Thay imageResourceId thành imagePath (String)

        int result = db.update(Utils.TABLE_PRODUCT, values, Utils.COLUMN_PRODUCT_ID + "=?", new String[]{String.valueOf(product.getId())}); // Dùng ID thay vì name
        db.close();
        return result;
    }
}
