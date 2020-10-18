package com.irfandev.project.simplemarket.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.irfandev.project.simplemarket.R;

/**
 * created by Irfan Assidiq
 * email : assidiq.irfan@gmail.com
 **/
public class HadiahFragment extends Fragment {
    RecyclerView rcView;
    FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hadiah, container, false);
        rcView = view.findViewById(R.id.rcView);
        fab = view.findViewById(R.id.fab);
        return view;
    }
}
