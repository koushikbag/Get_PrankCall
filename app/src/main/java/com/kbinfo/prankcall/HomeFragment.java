package com.kbinfo.prankcall;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class HomeFragment extends Fragment {

    private Context mContext;
    private static HomeFragment mHomeFragmentInstance;
    private AdView mAdView;

    private void HomeFragment(){}

    public static HomeFragment getInstance(){
        if (mHomeFragmentInstance == null){
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
//        mAdView.setAdSize(AdSize.BANNER);
//        mAdView.setAdUnitId(getString(R.string.banner_home));

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

        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                //.addTestDevice("C04B1BFFB0774708339BC273F8A43708")
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
        mContext = null;
        super.onDestroy();
    }
}
