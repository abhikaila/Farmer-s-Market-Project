package com.example.dell.farmersmarket.Sell_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.farmersmarket.HomePage;
import com.example.dell.farmersmarket.R;
import com.example.dell.farmersmarket.SharedPrefManager;
import com.example.dell.farmersmarket.Urls;
import com.example.dell.farmersmarket.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellProduct extends Fragment {

    public Context applicationContext;
    RecyclerView mSellProductRV;
    List<SellProductModel> sellProductList;
    SellProductAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sell_product, container, false);

        applicationContext = HomePage.getContextOfApplication();
        declaration(view);

        loadUsers();



        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new AddProductForSell());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    private void loadUsers() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                new Urls().getGetSellingProductPath(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray places = new JSONArray(response);

                            for (int i=0;i<places.length();i++){
                                JSONObject placesObject = places.getJSONObject(i);

                                String Name = placesObject.getString("Name");
                                String Category = placesObject.getString("Category");
                                String Quantity = placesObject.getString("Quantity");
                                String Image = placesObject.getString("Image");
                                String City = placesObject.getString("City");

                                SellProductModel sellProductModel = new SellProductModel(Name, Category, City, Image, Quantity);
                                sellProductList.add(sellProductModel);

                            }

                            adapter = new SellProductAdapter(applicationContext, sellProductList);
                            mSellProductRV.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                User user = SharedPrefManager.getInstance(getContext()).getUser();

                Map<String, String> params = new HashMap<>();
//                cityName = getIntent().getExtras().get("cityName").toString();
//                String category = getIntent().getExtras().get("category").toString();
//                params.put("cityName", cityName);
                params.put("mobileNo", user.getMobileNo());
                return params;
            }
        };
        Volley.newRequestQueue(applicationContext).add(stringRequest);

    }

    private void declaration(View view) {
        sellProductList = new ArrayList<>();

        mSellProductRV = view.findViewById(R.id.sell_list_recycler_view);
        mSellProductRV.setHasFixedSize(true);
        mSellProductRV.setLayoutManager(new LinearLayoutManager(applicationContext));

    }
}
