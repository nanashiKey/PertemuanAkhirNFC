package com.irfandev.project.simplemarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.irfandev.project.simplemarket.helpers.APIServices;
import com.irfandev.project.simplemarket.helpers.Const;
import com.irfandev.project.simplemarket.helpers.PrefsHelper;
import com.irfandev.project.simplemarket.models.UsersResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * created by Irfan Assidiq
 * email : assidiq.irfan@gmail.com
 **/
public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextInputEditText etUsername, etPassword;
    TextView tvRegis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvRegis = findViewById(R.id.tvRegister);
        tvRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname, pass;
                uname = etUsername.getText().toString();
                pass = etPassword.getText().toString();
                doLogin(uname, pass);
            }
        });
        boolean cekLogin = PrefsHelper.sharedInstance(LoginActivity.this).getStatusLogin();
        if(cekLogin){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    public void doLogin(final String uname, String pass){
        if(uname.equals("") && pass.equals("")){
            Toast.makeText(this, "silahkan isi kolom yang kosong", Toast.LENGTH_SHORT).show();
        }else{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Const.BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            APIServices apiServicess = retrofit.create(APIServices.class);
            apiServicess.userLogin(uname, pass).enqueue(new Callback<UsersResponse>() {
                @Override
                public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login Berhasil",
                                Toast.LENGTH_SHORT).show();
                        PrefsHelper.sharedInstance(LoginActivity.this).setStatusLogin(true);
                        PrefsHelper.sharedInstance(LoginActivity.this).setUsername(uname);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Login Error",
                                Toast.LENGTH_SHORT).show();
                        try {
                            Log.e("TAGERROR", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UsersResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Login Error",
                            Toast.LENGTH_SHORT).show();
                    Log.e("TAGERROR", t.getLocalizedMessage());
                }
            });
        }
    }
}
