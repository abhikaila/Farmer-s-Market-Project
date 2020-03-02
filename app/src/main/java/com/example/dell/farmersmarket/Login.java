package com.example.dell.farmersmarket;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextInputLayout mMobileNoInputLayout, mPasswordInputLayout;
    TextInputEditText mMobileNoEditText, mPasswordEditText;

    TextView mSignUpBtn;
    Button mLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._login);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, HomePage.class));
            return;
        }

        mMobileNoInputLayout = findViewById(R.id.loginMobileNoInputLayout);
        mMobileNoEditText = findViewById(R.id.loginMobileNoEditText);

        mPasswordInputLayout = findViewById(R.id.loginPasswordInputLayout);
        mPasswordEditText = findViewById(R.id.loginPasswordEditText);


        mSignUpBtn = findViewById(R.id.SignUp);
        mLoginBtn = findViewById(R.id.Loginbtn);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
//                Intent intent = new Intent(Login.this,HomePage.class);
//                startActivity(intent);
            }
        });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,UserRegistration.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                new Urls().getLoginUser(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("1")){
                            User user = new User(mMobileNoEditText.getText().toString(),
                                    mPasswordEditText.getText().toString());
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            Intent intent = new Intent(Login.this,HomePage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Login.this, "Wrong mobileNo or password!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("mobileNo",mMobileNoEditText.getText().toString());
                params.put("password",mPasswordEditText.getText().toString());
                return  params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

}
