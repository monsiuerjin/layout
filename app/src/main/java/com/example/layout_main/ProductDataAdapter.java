package com.example.layout_main;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductDataAdapter extends RecyclerView.Adapter<ProductDataAdapter.ProductViewHolder> {
    ArrayList<Product> lstProduct;
    Context context;
    ProductCallback productCallback;

    public ProductDataAdapter(ArrayList<Product> lstProduct) {
        this.lstProduct = lstProduct;
    }

    public void setCallback(ProductCallback callback) {
        this.productCallback = callback;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productView = inflater.inflate(R.layout.layoutitem, parent, false);
        return new ProductViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product item = lstProduct.get(position);

        holder.tvName.setText(item.getName());
        holder.tvPrice.setText(item.getPrice() + " VNĐ");
        holder.tvDescription.setText(item.getDescription());

        // Xử lý hiển thị giá cũ (gạch ngang nếu có)
        if (item.getOldPrice() > 0) {
            holder.tvOldPrice.setVisibility(View.VISIBLE);
            holder.tvOldPrice.setText(item.getOldPrice() + " VNĐ");
            holder.tvOldPrice.setPaintFlags(holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tvOldPrice.setVisibility(View.GONE);
        }

        // Hiển thị ảnh từ thư mục assets
        holder.imProduct.setImageBitmap(Utils.convertToBitmapFromAssets(context, item.getImagePath()));

        // Xử lý sự kiện chỉnh sửa và xóa
        holder.imDelete.setOnClickListener(view -> productCallback.onItemDeleteClicked(item, position));
        holder.imEdit.setOnClickListener(view -> productCallback.onItemEditClicked(item, position));
    }

    @Override
    public int getItemCount() {
        return lstProduct.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imProduct, imEdit, imDelete;
        TextView tvName, tvPrice, tvOldPrice, tvDescription;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imProduct = itemView.findViewById(R.id.ivProductImage);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            tvOldPrice = itemView.findViewById(R.id.tvProductOldPrice);
            tvDescription = itemView.findViewById(R.id.tvProductDescription);
            imEdit = itemView.findViewById(R.id.ivEditProduct);
            imDelete = itemView.findViewById(R.id.ivDeleteProduct);
        }
    }

    public interface ProductCallback {
        void onItemDeleteClicked(Product product, int position);
        void onItemEditClicked(Product product, int position);
    }
}
