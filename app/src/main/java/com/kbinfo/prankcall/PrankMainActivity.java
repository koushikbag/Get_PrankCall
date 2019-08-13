package com.kbinfo.prankcall;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class PrankMainActivity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MobileAds.initialize(this, getString(R.string.add_mob_id));

        //ad view implementaion here
        mAdView = findViewById(R.id.adView);
        /*mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.call_end_fullscreen));

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(new AdRequest.Builder().build());*/

        adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                //.addTestDevice("EEE580F95FFDE12539941C2E1AD22984")
                //.setRequestAgent("android_studio:ad_template")
                .build();

        /*mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(PrankMainActivity.this, "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(PrankMainActivity.this, "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(PrankMainActivity.this, "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Toast.makeText(PrankMainActivity.this, "Ad Opened", Toast.LENGTH_SHORT).show();
            }
        });*/

        mAdView.loadAd(adRequest);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, HomeFragment.getInstance())
                .commit();
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        /*if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            Log.d("PrankMainActivity: ", "Loaded Succesfully");
        } else {
            Log.d("PrankMainActivity: ", "The interstitial wasn't loaded yet.");
        }*/
        super.onDestroy();
    }
}
