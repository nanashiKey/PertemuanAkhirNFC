package com.irfandev.project.simplemarket;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.irfandev.project.simplemarket.helpers.APIServices;
import com.irfandev.project.simplemarket.helpers.Const;
import com.irfandev.project.simplemarket.models.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * created by Irfan Assidiq
 * email : assidiq.irfan@gmail.com
 **/
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText etEmail, etPassword, etRePassword, etUsername;
    Button btnRegister;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etRePassword = findViewById(R.id.etRePassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    public void doRegister(String username, String email, String password, String rePassword){
        if(username.equals("") && email.equals("") && password.equals("") && rePassword.equals("")){
            Toast.makeText(this, "silahkan isi setiap kolom kosong", Toast.LENGTH_SHORT).show();
        }else{
            if(password.equals(rePassword)){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Const.BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIServices apiServicess = retrofit.create(APIServices.class);
                apiServicess.userRegister(username, email, password, 0)
                        .enqueue(new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                if(response.isSuccessful()){
                                    DefaultResponse defRes = response.body();
                                    Toast.makeText(RegisterActivity.this, defRes.message, Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(RegisterActivity.this, "register gagal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                Toast.makeText(RegisterActivity.this, "register gagal", Toast.LENGTH_SHORT).show();
                            }
                        });
            }else{
                Toast.makeText(this, "password tidak sama", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        String email, username, password, rePass;
        email = etEmail.getText().toString();
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        rePass = etRePassword.getText().toString();
        doRegister(username, email, password, rePass);
    }
}
