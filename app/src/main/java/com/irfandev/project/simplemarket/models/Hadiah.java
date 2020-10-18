package com.irfandev.project.simplemarket.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * created by Irfan Assidiq
 * email : assidiq.irfan@gmail.com
 **/
public class Hadiah implements Serializable {
    @SerializedName("id")
    public int id;

    @SerializedName("namahadiah")
    public String namahadiah;

    @SerializedName("point")
    public int point;

    @SerializedName("banyakitem")
    public String banyakitem;
}
