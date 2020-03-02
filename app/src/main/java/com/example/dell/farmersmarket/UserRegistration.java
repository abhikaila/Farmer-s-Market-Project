package com.example.dell.farmersmarket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.farmersmarket.Sell_fragment.SellProduct;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserRegistration extends AppCompatActivity {

    TextInputEditText mUserName, mAddress1, mAddress2, mCity, mDistrict, mState, mMobileNo, mPassword;
    ImageView mUserImage;
    Button mRegisterUSer,mAddUserImageBtn_registration;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._user_registration);

        mUserName = findViewById(R.id.userNameEt_reg);
        mAddress1 = findViewById(R.id.address1Et_reg);
        mAddress2 = findViewById(R.id.address2Et_reg);
        mCity = findViewById(R.id.cityEt_reg);
        mDistrict = findViewById(R.id.districtEt_reg);
        mState = findViewById(R.id.stateEt_reg);
        mMobileNo = findViewById(R.id.mobileNoEt_reg);
        mPassword = findViewById(R.id.passwordEt_reg);
        mAddUserImageBtn_registration = findViewById(R.id.AddUserImageBtn_registration);

        mUserImage = findViewById(R.id.userImage_registration);

        mAddUserImageBtn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });

        mRegisterUSer = findViewById(R.id.RegisterNewUseData);

        mRegisterUSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUserData();
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

    @Override
    public void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                mUserImage.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    private void RegisterUserData() {
        final String imageL = getStringImage(bitmap);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                new Urls().getRegisterNewUserData(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("Successful.")){
                            Toast.makeText(UserRegistration.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserRegistration.this, Login.class);
                            User user = new User(mMobileNo.getText().toString(),
                                    mPassword.getText().toString());
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{
                            Toast.makeText(UserRegistration.this, "Error !!!!.", Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserRegistration.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("image",imageL);
                params.put("name",mUserName.getText().toString());
                params.put("address1",mAddress1.getText().toString());
                params.put("address2",mAddress2.getText().toString());
                params.put("city",mCity.getText().toString());
                params.put("district",mDistrict.getText().toString());
                params.put("state",mState.getText().toString());
                params.put("mobileNo",mMobileNo.getText().toString());
                params.put("password",mPassword.getText().toString());
                return  params;
            }
        };

        Volley.newRequestQueue(UserRegistration.this).add(stringRequest);
    }
}
