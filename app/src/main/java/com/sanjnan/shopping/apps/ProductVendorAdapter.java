package com.sanjnan.shopping.apps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductVendorAdapter
        extends RecyclerView.Adapter<ProductVendorAdapter.ViewHolder> {

    Map<String, Vendor> vendorMap = new HashMap<>();
    List<VendorProduct> vendorProductList = new ArrayList<>();
    private final String productId;
    private final float price;
    private ProductAvailabilityActivity context;
    // Provide a suitable constructor (depends on the kind of dataset)

    public ProductVendorAdapter(ProductAvailabilityActivity context, String productId, float price) {
        this.context = context;
        this.productId = productId;
        this.price = price;
        populateData();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public Vendor vendor;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    private void populateData() {

        Database
                .INSTANCE
                .getInstance()
                .getReference()
                .child(String.format("%s/%s", Constants.VENDOR_FOR_PRODUCTS_KEY, productId))
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        final VendorProduct vp = dataSnapshot.getValue(VendorProduct.class);
                        Database.INSTANCE.getInstance().getReference()
                                .child(String.format("%s/%s", Constants.VENDORS_KEY, vp.getVendorId()))
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot vDataSnapshot) {
                                        Vendor v = vDataSnapshot.getValue(Vendor.class);
                                        vendorProductList.add(vp);
                                        vendorMap.put(v.getId(), v);
                                        refreshDataset();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProductVendorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_vendor_availability_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final VendorProduct vp = vendorProductList.get(position);
        final Vendor v = vendorMap.get(vp.getVendorId());
        holder.vendor = v;
        Glide.with(context).load(v.getUrl())
                .into((ImageView) holder.view.findViewById(R.id.vendor_logo));
        ((RatingBar) holder.view.findViewById(R.id.vendor_rating)).setRating(v.getRating());
        ((TextView) holder.view.findViewById(R.id.vendor_name)).setText(v.getName());
        ((TextView) holder.view.findViewById(R.id.price)).setText(String.format("%.2f", vp.getPrice()));
        ((TextView) holder.view.findViewById(R.id.availability)).setText(vp.getAvailability() == 0 ? "Available Now!" : String.format("in %d days!", vp.getAvailability()));
        ((ImageButton)holder.view.findViewById(R.id.map_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VendorLocationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble(Constants.LATITUDE, v.getLatitude());
                bundle.putDouble(Constants.LONGITUDE, v.getLongitude());
                bundle.putString(Constants.VENDOR_NAME, v.getName());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return vendorProductList == null ? 0 :vendorProductList.size();
    }

    private void refreshDataset() {

        notifyDataSetChanged();
    }
}
