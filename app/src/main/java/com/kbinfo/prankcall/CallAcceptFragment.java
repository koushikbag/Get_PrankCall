package com.kbinfo.prankcall;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CallAcceptFragment extends Fragment {
    private Context mContext;
    private static CallAcceptFragment mCallAcceptFragmentInstance;
    private String name = "";
    private String mobileNumber = "";
    private AdView mAdView;

    private void CallAcceptFragment() {
    }

    public static CallAcceptFragment getInstance() {
        if (mCallAcceptFragmentInstance == null) {
            mCallAcceptFragmentInstance = new CallAcceptFragment();
        }
        return mCallAcceptFragmentInstance;
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
        View view = inflater.inflate(R.layout.fragment_call_accept, container, false);
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvNumber = view.findViewById(R.id.tv_mobile_number);
        ImageButton btnAccept = view.findViewById(R.id.btn_accept);
        mAdView = view.findViewById(R.id.adv_call_start);
        // mAdView.setAdSize(AdSize.BANNER);
        //mAdView.setAdUnitId(getString(R.string.banner_call_start));
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

        Uri phoneNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        final Ringtone r = RingtoneManager.getRingtone(mContext, phoneNotification);
        final Vibrator vibe = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {500, 500, 500, 500, 500};

        r.play();
        vibe.vibrate(pattern, 0);

        /*if (Build.VERSION.SDK_INT >= 26) {
            vibe.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibe.vibrate(pattern, -1);
        }*/

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment callDeclineFragment = CallDeclinedFragment.getInstance();
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("mobile_number", mobileNumber);

                if (r.isPlaying()) {
                    r.stop();
                }

                vibe.cancel();

                callDeclineFragment.setArguments(bundle);
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, callDeclineFragment)
                            .addToBackStack("decline")
                            .commit();
                }
            }
        });


        /*btnAccept.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                *//*switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d("AcceptFragment: ", "Action down");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("AcceptFragment: ", "Action up");
                        break;
                }*//*

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d("AcceptFragment: ", "Action down");
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.d("AcceptFragment: ", "Action up");
                    return true;
                }
                return false;
            }
        });*/
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                //.addTestDevice("EEE580F95FFDE12539941C2E1AD22984")
                .setRequestAgent("android_studio:ad_template")
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
