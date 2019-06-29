package com.kbinfo.prankcall;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.kbinfo.prankcall.util.MyBounceInterpolator;

public class CallScreenActivity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;
    private Ringtone mRingtone;
    private Vibrator mVibrator;
    private String name = "";
    private String mobileNumber = "";
    private TextView tvName;
    private TextView tvNumber;
    private ImageButton btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_screen);
        setWindowFlag();
        setAdParams();
        init();

        /*getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_call_container, CallAcceptFragment.getInstance())
                .commit();*/


        Uri phoneNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        mRingtone = RingtoneManager.getRingtone(this, phoneNotification);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {500, 500, 500, 500, 500};

        mRingtone.play();
        if (mVibrator.hasVibrator())
            mVibrator.vibrate(pattern, 0);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment callDeclineFragment = CallDeclinedFragment.getInstance();
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("mobile_number", mobileNumber);

                if (mRingtone.isPlaying()) {
                    mRingtone.stop();
                }

                if (mVibrator.hasVibrator())
                    mVibrator.cancel();

                callDeclineFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_call_container, callDeclineFragment)
                        .commit();
            }
        });

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        //myAnim.setRepeatCount(Animation.INFINITE);
        //myAnim.setRepeatMode(AlphaAnimation.INFINITE);
        btnAccept.startAnimation(myAnim);
    }

    private void init() {
        tvName = findViewById(R.id.tv_name);
        tvNumber = findViewById(R.id.tv_mobile_number);
        btnAccept = findViewById(R.id.btn_accept);

        name = getIntent().getStringExtra("name");
        mobileNumber = getIntent().getStringExtra("mobile_number");

        if (name.isEmpty()) {
            name = "Unknown";
        }
        if (mobileNumber != null && mobileNumber.isEmpty())
            mobileNumber = "+91 9011020304";

        tvName.setText(name);
        tvNumber.setText(mobileNumber);
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

    private void setWindowFlag() {
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    private void setAdParams() {
        //MobileAds.initialize(this, getString(R.string.add_mob_id));
        //ad view implementaion here
        mAdView = findViewById(R.id.adViewCall);
        mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.call_end_fullscreen));

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                //.addTestDevice("EEE580F95FFDE12539941C2E1AD22984")
                .setRequestAgent("android_studio:ad_template")
                .build();

        mAdView.loadAd(adRequest);
    }
}
