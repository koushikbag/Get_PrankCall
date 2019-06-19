package com.kbinfo.prankcall;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private Context mContext;
    private static HomeFragment mHomeFragmentInstance;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;

    private void HomeFragment() {
    }

    public static HomeFragment getInstance() {
        if (mHomeFragmentInstance == null) {
            mHomeFragmentInstance = new HomeFragment();
        }
        return mHomeFragmentInstance;
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final EditText etName = view.findViewById(R.id.et_name);
        final EditText etMobileNumber = view.findViewById(R.id.et_mobile_number);
        Button btnStartCall = view.findViewById(R.id.btn_call_me);
        mAdView = view.findViewById(R.id.adView);
        /*mInterstitialAd = new InterstitialAd(mContext);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.call_end_fullscreen));*/

        btnStartCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    Fragment callAcceptFragment = CallAcceptFragment.getInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", etName.getText().toString());
                    bundle.putString("mobile_number", etMobileNumber.getText().toString());

                    callAcceptFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, callAcceptFragment)
                            .addToBackStack("accept")
                            .commit();
                }
            }
        });

        adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                //.addTestDevice("EEE580F95FFDE12539941C2E1AD22984")
                .setRequestAgent("android_studio:ad_template")
                .build();

       /* mAdView.setAdListener(new AdListener() {
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
                Toast.makeText(mContext, "Ad Opened", Toast.LENGTH_SHORT).show();
            }
        });*/

        mAdView.loadAd(adRequest);

        return view;
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
        // Load ads into Interstitial Ads
        //mInterstitialAd.loadAd(adRequest);
        mContext = null;
        super.onDestroy();
    }
}
