package com.irfandev.project.simplemarket.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irfandev.project.simplemarket.MainActivity;
import com.irfandev.project.simplemarket.R;
import com.irfandev.project.simplemarket.helpers.APIServices;
import com.irfandev.project.simplemarket.helpers.Const;
import com.irfandev.project.simplemarket.helpers.PrefsHelper;
import com.irfandev.project.simplemarket.models.Barang;
import com.irfandev.project.simplemarket.models.DefaultResponse;

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
public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.BarangViewHolder>{

    Context ctx;
    ArrayList<Barang> barangs;
    public BarangAdapter(){ }
    public BarangAdapter(Context ctx, ArrayList<Barang> barangs){
        this.ctx = ctx;
        this.barangs = barangs;
    }
    @NonNull
    @Override
    public BarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_product, parent, false);
        return new BarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangViewHolder holder, int position) {
            final Barang barang = barangs.get(position);
            holder.tvNamaItem.setText(barang.namabarang);
            String stock = String.valueOf(barang.stock);
            holder.tvStock.setText(stock);
            String harga = String.valueOf(barang.hargabarang);
            holder.tvPrice.setText(harga);
            holder.btnBeli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int idUser = PrefsHelper.sharedInstance(ctx).getUID();
                    int idBarang = barang.id;
                    showBeliDialog(idUser, idBarang);
                }
            });
    }

    @Override
    public int getItemCount() {
        return barangs.size();
    }

    class BarangViewHolder extends RecyclerView.ViewHolder{
        TextView tvNamaItem, tvPrice, tvStock;
        LinearLayout llayout;
        Button btnBeli;
        BarangViewHolder(View itemView){
            super(itemView);
            tvNamaItem = itemView.findViewById(R.id.tvItemName);
            tvPrice = itemView.findViewById(R.id.tvItemPrice);
            tvStock = itemView.findViewById(R.id.tvItemStock);
            llayout = itemView.findViewById(R.id.llayout);
            btnBeli = itemView.findViewById(R.id.btnBeli);
        }
    }

    private void showBeliDialog(final int iduser, final int idbarang){
        final Dialog beli = new Dialog(ctx);
        beli.setContentView(R.layout.pop_beliitems);
        beli.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        beli.setCancelable(false);
        Button btnBeli = beli.findViewById(R.id.btnBeli);
        Button btnCancel = beli.findViewById(R.id.btnCancel);
        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beli.dismiss();
                beliItem(idbarang, iduser);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beli.dismiss();
            }
        });
        beli.show();
    }

    private void beliItem(int idbarang, int iduser){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIServices apiServicess = retrofit.create(APIServices.class);
        apiServicess.beliBarang(iduser, idbarang)
                .enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                       if(response.isSuccessful()){
                           Toast.makeText(ctx, "barang berhasil dibeli", Toast.LENGTH_SHORT).show();
                           ctx.startActivity(new Intent(ctx, MainActivity.class));
                           ((Activity) ctx).finish();
                           ((Activity) ctx).overridePendingTransition(0,0);
                       }else{
                           Toast.makeText(ctx, "barang gagal dibeli", Toast.LENGTH_SHORT).show();
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
