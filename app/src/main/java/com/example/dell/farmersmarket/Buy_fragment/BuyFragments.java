package com.example.dell.farmersmarket.Buy_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.farmersmarket.R;

public class BuyFragments extends Fragment {

    public BuyFragments() {
        // Required empty public constructor
    }

    public static BuyFragments newInstance(String param1, String param2) {
        BuyFragments fragment = new BuyFragments();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.buy_activity, container, false);
        return view;
    }
}