package com.example.layout_main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> productList;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public ProductAdapter(Context context, List<Product> products) {
        this.productList = products;
        this.context = context;
        //this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(formatPrice(product.getPrice()));

        // Hiển thị ảnh từ drawable
        holder.productImage.setImageResource(product.getImageResourceId());

        // Hiển thị giá cũ nếu có giảm giá
        if (product.getOldPrice() > product.getPrice()) {
            holder.productOldPrice.setText(formatPrice(product.getOldPrice()));
            holder.productOldPrice.setVisibility(View.VISIBLE);
            holder.discountBadge.setVisibility(View.VISIBLE);
            holder.productOldPrice.setPaintFlags(holder.productOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            // Tính toán phần trăm giảm giá
            int discount = (int) ((1 - (product.getPrice() / product.getOldPrice())) * 100);
            holder.productDiscount.setText("-" + discount + "%");
        } else {
            holder.productOldPrice.setVisibility(View.GONE);
            holder.discountBadge.setVisibility(View.GONE);
        }

        // Xử lý sự kiện nhấp vào item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("name", product.getName());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("oldPrice", product.getOldPrice());
            intent.putExtra("description", product.getDescription());
            intent.putExtra("imageResourceId", product.getImageResourceId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productOldPrice, productDiscount, discountBadge;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImg);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productOldPrice = itemView.findViewById(R.id.productOldprice);
            productDiscount = itemView.findViewById(R.id.productDisc);
            discountBadge = itemView.findViewById(R.id.discountBadge);
        }
    }

    // Hàm định dạng giá tiền
    private String formatPrice(double price) {
        if (price >= 1_000_000) {
            return String.format("%.2f triệu ₫", price / 1_000_000);
        } else {
            return String.format("%,.0f ₫", price);
        }
    }
    // tìm kiếm
    public void updateList(List<Product> newList) {
        productList.clear();
        productList.addAll(newList);
        notifyDataSetChanged();
    }
}
