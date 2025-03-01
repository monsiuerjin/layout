package com.example.layout_main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView productImage;
    TextView productName;
    TextView productPrice;
    TextView productOldPrice;
    TextView productDesc;
    TextView detailName, detailPrice;


    @SuppressWarnings("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Ánh xạ View
        ImageView btnBack = findViewById(R.id.btn_back); // Thêm nút Back
        TextView description = findViewById(R.id.detail_description);
        Button btnToggle = findViewById(R.id.btn_toggle_description);
        Button btnAddToCart = findViewById(R.id.btn_add_to_cart);
        Button btnBuyNow = findViewById(R.id.btn_buy_now);
        detailName = findViewById(R.id.detail_name);
        detailPrice = findViewById(R.id.detail_price);

        productImage = findViewById(R.id.detail_image);
        productName = findViewById(R.id.detail_name);
        productPrice = findViewById(R.id.detail_price);
        productOldPrice = findViewById(R.id.detail_old_price);

        productDesc = findViewById(R.id.detail_description);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        double price = intent.getDoubleExtra("price", 0.0);
        double oldPrice = intent.getDoubleExtra("oldPrice", 0.0);
        String details = intent.getStringExtra("description");
        int imageResourceId = intent.getIntExtra("imageResourceId", 0);


        // Xử lý sự kiện nút Back
        btnBack.setOnClickListener(v -> {
            finish(); // Đóng Activity hiện tại, quay về Activity trước đó
        });
        //TextView oldPrice = findViewById(R.id.detail_old_price);
        //oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        // Nội dung mô tả
//        String details = "\uD83D\uDD39 Processor & Graphics\n"
//                + "Graphics Card: Intel Iris Xe Graphics\n"
//                + "CPU: Intel Core i5-1235U (1.3 GHz, 12M Cache, up to 4.4 GHz, 10 cores)\n\n"
//                + "\uD83D\uDD39 Memory & Storage\n"
//                + "RAM: 16GB DDR4 (8GB DDR4 Onboard + 8GB DDR4 SO-DIMM)\n"
//                + "Storage: 512GB M.2 NVMe PCIe 3.0 SSD\n\n"
//                + "\uD83D\uDD39 Display\n"
//                + "Size: 15.6 inches\n"
//                + "Resolution: 1920 x 1080 pixels (Full HD)\n"
//                + "Refresh Rate: 60 Hz\n"
//                + "Brightness: 250 nits\n"
//                + "Color Coverage: 45% NTSC\n"
//                + "Features: Anti-glare, TÜV Rheinland-certified\n\n"
//                + "\uD83D\uDD39 Audio\n"
//                + "Technology: SonicMaster\n"
//                + "Built-in: Speakers & Array Microphone\n\n"
//                + "\uD83D\uDD39 Connectivity\n"
//                + "Wi-Fi: Wi-Fi 6E (802.11ax) (Dual band) 1×1\n"
//                + "Bluetooth: Bluetooth 5.3\n";

        // Hiển thị nội dung rút gọn ban đầu
        productName.setText(name);
//        productPrice.setText(String.format("%.0f ₫", price));
//        productOldPrice.setText(String.format("%.0f ₫", oldPrice));
        productPrice.setText(formatPrice(price));
        productOldPrice.setText(formatPrice(oldPrice));
        productOldPrice.setPaintFlags(productOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        productDesc.setText(details);
        productImage.setImageResource(imageResourceId);
        //description.setText(details);
        productDesc.setMaxLines(5);
        productDesc.setEllipsize(android.text.TextUtils.TruncateAt.END);

        // Xử lý mở rộng / thu gọn nội dung mô tả
        boolean[] isExpanded = {false};

        btnToggle.setOnClickListener(v -> {
            if (isExpanded[0]) {
                description.setMaxLines(5);
                btnToggle.setText("Xem thêm ▼");
                description.setForeground(getResources().getDrawable(R.drawable.gradient_fade));
            } else {
                description.setMaxLines(Integer.MAX_VALUE);
                btnToggle.setText("Thu gọn ▲");
                description.setForeground(null);
            }
            isExpanded[0] = !isExpanded[0];
        });
        // Tìm RatingBar
        RatingBar ratingBar = findViewById(R.id.detail_rating_bar);

        // Cách đổi màu từng sao mà không ảnh hưởng đến sao chưa chọn
        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            if (rating >= 4) {
                ratingBar1.getProgressDrawable().setColorFilter(Color.parseColor("#FFD700"), PorterDuff.Mode.SRC_ATOP);
            } else {
                ratingBar1.getProgressDrawable().setColorFilter(Color.parseColor("#757575"), PorterDuff.Mode.SRC_ATOP);
            }
        });

        // Đặt giá trị ban đầu là 4 sao và đổi màu
        ratingBar.setRating(4);
        ratingBar.getProgressDrawable().setColorFilter(Color.parseColor("#FFD700"), PorterDuff.Mode.SRC_ATOP);

        // Xử lý sự kiện khi bấm nút
        btnAddToCart.setOnClickListener(v -> {
            try {
                // Lấy tên sản phẩm
                final String finalName = detailName.getText().toString().trim();

                // Lấy giá sản phẩm và loại bỏ ký tự ₫, dấu phẩy
                String priceText = detailPrice.getText().toString().replace("₫", "").replace(",", "").trim();

                // Lấy ảnh sản phẩm từ Intent
                final int imageResId = getIntent().getIntExtra("imageResourceId", 0);

                // Kiểm tra nếu giá trống
                if (priceText.isEmpty()) {
                    Toast.makeText(ProductDetailActivity.this, "Lỗi: Giá sản phẩm không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Chuyển đổi giá trị sang số
                final double finalPrice = Double.parseDouble(priceText);

                // Tạo sản phẩm với Tên, Giá, Ảnh
                Product product = new Product(finalName, finalPrice, 0, "", imageResId);

                // Thêm vào giỏ hàng
                CartManager.addToCart(product);

                // Hiển thị thông báo
                Toast.makeText(ProductDetailActivity.this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(ProductDetailActivity.this, "Lỗi: Định dạng giá không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });


        btnBuyNow.setOnClickListener(v ->
                Toast.makeText(ProductDetailActivity.this, "Chuyển đến trang thanh toán!", Toast.LENGTH_SHORT).show()
        );
    }
    private String formatPrice(double price) {
            return String.format("%,.0f ₫", price);
    }
}
