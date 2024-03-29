package com.sanjnan.shopping.apps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ProductActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_availability);
        Bundle bundle = getIntent().getExtras();
        final String productId = bundle.getString(Constants.PRODUCT_ID);
        final float price = bundle.getFloat(Constants.PRICE, 0.0f);
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
                    Intent intent = new Intent(ProductActivity.this, ProductAvailabilityActivity.class);
                    Bundle b = new Bundle();
                    b.putString(Constants.PRODUCT_ID, productId);
                    b.putFloat(Constants.PRICE, price);
                    intent.putExtras(b);
                    startActivity(intent);

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
        {
            TextView tv = findViewById(R.id.currency);
            tv.setText(p.getCurrencySymbol());
        }
        {
            TextView tv = findViewById(R.id.price);
            tv.setText(String.format("%.2f", price));
        }
    }
}
