package com.example.layout_main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {
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

        // Xử lý sự kiện nút Back
        btnBack.setOnClickListener(v -> {
            finish(); // Đóng Activity hiện tại, quay về Activity trước đó
        });

        // Nội dung mô tả
        String details = "\uD83D\uDD39 Processor & Graphics\n"
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
                + "Bluetooth: Bluetooth 5.3\n";

        // Hiển thị nội dung rút gọn ban đầu
        description.setText(details);
        description.setMaxLines(3);
        description.setEllipsize(android.text.TextUtils.TruncateAt.END);

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

        // Xử lý sự kiện khi bấm nút
        btnAddToCart.setOnClickListener(v ->
                Toast.makeText(ProductDetailActivity.this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show()
        );

        btnBuyNow.setOnClickListener(v ->
                Toast.makeText(ProductDetailActivity.this, "Chuyển đến trang thanh toán!", Toast.LENGTH_SHORT).show()
        );
    }
}
