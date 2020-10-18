package com.irfandev.project.simplemarket.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.irfandev.project.simplemarket.LoginActivity;
import com.irfandev.project.simplemarket.R;
import com.irfandev.project.simplemarket.helpers.PrefsHelper;

/**
 * created by Irfan Assidiq
 * email : assidiq.irfan@gmail.com
 **/
public class ProfileFragment extends Fragment {
    TextView tvUsername;
    Button btnLogout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tvUsername = view.findViewById(R.id.tvUsername);
        btnLogout = view.findViewById(R.id.btnLogout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvUsername.setText(PrefsHelper.sharedInstance(getActivity().getApplicationContext()).getUsername());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                PrefsHelper.sharedInstance(getActivity().getApplicationContext()).setStatusLogin(false);
                PrefsHelper.sharedInstance(getActivity().getApplicationContext()).setUsername("");
                getActivity().finish();
            }
        });
    }
}
