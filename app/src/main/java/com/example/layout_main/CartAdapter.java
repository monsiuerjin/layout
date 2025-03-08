package com.example.layout_main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartItem> cartItems;
    private Context context;
    private OnQuantityChangeListener quantityChangeListener;

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }

    public CartAdapter(Context context, List<CartItem> cartItems, OnQuantityChangeListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.quantityChangeListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        holder.name.setText(item.getName());
        holder.price.setText(formatCurrency(item.getPrice()));
        holder.quantity.setText(String.valueOf(item.getQuantity()));
        loadImageFromAssets(holder.image, item.getImagePath());

        holder.btnDecrease.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyItemChanged(position);
                quantityChangeListener.onQuantityChanged();
            }
        });

        holder.btnIncrease.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyItemChanged(position);
            quantityChangeListener.onQuantityChanged();
        });

        holder.btnRemove.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            quantityChangeListener.onQuantityChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity;
        ImageView image;
        ImageButton btnIncrease, btnDecrease, btnRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_item_name);
            price = itemView.findViewById(R.id.cart_item_price);
            image = itemView.findViewById(R.id.cart_item_image);
            quantity = itemView.findViewById(R.id.cart_item_quantity);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
            btnRemove = itemView.findViewById(R.id.btn_remove);
        }
    }

    private void loadImageFromAssets(ImageView imageView, String imagePath) {
        try {
            InputStream inputStream = context.getAssets().open("image/" + imagePath);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    private String formatCurrency(int amount) {
        DecimalFormat formatter = new DecimalFormat("#,###,###đ");
        return formatter.format(amount);
    }
}
