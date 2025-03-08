package com.example.layout_main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentData;
import vn.payos.type.ItemData;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnQuantityChangeListener {

    private List<CartItem> cartItems;
    private CartAdapter cartAdapter;
    private TextView txtTotalPrice;

    //payOS thanh toán
    private static final String CLIENT_ID = "-";
    private static final String API_KEY = "-";
    private static final String CHECKSUM_KEY = "-";
    private static final String RETURN_URL = "payos-payment://success";
    private static final String CANCEL_URL = "payos-payment://failed";
    private Button btn_checkout;
    private int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        // Ánh xạ TextView hiển thị tổng giá
        txtTotalPrice = findViewById(R.id.txt_total_price);

        // Lấy giỏ hàng tạm thời từ ProductDetailActivity
        cartItems = ProductDetailActivity.getTempCart();

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

        // Xử lý nút thanh toán
        btn_checkout = findViewById(R.id.btn_checkout);
        btn_checkout.setOnClickListener(v -> {
            startPayment(total);
        });
    }

    //Xử lý thanh toán payOS
    private long generateOrderCode() {
        int randomNumber = 100000 + (int) (Math.random() * 900000);
        return randomNumber;
    }

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private void startPayment(int amount) {

        executorService.execute(() -> {
            PayOS payOS = new PayOS(CLIENT_ID, API_KEY, CHECKSUM_KEY);
            long orderCode = generateOrderCode();

            ItemData itemData = ItemData.builder()
                    .name("Thanh toán giỏ hàng")
                    .quantity(1)
                    .price(amount)
                    .build();

            PaymentData paymentData = PaymentData.builder()
                    .orderCode(orderCode)
                    .amount(amount)
                    .description("Ma hoa don " + orderCode)
                    .returnUrl(RETURN_URL)
                    .cancelUrl(CANCEL_URL)
                    .item(itemData)
                    .build();

            // Tạo link thanh toán
            try {
                CheckoutResponseData result = payOS.createPaymentLink(paymentData);
                if (result != null) {
                    String paymentUrl = result.getCheckoutUrl(); // Lấy URL thanh toán
                    openWebPage(paymentUrl);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    // Cập nhật tổng giá khi số lượng thay đổi
    @Override
    public void onQuantityChanged() {
        updateTotalPrice();
    }

    // Tính tổng giá của giỏ hàng
    private void updateTotalPrice() {
        total = 0;
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
