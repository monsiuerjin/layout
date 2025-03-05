package com.example.layout_main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity implements ProductDataAdapter.ProductCallback {
    RecyclerView rvProductList;
    ArrayList<Product> lstProduct;
    ProductDataAdapter productAdapter;
    FloatingActionButton fbAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        rvProductList = findViewById(R.id.rvProductList);
        fbAdd = findViewById(R.id.fbAddProduct);
        fbAdd.setOnClickListener(view -> addProductDialog());

        lstProduct = ProductDataQuery.getAll(this);
        productAdapter = new ProductDataAdapter(lstProduct);
        productAdapter.setCallback(this);

        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        rvProductList.setAdapter(productAdapter);
    }

    void addProductDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DataActivity.this);
        alertDialog.setTitle("Thêm sản phẩm mới");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_product, null);
        alertDialog.setView(dialogView);

        EditText edName = dialogView.findViewById(R.id.edProductName);
        EditText edPrice = dialogView.findViewById(R.id.edProductPrice);
        EditText edOldPrice = dialogView.findViewById(R.id.edProductOldPrice);
        EditText edDescription = dialogView.findViewById(R.id.edProductDescription);
        EditText edImagePath = dialogView.findViewById(R.id.edProductImage);

        alertDialog.setPositiveButton("Thêm", (dialog, which) -> {
            String name = edName.getText().toString();
            String priceText = edPrice.getText().toString();
            String oldPriceText = edOldPrice.getText().toString();
            String description = edDescription.getText().toString();
            String imagePath = edImagePath.getText().toString();

            if (name.isEmpty() || priceText.isEmpty() || imagePath.isEmpty()) {
                Toast.makeText(DataActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                double price = Double.parseDouble(priceText);
                double oldPrice = oldPriceText.isEmpty() ? 0 : Double.parseDouble(oldPriceText);
                Product product = new Product(0, name, price, oldPrice, description, imagePath);

                long id = ProductDataQuery.insert(DataActivity.this, product);
                if (id > 0) {
                    Toast.makeText(DataActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    resetData();
                    dialog.dismiss();
                }
            }
        });

        alertDialog.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        alertDialog.create().show();
    }

    void updateProductDialog(Product product) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DataActivity.this);
        alertDialog.setTitle("Cập nhật sản phẩm");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_product, null);
        alertDialog.setView(dialogView);

        EditText edName = dialogView.findViewById(R.id.edProductName);
        EditText edPrice = dialogView.findViewById(R.id.edProductPrice);
        EditText edOldPrice = dialogView.findViewById(R.id.edProductOldPrice);
        EditText edDescription = dialogView.findViewById(R.id.edProductDescription);
        EditText edImagePath = dialogView.findViewById(R.id.edProductImage);

        edName.setText(product.getName());
        edPrice.setText(String.valueOf(product.getPrice()));
        edOldPrice.setText(String.valueOf(product.getOldPrice()));
        edDescription.setText(product.getDescription());
        edImagePath.setText(product.getImagePath());

        alertDialog.setPositiveButton("Cập nhật", (dialog, which) -> {
            product.setName(edName.getText().toString());
            product.setPrice(Double.parseDouble(edPrice.getText().toString()));
            product.setOldPrice(Double.parseDouble(edOldPrice.getText().toString()));
            product.setDescription(edDescription.getText().toString());
            product.setImagePath(edImagePath.getText().toString());

            int id = ProductDataQuery.update(DataActivity.this, product);
            if (id > 0) {
                Toast.makeText(DataActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                resetData();
                dialog.dismiss();
            }
        });

        alertDialog.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        alertDialog.create().show();
    }

    @Override
    public void onItemDeleteClicked(Product product, int position) {
        boolean result = ProductDataQuery.delete(DataActivity.this, product.getId());
        if (result) {
            Toast.makeText(DataActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            resetData();
        } else {
            Toast.makeText(DataActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemEditClicked(Product product, int position) {
        updateProductDialog(product);
    }

    void resetData() {
        lstProduct.clear();
        lstProduct.addAll(ProductDataQuery.getAll(this));
        productAdapter.notifyDataSetChanged();
    }
}