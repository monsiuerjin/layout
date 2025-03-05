package com.example.layout_main;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ViewFlipper viewFlipper;
    private ImageButton btnPrev, btnNext;
    private ProductDBHelper databaseHelper;
    private List<Product> originalProductList;
    private androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new ProductDBHelper(this);
        List<Product> products = getAllProductsFromDatabase();
        originalProductList = new ArrayList<>(products);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        adapter = new ProductAdapter(this, products, this);
        recyclerView.setAdapter(adapter);

        viewFlipper = findViewById(R.id.viewFlipper);
        int[] images = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
        for (int image : images) {
            addImageToFlipper(image);
        }
        viewFlipper.setFlipInterval(10000);
        viewFlipper.startFlipping();

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        btnPrev.setOnClickListener(v -> {
            viewFlipper.setInAnimation(this, R.anim.slide_in_left);
            viewFlipper.setOutAnimation(this, R.anim.slide_out_right);
            viewFlipper.stopFlipping();
            viewFlipper.showPrevious();
            viewFlipper.startFlipping();
        });

        btnNext.setOnClickListener(v -> {
            viewFlipper.setInAnimation(this, R.anim.slide_in_right);
            viewFlipper.setOutAnimation(this, R.anim.slide_out_left);
            viewFlipper.stopFlipping();
            viewFlipper.showNext();
            viewFlipper.startFlipping();
        });

        searchView = findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterProducts(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProducts(newText);
                return true;
            }
        });

        findViewById(R.id.btn_cart).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    private List<Product> getAllProductsFromDatabase() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(Utils.TABLE_PRODUCT, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_PRODUCT_ID));
                String name = cursor.getString(cursor.getColumnIndex(Utils.COLUMN_PRODUCT_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(Utils.COLUMN_PRODUCT_PRICE));
                double oldPrice = cursor.getDouble(cursor.getColumnIndex(Utils.COLUMN_PRODUCT_OLD_PRICE));
                String description = cursor.getString(cursor.getColumnIndex(Utils.COLUMN_PRODUCT_DESCRIPTION));
                String image = cursor.getString(cursor.getColumnIndex(Utils.COLUMN_PRODUCT_IMAGE));

                productList.add(new Product(id, name, price, oldPrice, description, image));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return productList;
    }

    private void filterProducts(String query) {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : originalProductList) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(product);
            }
        }
        adapter.updateList(filteredList);
    }

    private void addImageToFlipper(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(image);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        viewFlipper.addView(imageView);
    }

    @Override
    public void onItemClick(Product product) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }
}