package com.example.layout_main;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
        products.add(new Product("Laptop ASUS Vivobook 15", 13990000.0, 15000000.0, "\uD83D\uDD39 Processor & Graphics\n"
                + "Graphics Card: Intel Iris Xe Graphics\n"
                + "CPU: Intel Core i5-1235U (1.3 GHz, 12M Cache, up to 4.4 GHz, 10 cores)\n\n"
                + "\uD83D\uDD39 Memory & Storage\n"
                + "RAM: 16GB DDR4 (8GB DDR4 Onboard + 8GB DDR4 SO-DIMM)\n"
                + "Storage: 512GB M.2 NVMe PCIe 3.0 SSD\n\n"
                + "\uD83D\uDD39 Display\n"
                + "Size: 15.6 inches\n"
                + "Resolution: 1920 x 1080 pixels (Full HD)\n"
                + "Refresh Rate: 60 Hz\n"
                + "Brightness: 250 nits\n"
                + "Color Coverage: 45% NTSC\n"
                + "Features: Anti-glare, TÜV Rheinland-certified\n\n"
                + "\uD83D\uDD39 Audio\n"
                + "Technology: SonicMaster\n"
                + "Built-in: Speakers & Array Microphone\n\n"
                + "\uD83D\uDD39 Connectivity\n"
                + "Wi-Fi: Wi-Fi 6E (802.11ax) (Dual band) 1×1\n"
                + "Bluetooth: Bluetooth 5.3\n",R.drawable.vivobook));
        products.add(new Product("AKG N700NCM2 Wireless Headphones", 3790000.0, 3990000.0, "Test mo ta 2", R.drawable.product_image));
        products.add(new Product("iPad Air 6 M2", 13990000.0, 14990000.0, "Test mo ta 3",R.drawable.ipab_air_6_m2));
        products.add(new Product("Apple Watch Series 9", 6999000.0, 7990000.0, "Test mo ta 4",R.drawable.apple_watch_sr9));
        products.add(new Product("AirPods Pro 2", 5590000.0, 6190000.0, "Test mo ta 5",R.drawable.airpod_pro_2));
        products.add(new Product("Loa Bluetooth Beats Pill", 3990000.0, 4290000.0, "Test mo ta 6",R.drawable.beat_pill));
        products.add(new Product("Tivi Xiaomi A Pro 4K", 11790000.0, 14990000.0, "Test mo ta 7",R.drawable.tv_xiaomi));
        products.add(new Product("Magic Keyboard", 3499000.0, 4990000.0, "Test mo ta 8",R.drawable.magic_key));
// Không giảm giá


//         Setup RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Hiển thị 2 cột
        recyclerView.setHasFixedSize(true); // Tối ưu hiệu suất
        adapter = new ProductAdapter(MainActivity.this, products);
        recyclerView.setAdapter(adapter);

        viewFlipper = findViewById(R.id.viewFlipper);
        for (int image : images){
            addImageToFlipper(image);
        }
        // Áp dụng hiệu ứng chuyển đổi
//        viewFlipper.setInAnimation(this, R.anim.slide_in_right);
//        viewFlipper.setOutAnimation(this, R.anim.slide_out_left);

        viewFlipper.setFlipInterval(10000); // Chuyển banner mỗi 10 giây
        viewFlipper.startFlipping();

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        btnPrev.setOnClickListener(v -> {
            if (viewFlipper != null) {
                viewFlipper.setInAnimation(this, R.anim.slide_in_left);
                viewFlipper.setOutAnimation(this, R.anim.slide_out_right);
                viewFlipper.stopFlipping();
                viewFlipper.showPrevious();
                viewFlipper.startFlipping();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (viewFlipper != null) {
                viewFlipper.setInAnimation(this, R.anim.slide_in_right);
                viewFlipper.setOutAnimation(this, R.anim.slide_out_left);
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
            intent.putExtra("product", product);
            startActivity(intent);
    }
}