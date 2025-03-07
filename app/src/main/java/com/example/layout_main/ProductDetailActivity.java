package com.example.layout_main;

import android.content.Intent;
import android.graphics.Bitmap;
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
import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView productImage;
    private TextView productName, productPrice, productOldPrice, productDesc;
    private static List<CartItem> tempCart = new ArrayList<>(); // Giỏ hàng tạm thời

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ImageView btnBack = findViewById(R.id.btn_back);
        Button btnToggle = findViewById(R.id.btn_toggle_description);
        Button btnAddToCart = findViewById(R.id.btn_add_to_cart);
        Button btnBuyNow = findViewById(R.id.btn_buy_now);
        RatingBar ratingBar = findViewById(R.id.detail_rating_bar);

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
        String imagePath = intent.getStringExtra("imagePath");

        // Xử lý sự kiện nút Back
        btnBack.setOnClickListener(v -> finish());

        // Hiển thị thông tin sản phẩm
        productName.setText(name);
        productPrice.setText(formatPrice(price));
        productOldPrice.setText(formatPrice(oldPrice));
        productOldPrice.setPaintFlags(productOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        productDesc.setText(details);

        // Hiển thị hình ảnh sản phẩm
        Bitmap bitmap = Utils.convertToBitmapFromAssets(this, imagePath);
        if (bitmap != null) {
            productImage.setImageBitmap(bitmap);
        } else {
            productImage.setImageResource(R.drawable.ic_launcher_background);
        }

        // RatingBar màu vàng
        ratingBar.setRating(4);
        ratingBar.getProgressDrawable().setColorFilter(Color.parseColor("#FFD700"), PorterDuff.Mode.SRC_ATOP);

        // Nút "Thêm vào giỏ hàng"
        btnAddToCart.setOnClickListener(v -> {
            CartItem cartItem = new CartItem(name, (int) price, imagePath, 1);

            // Kiểm tra sản phẩm có trong giỏ hàng chưa
            boolean found = false;
            for (CartItem item : tempCart) {
                if (item.getName().equals(cartItem.getName())) {
                    item.setQuantity(item.getQuantity() + 1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                tempCart.add(cartItem);
            }

            Toast.makeText(ProductDetailActivity.this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
        });

        // Nút "Mua ngay"
        btnBuyNow.setOnClickListener(v ->
                Toast.makeText(ProductDetailActivity.this, "Chuyển đến trang thanh toán!", Toast.LENGTH_SHORT).show()
        );
    }

    private String formatPrice(double price) {
        return String.format("%,.0f ₫", price);
    }

    // Thêm phương thức này để các class khác có thể lấy danh sách giỏ hàng tạm thời
    public static List<CartItem> getTempCart() {
        return tempCart;
    }
}
