package com.example.dell.farmersmarket;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dell.farmersmarket.Buy_fragment.BuyFragments;
import com.example.dell.farmersmarket.Sell_fragment.AddProductForSell;
import com.example.dell.farmersmarket.Sell_fragment.SellProduct;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ActionBar toolbar1;

    private DrawerLayout drawer;
    BottomNavigationView navigation;
    Fragment fragment;
    FragmentManager fragmentManager;
    public static Context contextOfApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._home_page);

        contextOfApplication = getApplicationContext();
        //Side navigation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.container);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Bottom Navigation Drawer
        toolbar1 = getSupportActionBar();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar1.setTitle("Shop");
        loadFragment(new BuyFragments());

        //
    }
//SideNavigation

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_buy:
                toolbar1.setTitle("Farmer's Market");
                fragment = new BuyFragments();
                loadFragment(fragment);
                navigation.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_sell:
                toolbar1.setTitle("Your Product");
                fragment = new SellProduct();
                loadFragment(fragment);
                navigation.setVisibility(View.GONE);
                break;
            case R.id.nav_logout:
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                Intent intent = new Intent(HomePage.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
//            case R.id.nav_share:
//                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.nav_send:
//                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
//                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.frame_container);

        if (fragment == null) {
            fragment = new BuyFragments();

            fm.beginTransaction()
                    .add(R.id.frame_container, fragment)
                    .commit();

        }
    }
//Bottom Navigation
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_buy:
                    toolbar1.setTitle("Home");
                    fragment = new BuyFragments();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_favourits:
                    toolbar1.setTitle("My Favourits");
//                    fragment = new GiftsFragment();
//                    loadFragment(fragment);
                    return true;
                case R.id.navigation_cart:
                    toolbar1.setTitle("Cart");
//                    fragment = new CartFragment();
//                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    toolbar1.setTitle("Profile");
//                    fragment = new ProfileFragment();
//                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        fragmentManager  = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }

    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
}
