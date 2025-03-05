package com.example.layout_main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView productImage;
    TextView productName;
    TextView productPrice;
    TextView productOldPrice;
    TextView productDesc;
    TextView detailName, detailPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Ánh xạ View
        ImageView btnBack = findViewById(R.id.btn_back);
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

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        double price = intent.getDoubleExtra("price", 0.0);
        double oldPrice = intent.getDoubleExtra("oldPrice", 0.0);
        String details = intent.getStringExtra("description");
        String imagePath = intent.getStringExtra("imagePath"); // Lấy đường dẫn ảnh từ Intent

        // Xử lý sự kiện nút Back
        btnBack.setOnClickListener(v -> finish());

        // Hiển thị thông tin sản phẩm
        productName.setText(name);
        productPrice.setText(formatPrice(price));
        productOldPrice.setText(formatPrice(oldPrice));
        productOldPrice.setPaintFlags(productOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        productDesc.setText(details);

        // Load ảnh từ đường dẫn (không dùng Glide)
        if (imagePath != null && !imagePath.isEmpty()) {
            Bitmap bitmap = Utils.convertToBitmapFromAssets(this, imagePath);
            if (bitmap != null) {
                productImage.setImageBitmap(bitmap);
            } else {
                productImage.setImageResource(R.drawable.ic_launcher_background);
            }
        } else {
            productImage.setImageResource(R.drawable.ic_launcher_background);
        }


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

        // Xử lý RatingBar
        RatingBar ratingBar = findViewById(R.id.detail_rating_bar);
        ratingBar.setRating(4);
        ratingBar.getProgressDrawable().setColorFilter(Color.parseColor("#FFD700"), PorterDuff.Mode.SRC_ATOP);

        btnAddToCart.setOnClickListener(v -> {
            try {
                // Lấy tên và giá sản phẩm từ giao diện
                String productNameText = detailName.getText().toString().trim();
                String productPriceText = detailPrice.getText().toString().replace("₫", "").replace(",", "").trim(); // Đổi tên biến để tránh trùng

                if (productPriceText.isEmpty()) {
                    Toast.makeText(ProductDetailActivity.this, "Lỗi: Giá sản phẩm không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double productPrice = Double.parseDouble(productPriceText);

                // Tạo đối tượng CartItem
                CartItem cartItem = new CartItem(productNameText,(int) productPrice, imagePath, 1);

                // Thêm vào giỏ hàng
                CartManager.addToCart(ProductDetailActivity.this, cartItem);

                Toast.makeText(ProductDetailActivity.this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(ProductDetailActivity.this, "Lỗi: Định dạng giá không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });

        // Sự kiện bấm nút "Mua ngay"
        btnBuyNow.setOnClickListener(v ->
                Toast.makeText(ProductDetailActivity.this, "Chuyển đến trang thanh toán!", Toast.LENGTH_SHORT).show()
        );
    }

    private String formatPrice(double price) {
        return String.format("%,.0f ₫", price);
    }
}
