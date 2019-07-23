package com.sanjnan.shopping.apps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAvailabilityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_vendor_availability);
        Bundle bundle = getIntent().getExtras();
        final String productId = bundle.getString(Constants.PRODUCT_ID);
        final float price = bundle.getFloat(Constants.PRICE, 0.0f);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.product_vendor_availability_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ProductVendorAdapter(this, productId, price));
        recyclerView.invalidate();

        Database
                .INSTANCE
                .getInstance()
                .getReference()
                .child(String.format("%s/%s", Constants.PRODUCTS_KEY, productId))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Product p = dataSnapshot.getValue(Product.class);
                        populateView(productId, price, p);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        {
            ImageButton ib = findViewById(R.id.next);
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
    private void populateView(String productId, float price, Product p) {
        {
            ImageView iv = findViewById(R.id.product_image);
            Glide.with(this).load(p.getUrl())
                    .into(iv);
        }
        {
            TextView tv = findViewById(R.id.title);
            tv.setText(p.getTitle());
        }
        {
            TextView tv = findViewById(R.id.description);
            tv.setText(p.getDetails());
        }
    }

    private void refreshDataset() {

    }
}
