package com.kbinfo.prankcall;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

public class PrankApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, getString(R.string.add_mob_id));
    }
}
