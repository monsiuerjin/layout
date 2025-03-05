package com.example.layout_main;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnQuantityChangeListener {

    private List<CartItem> cartItems;
    private CartAdapter cartAdapter;
    private TextView txtTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        // Ánh xạ TextView hiển thị tổng giá
        txtTotalPrice = findViewById(R.id.txt_total_price);

        // Danh sách giỏ hàng
        cartItems = new ArrayList<>();
        for (Product product : CartManager.getCartItems()) {
            cartItems.add(new CartItem(
                    product.getName(),
                    (int) product.getPrice(), // Chuyển sang int để tính toán chính xác
                    product.getImagePath(),  // Sử dụng imagePath (String)
                    1 // Mặc định số lượng là 1
            ));
        }

        // Thiết lập RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo adapter với danh sách sản phẩm
        cartAdapter = new CartAdapter(this, cartItems, this);
        recyclerView.setAdapter(cartAdapter);

        // Cập nhật tổng tiền ban đầu
        updateTotalPrice();

        // Xử lý nút Back để quay lại màn hình trước
        ImageView btnBack = findViewById(R.id.btn_back_cart);
        btnBack.setOnClickListener(v -> finish());
    }

    // Cập nhật tổng giá khi số lượng thay đổi
    @Override
    public void onQuantityChanged() {
        updateTotalPrice();
    }

    // Tính tổng giá của giỏ hàng
    private void updateTotalPrice() {
        int total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        txtTotalPrice.setText("Tổng: " + formatCurrency(total));
    }

    // Định dạng tiền tệ
    private String formatCurrency(int amount) {
        DecimalFormat formatter = new DecimalFormat("#,###,###đ");
        return formatter.format(amount);
    }
}
