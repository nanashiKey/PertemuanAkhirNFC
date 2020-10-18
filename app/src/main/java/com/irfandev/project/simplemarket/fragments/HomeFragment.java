package com.irfandev.project.simplemarket.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.IOException;
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
                    barangAdapter = new BarangAdapter(getActivity().getApplicationContext(), barangs);
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
    }
}
