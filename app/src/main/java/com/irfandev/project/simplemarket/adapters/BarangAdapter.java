package com.irfandev.project.simplemarket.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irfandev.project.simplemarket.R;
import com.irfandev.project.simplemarket.models.Barang;

import java.util.ArrayList;

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
            Barang barang = barangs.get(position);
            holder.tvNamaItem.setText(barang.namabarang);
//            int stock = barang.stock;
//            holder.tvStock.setText(stock);
//            holder.tvPrice.setText((int) barang.hargabarang);
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
}
