package com.kbinfo.prankcall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.MobileAds;

public class PrankMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, getString(R.string.add_mob_id));
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, HomeFragment.getInstance())
                .addToBackStack("Home")
                .commit();
    }
}
