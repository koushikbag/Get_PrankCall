package com.kbinfo.prankcall;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PrankMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MobileAds.initialize(this, getString(R.string.add_mob_id));
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, HomeFragment.getInstance())
                .commit();
    }
}
