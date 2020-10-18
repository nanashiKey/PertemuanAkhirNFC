package com.irfandev.project.simplemarket.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * created by Irfan Assidiq
 * email : assidiq.irfan@gmail.com
 **/
public class Barang implements Serializable {
    @SerializedName("id")
    public int id;

    @SerializedName("namabarang")
    public String namabarang;

    @SerializedName("hargabarang")
    public long hargabarang;

    @SerializedName("stock")
    public int stock;
}
