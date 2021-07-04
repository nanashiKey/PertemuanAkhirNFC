package com.irfandev.project.simplemarket.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.irfandev.project.simplemarket.R;
import com.irfandev.project.simplemarket.adapters.BarangAdapter;
import com.irfandev.project.simplemarket.helpers.APIServices;
import com.irfandev.project.simplemarket.helpers.Const;
import com.irfandev.project.simplemarket.models.Barang;
import com.irfandev.project.simplemarket.models.BarangResponse;
import com.irfandev.project.simplemarket.models.DefaultResponse;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * created by Irfan Assidiq
 * email : assidiq.irfan@gmail.com
 **/
public class HomeFragment extends Fragment {
    RecyclerView rcView;
    FloatingActionButton fab;
    RecyclerView.LayoutManager layoutManager;
    BarangAdapter barangAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rcView = view.findViewById(R.id.rcView);
        fab = view.findViewById(R.id.fab);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        barangAdapter = new BarangAdapter();
        rcView.setHasFixedSize(true);
        rcView.setLayoutManager(layoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIServices apiServicess = retrofit.create(APIServices.class);
        apiServicess.getAllBarang().enqueue(new Callback<BarangResponse>() {
            @Override
            public void onResponse(Call<BarangResponse> call, Response<BarangResponse> response) {
                if(response.isSuccessful()){
                    ArrayList<Barang> barangs = response.body().data;
                    ArrayList<Barang> barangs1 = new ArrayList<>();
                    for(int pos = 0; pos < barangs.size(); pos++){
                        if(barangs.get(pos).stock != 0){
                            barangs1.add(barangs.get(pos));
                        }
                    }
                    barangAdapter = new BarangAdapter(getActivity(), barangs1);
                    rcView.setAdapter(barangAdapter);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "item gagal di load", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("TAGERROR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BarangResponse> call, Throwable t) {
                Log.e("TAGERROR", t.getLocalizedMessage());
                Toast.makeText(getActivity().getApplicationContext(), "item gagal di load", Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadBarang();
            }
        });
    }

    private void uploadBarang(){
        final Dialog dialogUpload = new Dialog(getActivity());
        dialogUpload.setContentView(R.layout.pop_uploadbarang);
        dialogUpload.setCancelable(false);
        dialogUpload.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        final EditText etNamaBarang, etHargaBarang, etStock;
        Button btnUpload, btnCancel;
        etNamaBarang = dialogUpload.findViewById(R.id.etNamaBarang);
        etHargaBarang = dialogUpload.findViewById(R.id.etHargaBarang);
        etStock = dialogUpload.findViewById(R.id.etStock);
        btnUpload = dialogUpload.findViewById(R.id.btnUploadB);
        btnCancel = dialogUpload.findViewById(R.id.btnCancel);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaBarang = etNamaBarang.getText().toString();
                String hargaBarang = etHargaBarang.getText().toString();
                String stockBarang = etStock.getText().toString();
                if(namaBarang.equals("") || hargaBarang.equals("") || stockBarang.equals("")){
                    Toast.makeText(getActivity().getApplicationContext(), "silahkan isi kolom kosong", Toast.LENGTH_SHORT).show();
                }else{
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Const.BASEURL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    APIServices apiServicess = retrofit.create(APIServices.class);
                    apiServicess.uploadBarang(namaBarang, Long.parseLong(hargaBarang), Integer.parseInt(stockBarang))
                            .enqueue(new Callback<DefaultResponse>() {
                                @Override
                                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                    if(response.isSuccessful()){
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                response.body().message, Toast.LENGTH_SHORT).show();
                                        dialogUpload.dismiss();
                                        getActivity().startActivity(getActivity().getIntent());
                                        getActivity().finish();
                                        getActivity().overridePendingTransition(0,0);
                                    }else{
                                        Toast.makeText(getContext().getApplicationContext(), "barang gagal diupload", Toast.LENGTH_SHORT).show();
                                        try {
                                            Log.e("TAGERROR", response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                        Log.e("TAGERROR", t.getLocalizedMessage());
                                }
                            });
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpload.dismiss();
            }
        });
        dialogUpload.show();
    }
}
