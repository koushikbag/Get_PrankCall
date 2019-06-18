package com.kbinfo.prankcall;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CallDeclinedFragment extends Fragment {
    private Context mContext;
    private static CallDeclinedFragment mCallDeclineFragmentInstance;
    private String name = "";
    private String mobileNumber = "";
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    private void CallDeclinedFragment() {
    }

    public static CallDeclinedFragment getInstance() {
        if (mCallDeclineFragmentInstance == null) {
            mCallDeclineFragmentInstance = new CallDeclinedFragment();
        }
        return mCallDeclineFragmentInstance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_decline, container, false);
        final TextView tvTime = view.findViewById(R.id.tv_time);
        TextView tvName = view.findViewById(R.id.tv_name_d);
        TextView tvNumber = view.findViewById(R.id.tv_mobile_number_d);
        ImageButton btnEndCall = view.findViewById(R.id.btn_end);
        mAdView = view.findViewById(R.id.adv_call_end);
        //mAdView.setAdSize(AdSize.BANNER);
       // mAdView.setAdUnitId(getString(R.string.banner_call_end));

        mInterstitialAd = new InterstitialAd(mContext);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.call_end_fullscreen));

        final AdRequest adRequestFu = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                //.addTestDevice("C04B1BFFB0774708339BC273F8A43708")
                .build();

       /* mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });*/

        Bundle bundle = getArguments();

        if (bundle != null) {
            name = bundle.getString("name");
            mobileNumber = bundle.getString("mobile_number");

            if (name.isEmpty()) {
                name = "Unknown";
            }
            if (mobileNumber != null && mobileNumber.isEmpty())
                mobileNumber = "+91 9011020304";

            tvName.setText(name);
            tvNumber.setText(mobileNumber);
        }

        /*new CountDownTimer(20*60000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTime.setText("seconds remaining: " +new SimpleDateFormat("mm:ss:SS").format(new Date( millisUntilFinished)));
            }

            public void onFinish() {
                tvTime.setText("done!");
            }
        }.start();*/

        final int[] time = {60};
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTime.setText("0:" + checkDigit(time[0]));
                time[0]--;
            }

            public void onFinish() {

                tvTime.setText("call finished");

                // Load ads into Interstitial Ads
                //mInterstitialAd.loadAd(adRequestFu);

                Objects.requireNonNull(getActivity()).finish();
            }

        }.start();

        btnEndCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Load ads into Interstitial Ads
                mInterstitialAd.loadAd(adRequestFu);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(getActivity()).finish();
                }
            }
        });

        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                //.addTestDevice("C04B1BFFB0774708339BC273F8A43708")
                .build();

        /*mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(mContext, "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(mContext, "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(mContext, "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });*/

        mAdView.loadAd(adRequest);

        return view;
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public String checkDigit(int number) {
        return (60 - number) <= 9 ? "0" + (60 - number) : String.valueOf(60 - number);
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
        mContext = null;
        super.onDestroy();
    }
}
