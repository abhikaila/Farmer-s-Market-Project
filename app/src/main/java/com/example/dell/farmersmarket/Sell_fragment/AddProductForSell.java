package com.example.dell.farmersmarket.Sell_fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class AddProductForSell extends Fragment{

//    Toolbar toolbar;
    TextInputLayout mNameIL, mPriceIL, mDescriptionIL, mQuantityIL, mCategoryIL;
    TextInputEditText mNameEt, mPriceEt, mDescriptionEt, mQuantityEt, mCategoryEt;
    String mName, mPrice, mDescription, mQuantity, mCategory;
    ImageView mProductImage;
    Button mAddProductBtn, mSubmitData;
    Context applicationContext;

//    For Image
    Bitmap bitmap;
    ProgressDialog progressDialog ;
    String GetImageNameEditText;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_product_for_sell, container, false);

//        toolbar = view.findViewById(R.id.toolbar);

        // for declare views
        declaration(view);

        //get values from text views
        getValues();
       //get images from gallery
        BtnsOnClick();

        return view;
    }

    private void BtnsOnClick() {
        mAddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });


        mSubmitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetImageNameEditText = mName;

                ImageUploadToServerFunction1();

            }
        });
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void ImageUploadToServerFunction1() {
        final String imageL = getStringImage(bitmap);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                new Urls().getServerUploadProductPath(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("Your Image Has Been Uploaded.")){
                            Toast.makeText(applicationContext, "Successful", Toast.LENGTH_SHORT).show();

                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_container, new SellProduct());
                            transaction.addToBackStack(null);
                            fragmentManager  = getFragmentManager();
                            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            transaction.commit();
                        }else{
                            Toast.makeText(applicationContext, ".", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                User user = SharedPrefManager.getInstance(getContext()).getUser();

                Map<String,String> params = new HashMap<>();
                params.put("image",imageL);
                params.put("name",mNameEt.getText().toString());
                params.put("description",mDescriptionEt.getText().toString());
                params.put("quantity",mQuantityEt.getText().toString());
                params.put("category",mCategoryEt.getText().toString());
                params.put("date",currentDate);
                params.put("time",currentTime);
                params.put("seller_id", user.getMobileNo());

                return  params;
            }
        };

        Volley.newRequestQueue(applicationContext).add(stringRequest);
    }

    @Override
    public void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(applicationContext.getContentResolver(), uri);

                mProductImage.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private void getValues() {
        mName = mNameEt.getText().toString();
        mPrice = mPriceEt.getText().toString();
        mDescription = mDescriptionEt.getText().toString();
        mQuantity = mQuantityEt.getText().toString();
        mCategory = mCategoryEt.getText().toString();
        applicationContext = HomePage.getContextOfApplication();
    }

    private void declaration(View view) {
        mNameIL = view.findViewById(R.id.productNameInputLayout);
        mNameEt = view.findViewById(R.id.productNameEditText);

        mPriceIL = view.findViewById(R.id.productpriceInputLayout);
        mPriceEt = view.findViewById(R.id.productpriceEditText);

        mDescriptionIL = view.findViewById(R.id.productDescriptionInputLayout);
        mDescriptionEt = view.findViewById(R.id.productDescriptionEditText);

        mQuantityIL = view.findViewById(R.id.productQuantityInputLayout);
        mQuantityEt = view.findViewById(R.id.productQuantityEditText);

        mCategoryIL = view.findViewById(R.id.productCategoryInputLayout);
        mCategoryEt = view.findViewById(R.id.productCategoryEditText);

        mProductImage = view.findViewById(R.id.productImage);
        mAddProductBtn = view.findViewById(R.id.AddProductImageBtn);

        mSubmitData = view.findViewById(R.id.addProductBtn);
    }

}
