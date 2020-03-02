package com.example.dell.farmersmarket.Sell_fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.farmersmarket.Login;
import com.example.dell.farmersmarket.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SellProductAdapter extends RecyclerView.Adapter<SellProductAdapter.UserViewHolder> {
    private Context context, applicationContext;
    private List<SellProductModel> productList;
    private FragmentManager fragmentManager;
    private Fragment mFragment;
    private Bundle mBundle;
    AlertDialog.Builder builder;

    public SellProductAdapter(Context context, List<SellProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_sell_layout, null);
        UserViewHolder holder = new UserViewHolder(view);

        builder = new AlertDialog.Builder(context);
        builder.setTitle("Name");
        // set the custom layout
        final View customLayout = inflater.inflate(R.layout.sell_one_product_details, null);
        builder.setView(customLayout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder holder, final int i) {
        final SellProductModel product= productList.get(i);

        holder.mName_sell.setText(product.getName());
        holder.mCategory_sell.setText(product.getCategory());
        holder.mQuantity_sell.setText(product.getQuantity());
        final String ImageURL = "http://192.168.43.205/FarmersMarket/images/"+product.getImage();
        Picasso.get().load(ImageURL).fit().into(holder.mProductImage);

        holder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final SellProductModel item = productList.get(i);

//                AlertDialog dialog = builder.create();
//                dialog.show();

//               Intent intent = new Intent(context, Login.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);

//                applicationContext = SellProduct.getContextOfApplication();
//                Activity activity = (Activity) context;
//                AppCompatActivity activity = (AppCompatActivity) context;
//                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
//                FragmentManager fragmentManager = activity.getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.frame_container, new sellOneProductDetail());
//                fragmentTransaction.commit();
                Toast.makeText(context, "Click "+product.getName(), Toast.LENGTH_SHORT).show();

//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                Fragment myFragment = new sellOneProductDetail();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, myFragment).addToBackStack(null).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        TextView mName_sell, mCategory_sell, mQuantity_sell;
        ImageView mProductImage, medit_sell;
        CardView mParentLayout;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            mName_sell = itemView.findViewById(R.id.Name_Sell);
            mProductImage = itemView.findViewById(R.id.ProductImage_sell);
            medit_sell = itemView.findViewById(R.id.edit_sell);
            mCategory_sell = itemView.findViewById(R.id.category_sell);
            mQuantity_sell = itemView.findViewById(R.id.Quantity_sell);
            mParentLayout = itemView.findViewById(R.id.parent_cardView);
        }
    }
}
