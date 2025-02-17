package com.example.layout_main;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.layout_main.ProductAdapter;
import com.example.layout_main.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ViewFlipper viewFlipper;//mới thêm
    private ImageButton btnPrev, btnNext;

    private TextView txtQuangCao; // Text "QUẢNG CÁO"
    private int[] images = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};//mới thêm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Khởi tạo danh sách sản phẩm (8 sản phẩm)
        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop ASUS Vivobook 15", 13990000.0, 15000000.0, R.drawable.vivobook));
        products.add(new Product("AKG N700NCM2 Wireless Headphones", 3790000.0, 3990000.0, R.drawable.product_image));
        products.add(new Product("iPad Air 6 M2", 13990000.0, 14990000.0, R.drawable.ipab_air_6_m2));
        products.add(new Product("Apple Watch Series 9", 6999000.0, 7990000.0, R.drawable.apple_watch_sr9));
        products.add(new Product("AirPods Pro 2", 5590000.0, 6190000.0, R.drawable.airpod_pro_2));
        products.add(new Product("Loa Bluetooth Beats Pill", 3990000.0, 4290000.0, R.drawable.beat_pill));
        products.add(new Product("Tivi Xiaomi A Pro 4K", 11790000.0, 14990000.0, R.drawable.tv_xiaomi));
        products.add(new Product("Magic Keyboard", 3499000.0, 4990000.0, R.drawable.magic_key));
// Không giảm giá


//         Setup RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Hiển thị 2 cột
        recyclerView.setHasFixedSize(true); // Tối ưu hiệu suất
        adapter = new ProductAdapter(products, this);
        recyclerView.setAdapter(adapter);

        viewFlipper = findViewById(R.id.viewFlipper);
        for (int image : images){
            addImageToFlipper(image);
        }
        // Áp dụng hiệu ứng chuyển đổi
        viewFlipper.setInAnimation(this, R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_left);
        viewFlipper.setFlipInterval(10000); // Chuyển banner mỗi 4 giây
        viewFlipper.startFlipping();

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        btnPrev.setOnClickListener(v -> {
            if (viewFlipper != null) {
                viewFlipper.stopFlipping();
                viewFlipper.showPrevious();
                viewFlipper.startFlipping();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (viewFlipper != null) {
                viewFlipper.stopFlipping();
                viewFlipper.showNext();
                viewFlipper.startFlipping();
            }
        });


    }

//    public void ProductViewHolder(View itemView) {
//        super(itemView);
//        product_oldPrice = (TextView) itemView.findViewById(R.id.productOldprice);
//        product_name = (TextView) itemView.findViewById(R.id.productName);
//        product_image = (ImageView) itemView.findViewById(R.id.productImg);
//        product_discount = (TextView) itemView.findViewById(R.id.productDisc);
//        product_price = (TextView) itemView.findViewById(R.id.productPrice);
//
//        itemView.setOnClickListener(this);
//
//    }
private void addImageToFlipper(int image) {
    ImageView imageView = new ImageView(this);
    imageView.setImageResource(image);
    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    viewFlipper.addView(imageView);
}


    // Xử lý khi nhấp vào sản phẩm
    @Override
    public void onItemClick(Product product) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("product", product.getImageResourceId()); // Truyền sản phẩm qua Intent
        startActivity(intent);
    }
}