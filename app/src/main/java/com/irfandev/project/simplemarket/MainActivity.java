package com.irfandev.project.simplemarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.irfandev.project.simplemarket.fragments.HadiahFragment;
import com.irfandev.project.simplemarket.fragments.HomeFragment;
import com.irfandev.project.simplemarket.fragments.ProfileFragment;
import com.irfandev.project.simplemarket.helpers.PrefsHelper;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView btmNavView;
    FragmentManager frManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btmNavView = findViewById(R.id.btmNavView);

//        final String nama = PrefsHelper.sharedInstance(MainActivity.this).getUsername();
//        final Dialog dialog = new Dialog(MainActivity.this);
//        dialog.setContentView(R.layout.pop_dialog);
//        dialog.setCancelable(false);
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT);
//        Button btnOK = dialog.findViewById(R.id.btnOK);
//        TextView tvUsername = dialog.findViewById(R.id.tvUsername);
//        btnOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        tvUsername.setText(nama);
//        dialog.show();
        callFragment(new HomeFragment());
        btmNavView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home :{
                callFragment(new HomeFragment());
            }break;
            case R.id.give :{
                callFragment(new HadiahFragment());
            }break;
            case R.id.profile :{
                callFragment(new ProfileFragment());
            }break;
            default: {
                Toast.makeText(this, "Fragment Tidak tersedia", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    public void callFragment(Fragment fr){
        frManager = getSupportFragmentManager();
        frManager.beginTransaction()
                .replace(R.id.frlayout, fr)
                .commit();
    }
}